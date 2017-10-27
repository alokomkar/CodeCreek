package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.ProgramListActivity;
import com.sortedqueue.programmercreek.adapter.MatchOptionsDragAdapter;
import com.sortedqueue.programmercreek.adapter.MatchQuestionsDropAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener;
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener;
import com.sortedqueue.programmercreek.interfaces.WizardNavigationListener;
import com.sortedqueue.programmercreek.util.AnimationUtils;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-08-14.
 */

public class NewMatchFragment extends Fragment implements View.OnClickListener, TestCompletionListener {

    @BindView(R.id.questionRecyclerView)
    RecyclerView questionRecyclerView;
    @BindView(R.id.optionRecyclerView)
    RecyclerView optionRecyclerView;
    List<ProgramTable> mProgramTableList;
    @BindView(R.id.checkButton)
    Button checkButton;
    @BindView(R.id.optionsTextView)
    TextView optionsTextView;
    private Bundle newProgramActivityBundle;
    private int mInvokeMode;
    private ArrayList<ProgramTable> program_TableList;
    private ProgramIndex mProgramIndex;
    private boolean mWizard;
    private ArrayList<ProgramTable> mProgramQuestionList;
    private ArrayList<String> mOptionsList;
    private MatchQuestionsDropAdapter matchQuestionsAdapter;
    private MatchOptionsDragAdapter matchOptionsAdapter;
    private boolean quizComplete;
    private ArrayList<String> solutionList;
    private ModuleDetailsScrollPageListener moduleDetailsScrollPageListener;
    private String TAG = NewMatchFragment.class.getSimpleName();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_new_match, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    public void setBundle(Bundle bundle) {
        this.newProgramActivityBundle = bundle;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkButton.setOnClickListener(this);
        mInvokeMode = newProgramActivityBundle.getInt(ProgrammingBuddyConstants.INSTANCE.getKEY_INVOKE_TEST(), -1);
        program_TableList = newProgramActivityBundle.getParcelableArrayList(ProgrammingBuddyConstants.INSTANCE.getKEY_USER_PROGRAM());
        showHelperDialog();
        if (program_TableList != null && program_TableList.size() > 0) {
            mProgramIndex = (ProgramIndex) newProgramActivityBundle.get(ProgrammingBuddyConstants.INSTANCE.getKEY_PROG_ID());
            mWizard = newProgramActivityBundle.getBoolean(ProgramListActivity.Companion.getKEY_WIZARD(), false);
            initUI(program_TableList);
        } else {
            if (mInvokeMode == ProgrammingBuddyConstants.INSTANCE.getKEY_LESSON()) {
                mWizard = false;
                new FirebaseDatabaseHandler(getContext()).getProgramIndexInBackGround(newProgramActivityBundle.getInt(ProgrammingBuddyConstants.INSTANCE.getKEY_PROG_ID()),
                        new FirebaseDatabaseHandler.GetProgramIndexListener() {
                            @Override
                            public void onSuccess(ProgramIndex programIndex) {
                                mProgramIndex = programIndex;
                                getProgramTables();
                            }

                            @Override
                            public void onError(DatabaseError databaseError) {
                                CommonUtils.INSTANCE.displayToast(getContext(), R.string.unable_to_fetch_data);
                            }
                        });
            } else {
                mProgramIndex = (ProgramIndex) newProgramActivityBundle.get(ProgrammingBuddyConstants.INSTANCE.getKEY_PROG_ID());
                mWizard = newProgramActivityBundle.getBoolean(ProgramListActivity.Companion.getKEY_WIZARD(), false);

                getProgramTables();
            }
        }


    }

    private void showHelperDialog() {
        AuxilaryUtils.INSTANCE.displayInformation(getContext(), R.string.match_maker, R.string.match_maker_new_description, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }

        });
    }

    private void getProgramTables() {
        new FirebaseDatabaseHandler(getContext()).getProgramTablesInBackground(mProgramIndex.getProgram_index(), new FirebaseDatabaseHandler.GetProgramTablesListener() {
            @Override
            public void onSuccess(ArrayList<ProgramTable> programTables) {
                program_TableList = programTables;
                initUI(program_TableList);
            }

            @Override
            public void onError(DatabaseError databaseError) {
                CommonUtils.INSTANCE.displaySnackBar(getActivity(), R.string.unable_to_fetch_data);

            }
        });
    }

    private void initUI(ArrayList<ProgramTable> program_TableList) {
        if( mProgramIndex != null ) {
            try {
                CreekAnalytics.INSTANCE.logEvent(TAG, new JSONObject(new Gson().toJson(mProgramIndex)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        questionRecyclerView.setVisibility(View.INVISIBLE);
        optionRecyclerView.setVisibility(View.INVISIBLE);
        optionsTextView.setVisibility(View.INVISIBLE);
        solutionList = new ArrayList<>();
        for (ProgramTable programTable : program_TableList) {
            solutionList.add(programTable.getProgram_Line());
        }

        mProgramTableList = program_TableList;

        if (program_TableList != null && program_TableList.size() > 0) {
            //TODO
            getActivity().setTitle("Match : " + mProgramIndex.getProgram_Description());
            mProgramQuestionList = ProgramTable.getMatchList(program_TableList, new ProgramTable.MatchOptionsListener() {
                @Override
                public void getOptionsList(ArrayList<String> optionsList) {
                    mOptionsList = optionsList;
                }
            });
            for (ProgramTable programTable : mProgramQuestionList) {
                Log.d("Match", "programTable : " + programTable);
            }
            for (String solution : mOptionsList) {
                Log.d("Match", "solution : " + solution);
            }

        }

        Collections.shuffle(mOptionsList);

        questionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (mOptionsList.size() <= 4) {
            optionRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        } else {
            //optionRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false));
            optionRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        }

        matchQuestionsAdapter = new MatchQuestionsDropAdapter(mProgramQuestionList);
        questionRecyclerView.setAdapter(matchQuestionsAdapter);
        matchOptionsAdapter = new MatchOptionsDragAdapter(mOptionsList);
        optionRecyclerView.setAdapter(matchOptionsAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationUtils.INSTANCE.slideInToRight(questionRecyclerView);
                AnimationUtils.INSTANCE.slideInToLeft(optionRecyclerView);
                AnimationUtils.INSTANCE.slideInToLeft(optionsTextView);
            }
        }, 400);

    }

    private WizardNavigationListener wizardNavigationListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WizardNavigationListener) {
            wizardNavigationListener = (WizardNavigationListener) context;
        }
    }

    @Override
    public void onClick(View view) {
        if (checkButton.getText().toString().equalsIgnoreCase("Check")) {
            ArrayList<ProgramTable> programTables = matchQuestionsAdapter.getProgramList();
            for (int i = 0; i < solutionList.size(); i++) {
                programTables.get(i).isCorrect = solutionList.get(i).trim().equals(programTables.get(i).getProgram_Line().trim());
                program_TableList.get(i).setProgram_Line(solutionList.get(i));
            }
            quizComplete = true;
            matchQuestionsAdapter.setChecked(true, programTables);
            if( moduleDetailsScrollPageListener != null ) {
                moduleDetailsScrollPageListener.toggleFABDrawable();
            }
            if (mWizard) {
                checkButton.setText("Next");
            } else
                checkButton.setText("Finish");
        } else {
            if (!mWizard) {
                getActivity().finish();
            } else {
                Bundle newIntentBundle = new Bundle();
                newIntentBundle.putParcelable(ProgrammingBuddyConstants.INSTANCE.getKEY_PROG_ID(), mProgramIndex);
                newIntentBundle.putBoolean(ProgramListActivity.Companion.getKEY_WIZARD(), true);
                newIntentBundle.putParcelableArrayList(ProgrammingBuddyConstants.INSTANCE.getKEY_USER_PROGRAM(), newProgramActivityBundle.getParcelableArrayList(ProgrammingBuddyConstants.INSTANCE.getKEY_USER_PROGRAM()));
                Log.d("MatchFragment", "Preference Language : " + CreekApplication.Companion.getCreekPreferences().getProgramLanguage());
                if (program_TableList.size() <= 15) {
                    wizardNavigationListener.loadTestFragment(newIntentBundle);
                } else {
                    wizardNavigationListener.loadFillBlanksFragment(newIntentBundle);
                }
            }

        }

    }

    public ArrayList<String> getmProgramList() {
        return solutionList;
    }

    public void onBackPressed() {
        if (!quizComplete) {
            AuxilaryUtils.INSTANCE.showConfirmationDialog(getActivity());
        } else {
            getActivity().finish();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public int isTestComplete() {
        return quizComplete ? ProgrammingBuddyConstants.INSTANCE.getKEY_MATCH() : -1;
    }

    public void setModuleDetailsScrollPageListener(ModuleDetailsScrollPageListener moduleDetailsScrollPageListener) {
        this.moduleDetailsScrollPageListener = moduleDetailsScrollPageListener;
    }
}

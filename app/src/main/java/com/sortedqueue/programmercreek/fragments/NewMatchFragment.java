package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.ProgramListActivity;
import com.sortedqueue.programmercreek.adapter.MatchOptionsDragAdapter;
import com.sortedqueue.programmercreek.adapter.MatchQuestionsDropAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.WizardNavigationListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-08-14.
 */

public class NewMatchFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.questionRecyclerView)
    RecyclerView questionRecyclerView;
    @BindView(R.id.optionRecyclerView)
    RecyclerView optionRecyclerView;
    List<ProgramTable> mProgramTableList;
    @BindView(R.id.checkButton)
    Button checkButton;
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
        mInvokeMode = newProgramActivityBundle.getInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST, -1);
        program_TableList = newProgramActivityBundle.getParcelableArrayList(ProgrammingBuddyConstants.KEY_USER_PROGRAM);

        if (program_TableList != null && program_TableList.size() > 0) {
            mProgramIndex = (ProgramIndex) newProgramActivityBundle.get(ProgrammingBuddyConstants.KEY_PROG_ID);
            mWizard = newProgramActivityBundle.getBoolean(ProgramListActivity.KEY_WIZARD, false);
            initUI(program_TableList);
        } else {
            if (mInvokeMode == ProgrammingBuddyConstants.KEY_LESSON) {
                mWizard = false;
                new FirebaseDatabaseHandler(getContext()).getProgramIndexInBackGround(newProgramActivityBundle.getInt(ProgrammingBuddyConstants.KEY_PROG_ID),
                        new FirebaseDatabaseHandler.GetProgramIndexListener() {
                            @Override
                            public void onSuccess(ProgramIndex programIndex) {
                                mProgramIndex = programIndex;
                                getProgramTables();
                            }

                            @Override
                            public void onError(DatabaseError databaseError) {
                                CommonUtils.displayToast(getContext(), R.string.unable_to_fetch_data);
                            }
                        });
            } else {
                mProgramIndex = (ProgramIndex) newProgramActivityBundle.get(ProgrammingBuddyConstants.KEY_PROG_ID);
                mWizard = newProgramActivityBundle.getBoolean(ProgramListActivity.KEY_WIZARD, false);

                getProgramTables();
            }
        }


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
                CommonUtils.displaySnackBar(getActivity(), R.string.unable_to_fetch_data);

            }
        });
    }

    private void initUI(ArrayList<ProgramTable> program_TableList) {

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

        }

        Collections.shuffle(mOptionsList);

        questionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        optionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        matchQuestionsAdapter = new MatchQuestionsDropAdapter(mProgramQuestionList);
        questionRecyclerView.setAdapter(matchQuestionsAdapter);
        matchOptionsAdapter = new MatchOptionsDragAdapter(mOptionsList);
        optionRecyclerView.setAdapter(matchOptionsAdapter);
    }

    private WizardNavigationListener wizardNavigationListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if( context instanceof WizardNavigationListener ) {
            wizardNavigationListener = (WizardNavigationListener) context;
        }
    }

    @Override
    public void onClick(View view) {
        if( checkButton.getText().toString().equalsIgnoreCase("Check") ) {
            ArrayList<ProgramTable> programTables = matchQuestionsAdapter.getProgramList();
            for( int i = 0; i < mProgramTableList.size(); i++ ) {
                programTables.get(i).isCorrect = mProgramTableList.get(i).getProgram_Line() == programTables.get(i).getProgram_Line();
            }
            quizComplete = true;
            matchQuestionsAdapter.setChecked(true);
            checkButton.setText("Next");
        }
        else {
            Bundle newIntentBundle = new Bundle();
            newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, mProgramIndex);
            newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true);
            newIntentBundle.putParcelableArrayList(ProgrammingBuddyConstants.KEY_USER_PROGRAM, newProgramActivityBundle.getParcelableArrayList(ProgrammingBuddyConstants.KEY_USER_PROGRAM));
            Log.d("MatchFragment", "Preference Language : " + CreekApplication.getCreekPreferences().getProgramLanguage() );
            if( program_TableList.size() <= 15 ) {
                wizardNavigationListener.loadTestFragment(newIntentBundle);
            }
            else {
                wizardNavigationListener.loadFillBlanksFragment(newIntentBundle);
            }
        }

    }

    public ArrayList<String> getmProgramList() {
        ArrayList<String> programList = new ArrayList<>();
        for( ProgramTable programTable : mProgramTableList ) {
            programList.add(programTable.getProgram_Line());
        }
        return programList;
    }

    public void onBackPressed() {
        if (!quizComplete) {
            AuxilaryUtils.showConfirmationDialog(getActivity());
        } else {
            getActivity().finish();
        }

    }
}

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.ProgramListActivity;
import com.sortedqueue.programmercreek.adapter.MatchOptionsDragAdapter;
import com.sortedqueue.programmercreek.adapter.MatchQuestionsDropAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener;
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener;
import com.sortedqueue.programmercreek.interfaces.WizardNavigationListener;
import com.sortedqueue.programmercreek.util.AnimationUtils;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.VideoListener;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 23/08/17.
 */

public class NewFillBlankFragment extends Fragment implements View.OnClickListener, TestCompletionListener {

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
    private boolean isAnswered;
    private CreekUserStats creekUserStats;
    private int program_index;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_new_fill_blank, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkButton.setOnClickListener(this);
        mInvokeMode = newProgramActivityBundle.getInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST, -1);
        program_TableList = newProgramActivityBundle.getParcelableArrayList(ProgrammingBuddyConstants.KEY_USER_PROGRAM);
        showHelperDialog();
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

    private void showHelperDialog() {
        AuxilaryUtils.displayInformation(getContext(), R.string.fill_blanks, R.string.match_maker_new_description, new DialogInterface.OnDismissListener() {
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
                CommonUtils.displaySnackBar(getActivity(), R.string.unable_to_fetch_data);

            }
        });
    }

    public void setBundle(Bundle bundle) {
        this.newProgramActivityBundle = bundle;
    }

    private void initUI(ArrayList<ProgramTable> program_TableList) {
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
            getActivity().setTitle("Fill blanks : " + mProgramIndex.getProgram_Description());
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
            optionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        } else {
            //optionRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false));
            optionRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        }

        matchQuestionsAdapter = new MatchQuestionsDropAdapter(mProgramQuestionList, true);
        questionRecyclerView.setAdapter(matchQuestionsAdapter);
        matchOptionsAdapter = new MatchOptionsDragAdapter(mOptionsList);
        optionRecyclerView.setAdapter(matchOptionsAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationUtils.slideInToRight(questionRecyclerView);
                AnimationUtils.slideInToLeft(optionRecyclerView);
                AnimationUtils.slideInToLeft(optionsTextView);
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
            int rightAnswers = 0;
            for (int i = 0; i < solutionList.size(); i++) {
                programTables.get(i).isCorrect = solutionList.get(i).trim().equals(programTables.get(i).getProgram_Line().trim());
                if( programTables.get(i).isChoice && programTables.get(i).isCorrect ) rightAnswers++;
                //program_TableList.get(i).setProgram_Line(solutionList.get(i));
            }
            quizComplete = true;
            matchQuestionsAdapter.setChecked(true, programTables);
            isAnswered = true;
            String message = "";
            if( rightAnswers == mOptionsList.size() ) {
                message = "Congratulations, you've got it";
            }
            else if( rightAnswers < (mOptionsList.size() / 2) ) {
                message = "You need improvement, try again";
            }
            else {
                message = "Almost perfect, you are few steps away, retry";
            }
            isAnswered = rightAnswers == mOptionsList.size();
            CommonUtils.displaySnackBar(getActivity(), message + ". You scored : " + rightAnswers + "/" + mOptionsList.size());
            if (mWizard) {
                updateCreekStats();
            }
            if( isAnswered ) {

                if( mWizard ) {
                    checkButton.setText("Finish");
                }
                else {
                    checkButton.setText("Proceed");
                }
            }

        } else {
            if( mWizard )
                getActivity().finish();
            else {
                if( moduleDetailsScrollPageListener != null ) {
                    moduleDetailsScrollPageListener.onScrollForward();
                }
                else {
                    getActivity().finish();
                }


            }

        }

    }

    private void updateCreekStats() {
        creekUserStats = CreekApplication.getInstance().getCreekUserStats();
        switch (mProgramIndex.getProgram_Language().toLowerCase()) {
            case "c":
                creekUserStats.addToUnlockedCProgramIndexList(mProgramIndex.getProgram_index() + 1);
                break;
            case "cpp":
            case "c++":
                creekUserStats.addToUnlockedCppProgramIndexList(mProgramIndex.getProgram_index() + 1);
                break;
            case "java":
                creekUserStats.addToUnlockedJavaProgramIndexList(mProgramIndex.getProgram_index() + 1);
                break;
            case "usp":
                creekUserStats.addToUnlockedUspProgramIndexList(mProgramIndex.getProgram_index() + 1);
                break;
        }
        new FirebaseDatabaseHandler(getContext()).writeCreekUserStats(creekUserStats);
    }

    public ArrayList<String> getmProgramList() {
        return solutionList;
    }

    public void onBackPressed() {
        if (!quizComplete) {
            AuxilaryUtils.showConfirmationDialog(getActivity());
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
        return isAnswered ? ProgrammingBuddyConstants.KEY_FILL_BLANKS : -1;
    }

    public void setWizardMode(boolean wizardMode) {
        this.mWizard = wizardMode;
    }

    public void setmProgram_Index(int program_index) {
        this.program_index = program_index;
    }

    private ModuleDetailsScrollPageListener moduleDetailsScrollPageListener;
    public void setModulteDetailsScrollPageListener(ModuleDetailsScrollPageListener moduleDetailsScrollPageListener) {
        this.moduleDetailsScrollPageListener = moduleDetailsScrollPageListener;
    }

    public void showRewardedVideoDialog() {
       AuxilaryUtils.displayInformation(getContext(), R.string.hint_video, R.string.reward_video_description,
               new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       showRewardedClick();
                   }
               },

        new DialogInterface.OnDismissListener() {
                   @Override
                   public void onDismiss(DialogInterface dialogInterface) {

                   }
               });
    }


    public void showRewardedClick() {
        final StartAppAd rewardedVideo = new StartAppAd(getContext());

        /**
         * This is very important: set the video listener to be triggered after video
         * has finished playing completely
         */
        rewardedVideo.setVideoListener(new VideoListener() {

            @Override
            public void onVideoCompleted() {
                int maxHints = (mOptionsList.size() / 2);
                if( maxHints > 3 ) {
                    maxHints = 3;
                }
                CommonUtils.displaySnackBar(getActivity(), maxHints + " new hints have been added");

                matchQuestionsAdapter.addHints(maxHints);

            }
        });

        /**
         * Load rewarded by specifying AdMode.REWARDED
         * We are using AdEventListener to trigger ad show
         */
        rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {

            @Override
            public void onReceiveAd(Ad arg0) {
                rewardedVideo.showAd();
            }

            @Override
            public void onFailedToReceiveAd(Ad arg0) {
                /**
                 * Failed to load rewarded video:
                 * 1. Check that FullScreenActivity is declared in AndroidManifest.xml:
                 * See https://github.com/StartApp-SDK/Documentation/wiki/Android-InApp-Documentation#activities
                 * 2. Is android API level above 16?
                 */
                Log.e("MainActivity", "Failed to load rewarded video with reason: " + arg0.getErrorMessage());
            }
        });
    }
}

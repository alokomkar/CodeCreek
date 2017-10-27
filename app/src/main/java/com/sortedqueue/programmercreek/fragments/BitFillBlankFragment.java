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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.MatchOptionsDragAdapter;
import com.sortedqueue.programmercreek.adapter.MatchQuestionsDropAdapter;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.database.lessons.BitModule;
import com.sortedqueue.programmercreek.interfaces.LessonNavigationListener;
import com.sortedqueue.programmercreek.interfaces.OnBackPressListener;
import com.sortedqueue.programmercreek.util.AnimationUtils;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekAnalytics;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.VideoListener;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 31/08/17.
 */

public class BitFillBlankFragment extends Fragment implements View.OnClickListener, FirebaseDatabaseHandler.ConfirmUserProgram {

    @BindView(R.id.questionRecyclerView)
    RecyclerView questionRecyclerView;
    @BindView(R.id.optionsTextView)
    TextView optionsTextView;
    @BindView(R.id.optionRecyclerView)
    RecyclerView optionRecyclerView;
    @BindView(R.id.checkButton)
    Button checkButton;
    @BindView(R.id.buttonLayout)
    LinearLayout buttonLayout;
    @BindView(R.id.hintButton)
    Button hintButton;
    @BindView(R.id.resultTextView)
    TextView resultTextView;
    @BindView(R.id.proceedTextView)
    TextView proceedTextView;
    @BindView(R.id.resultLayout)
    RelativeLayout resultLayout;
    private ArrayList<ProgramTable> mProgramTableList;
    private ArrayList<ProgramTable> mProgramQuestionList;
    private ArrayList<String> mOptionsList;
    private MatchQuestionsDropAdapter matchQuestionsAdapter;
    private MatchOptionsDragAdapter matchOptionsAdapter;
    private boolean quizComplete;
    private boolean isAnswered;
    private CreekUserStats creekUserStats;

    private String programLangauge;
    private String moduleId;
    private BitModule bitModule;
    private OnBackPressListener onBackPressListener;
    private LessonNavigationListener lessonNavigationListener;
    private String TAG = BitFillBlankFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_bit_fill_blank, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CreekAnalytics.logEvent(TAG, "Fill Blanks - Bits and Bytes");
        bitModule = getArguments().getParcelable("BitModule");
        programLangauge = bitModule.getProgramLanguage();
        moduleId = bitModule.getModuleId();
        checkButton.setOnClickListener(this);
        hintButton.setOnClickListener(this);
        proceedTextView.setOnClickListener(this);
        showHelperDialog();
        //lessonNavigationListener.onProgessStatsUpdate(CreekUserStats.PROGRAM_SCORE);
        new FirebaseDatabaseHandler(getContext()).compileSharedProgram(bitModule.getCode(), this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LessonNavigationListener) {
            lessonNavigationListener = (LessonNavigationListener) context;
        }
    }

    private void showHelperDialog() {
        AuxilaryUtils.displayInformation(getContext(), R.string.fill_blanks, R.string.match_maker_new_description, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }

        });
    }

    private ArrayList<String> solutionList;

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
        optionRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkButton:
                if (checkButton.getText().toString().equalsIgnoreCase("Check")) {
                    ArrayList<ProgramTable> programTables = matchQuestionsAdapter.getProgramList();
                    int rightAnswers = 0;
                    for (int i = 0; i < solutionList.size(); i++) {
                        programTables.get(i).isCorrect = solutionList.get(i).trim().equals(programTables.get(i).getProgram_Line().trim());
                        if (programTables.get(i).isChoice && programTables.get(i).isCorrect)
                            rightAnswers++;
                        //program_TableList.get(i).setProgram_Line(solutionList.get(i));
                    }
                    quizComplete = true;
                    matchQuestionsAdapter.setChecked(true, programTables);
                    isAnswered = true;
                    String message = "";
                    if (rightAnswers == mOptionsList.size()) {
                        message = "Congratulations, you've got it";
                    } else if (rightAnswers < (mOptionsList.size() / 2)) {
                        message = "You need improvement, try again";
                    } else {
                        message = "Almost perfect, you are few steps away, retry";
                    }
                    isAnswered = rightAnswers == mOptionsList.size();
                    resultTextView.setText( message + ". You scored : " + rightAnswers + "/" + mOptionsList.size());
                    AnimationUtils.slideInToLeft(resultLayout);
                    updateCreekStats();
                    lessonNavigationListener.onProgessStatsUpdate(CreekUserStats.PROGRAM_SCORE);
                    proceedTextView.setText("Okay");
                    if (isAnswered) {
                        proceedTextView.setText("Proceed");
                        checkButton.setText("Finish");
                    }
                } else {
                    onBackPressListener.onBackPressed();
                }
                break;
            case R.id.hintButton:
                if (!CreekApplication.Companion.getCreekPreferences().isPremiumUser()) {
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
                                    showRewardedClick();
                                }
                            });
                } else {
                    matchQuestionsAdapter.enableHints();
                }

                break;

            case R.id.proceedTextView :
                if( proceedTextView.getText().toString().equalsIgnoreCase("Proceed")) {
                    onBackPressListener.onBackPressed();
                }
                AnimationUtils.slideOutToLeft(resultLayout);
                break;
        }

    }

    private void showRewardedClick() {
        final StartAppAd rewardedVideo = new StartAppAd(getContext());

        /**
         * This is very important: set the video listener to be triggered after video
         * has finished playing completely
         */
        rewardedVideo.setVideoListener(new VideoListener() {

            @Override
            public void onVideoCompleted() {
                matchQuestionsAdapter.enableHints();
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

    private void updateCreekStats() {
        creekUserStats = CreekApplication.getInstance().getCreekUserStats();
        switch (programLangauge.toLowerCase()) {
            /*case "c":
                creekUserStats.addToUnlockedCProgramIndexList(mProgramIndex.getProgram_index() + 1);
                break;
            case "cpp":
            case "c++":
                creekUserStats.addToUnlockedCppProgramIndexList(mProgramIndex.getProgram_index() + 1);
                break;*/
            case "java":
                creekUserStats.addToUnlockedJavaBitsAndBytesIndexList(moduleId);
                break;
            /*case "usp":
                creekUserStats.addToUnlockedUspProgramIndexList(mProgramIndex.getProgram_index() + 1);
                break;*/
        }
        new FirebaseDatabaseHandler(getContext()).writeCreekUserStats(creekUserStats);
    }

    @Override
    public void onSuccess(ProgramIndex programIndex, ArrayList<ProgramTable> programTables) {
        initUI(programTables);
        CommonUtils.dismissProgressDialog();
    }

    @Override
    public void onError(String errorMessage) {
        CommonUtils.dismissProgressDialog();
    }

    public void setOnBackPressListener(OnBackPressListener onBackPressListener) {
        this.onBackPressListener = onBackPressListener;
    }
}

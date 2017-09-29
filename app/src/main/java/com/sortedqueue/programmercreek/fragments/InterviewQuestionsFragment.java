package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CodeEditorRecyclerAdapter;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.adapter.InterviewQuestionsAdapter;
import com.sortedqueue.programmercreek.asynctask.SlideContentReaderTask;
import com.sortedqueue.programmercreek.database.InterviewQuestionModel;
import com.sortedqueue.programmercreek.database.OptionModel;
import com.sortedqueue.programmercreek.interfaces.InterviewNavigationListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.SimpleItemTouchHelperCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sortedqueue.programmercreek.constants.InterviewQuestionConstants.TYPE_MULTIPLE_RIGHT;
import static com.sortedqueue.programmercreek.constants.InterviewQuestionConstants.TYPE_REARRANGE;
import static com.sortedqueue.programmercreek.constants.InterviewQuestionConstants.TYPE_SINGLE_RIGHT;
import static com.sortedqueue.programmercreek.constants.InterviewQuestionConstants.TYPE_TRUE_FALSE;

/**
 * Created by Alok Omkar on 2017-03-08.
 */

public class InterviewQuestionsFragment extends Fragment implements SlideContentReaderTask.OnDataReadListener, CustomProgramRecyclerViewAdapter.AdapterClickListner {

    @BindView(R.id.questionTextView)
    TextView questionTextView;
    @BindView(R.id.optionsRecyclerView)
    RecyclerView optionsRecyclerView;
    @BindView(R.id.questionLayout)
    LinearLayout questionLayout;
    @BindView(R.id.lifeLine1ImageView)
    ImageView lifeLine1ImageView;
    @BindView(R.id.lifeLine2ImageView)
    ImageView lifeLine2ImageView;
    @BindView(R.id.lifeLine3ImageView)
    ImageView lifeLine3ImageView;
    @BindView(R.id.lifeLineLayout)
    RelativeLayout lifeLineLayout;
    @BindView(R.id.progressTextView)
    TextView progressTextView;
    @BindView(R.id.codeRecyclerView)
    RecyclerView codeRecyclerView;
    @BindView(R.id.timerProgressBar)
    ProgressBar timerProgressBar;
    @BindView(R.id.checkAnswerImageView)
    TextView checkAnswerImageView;

    private ArrayList<InterviewQuestionModel> interviewQuestionModels;
    private InterviewQuestionModel interviewQuestionModel;
    private InterviewQuestionsAdapter interviewQuestionsAdapter;
    private CountDownTimer mCountDownTimer;
    private InterviewNavigationListener mInterviewNavigationListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interview_questions, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkAnswerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
        interviewQuestionModels = new ArrayList<>();
        getAllInterviewModels(programLanguage);

    }

    private int index = 0;

    private void getAllInterviewModels(String programLanguage) {
        /*setupMultiRightModel();
        setupRearrangeModel();
        setupSingleRightModel();
        setupTrueFalseModel();*/
        String fileId = "c_questions";
        new SlideContentReaderTask(getActivity(), fileId, this).execute();


    }

    private void startTimer() {
        timerProgressBar.setIndeterminate(false);
        timerProgressBar.setMax(59);
        timerProgressBar.setProgress(0);
        mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (progressTextView != null)
                    progressTextView.setText("" + (int) (millisUntilFinished / 1000));
                timerProgressBar.setProgress((int) (millisUntilFinished / 1000));
                if( timerProgressBar.getProgress() == 1 ) {
                    timerProgressBar.setProgress(0);
                }
            }

            @Override
            public void onFinish() {
                if (progressTextView != null) {
                    progressTextView.setText("--");
                }


            }
        };
        mCountDownTimer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

    private void setupMultiRightModel() {
        interviewQuestionModel = new InterviewQuestionModel();
        interviewQuestionModel.setTypeOfQuestion(TYPE_MULTIPLE_RIGHT);
        ArrayList<OptionModel> optionModels = new ArrayList<>();
        int index = 1;
        optionModels.add(new OptionModel(index++, "int a = 1;"));
        optionModels.add(new OptionModel(index++, "char b = \"ava\""));
        optionModels.add(new OptionModel(index++, "char[] str = \"abcd\""));
        interviewQuestionModel.setOptionModels(optionModels);
        interviewQuestionModel.setQuestion("Which are valid?");
        ArrayList<Integer> correctOptions = new ArrayList<>();
        correctOptions.add(1);
        correctOptions.add(3);
        interviewQuestionModel.setCorrectOptions(correctOptions);
        interviewQuestionModel.setModelId("Model_1");
        interviewQuestionModel.setProgramLanguage("c");
        interviewQuestionModels.add(interviewQuestionModel);
    }

    private void setupRearrangeModel() {
        interviewQuestionModel = new InterviewQuestionModel();
        interviewQuestionModel.setTypeOfQuestion(TYPE_REARRANGE);
        ArrayList<OptionModel> optionModels = new ArrayList<>();
        int index = 1;
        optionModels.add(new OptionModel(index++, "void main() {"));
        optionModels.add(new OptionModel(index++, " puts(s);"));
        optionModels.add(new OptionModel(index++, " int s = 0;"));
        optionModels.add(new OptionModel(index++, "}"));
        ArrayList<Integer> correctSequence = new ArrayList<>();
        correctSequence.add(1);
        correctSequence.add(3);
        correctSequence.add(2);
        correctSequence.add(4);
        interviewQuestionModel.setCorrectSequence(correctSequence);
        interviewQuestionModel.setOptionModels(optionModels);
        interviewQuestionModel.setQuestion("Arrange in right sequence");
        interviewQuestionModel.setCorrectOption(1);
        interviewQuestionModel.setModelId("Model_1");
        interviewQuestionModel.setProgramLanguage("c");
        interviewQuestionModels.add(interviewQuestionModel);
    }

    private void setupSingleRightModel() {
        interviewQuestionModel = new InterviewQuestionModel();
        interviewQuestionModel.setTypeOfQuestion(TYPE_SINGLE_RIGHT);
        ArrayList<OptionModel> optionModels = new ArrayList<>();
        int index = 1;
        optionModels.add(new OptionModel(index++, "True"));
        optionModels.add(new OptionModel(index++, "False"));
        optionModels.add(new OptionModel(index++, "No Idea"));
        interviewQuestionModel.setOptionModels(optionModels);
        interviewQuestionModel.setQuestion("Is it True that !true is false?");
        interviewQuestionModel.setCorrectOption(1);
        interviewQuestionModel.setModelId("Model_1");
        interviewQuestionModel.setProgramLanguage("c");
        interviewQuestionModels.add(interviewQuestionModel);
    }

    private void setupTrueFalseModel() {
        interviewQuestionModel = new InterviewQuestionModel();
        interviewQuestionModel.setTypeOfQuestion(TYPE_TRUE_FALSE);
        ArrayList<OptionModel> optionModels = new ArrayList<>();
        int index = 1;
        optionModels.add(new OptionModel(index++, "True"));
        optionModels.add(new OptionModel(index++, "False"));
        interviewQuestionModel.setOptionModels(optionModels);
        interviewQuestionModel.setQuestion("Is it True that !true is false?");
        interviewQuestionModel.setCorrectOption(1);
        interviewQuestionModel.setModelId("Model_1");
        interviewQuestionModel.setProgramLanguage("c");
        interviewQuestionModels.add(interviewQuestionModel);
    }

    private void hideShowLifeLine() {
        lifeLineLayout.setVisibility(View.GONE);
        /*int visibility = interviewQuestionModel.getOptionModels().size() == 2
                ? View.GONE
                : View.VISIBLE;
        lifeLineLayout.setVisibility(visibility);*/
    }

    private void setupViews() {
        questionTextView.setText(interviewQuestionModel.getQuestion());
        if (interviewQuestionModel.getCode() != null) {
            codeRecyclerView.setVisibility(View.VISIBLE);
            codeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            codeRecyclerView.setAdapter(new CodeEditorRecyclerAdapter(getContext(), AuxilaryUtils.splitProgramIntolines(interviewQuestionModel.getCode()), programLanguage));
        } else {
            codeRecyclerView.setVisibility(View.GONE);
        }
    }

    private void setupRecyclerView() {
        optionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        interviewQuestionsAdapter = new InterviewQuestionsAdapter(interviewQuestionModel, this);
        optionsRecyclerView.setAdapter(interviewQuestionsAdapter);
        if (interviewQuestionModel.getTypeOfQuestion() == TYPE_REARRANGE) {
            ItemTouchHelper.Callback callback =
                    new SimpleItemTouchHelperCallback(interviewQuestionsAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(optionsRecyclerView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void checkAnswer() {

        interviewQuestionsAdapter.isAnswerChecked(true);
        cancelTimer();
        //mInterviewNavigationListener.showExplanation("Some explanation");
        if (null != interviewQuestionModel.getExplanation()) {
           // mInterviewNavigationListener.showExplanation(interviewQuestionModel.getExplanation());
            navigateToNext();
        } else {
            if (interviewQuestionModel.getTypeOfQuestion() != TYPE_MULTIPLE_RIGHT && interviewQuestionModel.getTypeOfQuestion() != TYPE_REARRANGE) {
                navigateToNext();
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        navigateToNext();
                    }
                }, 2500);
            }

        }

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToNext();
            }
        }, 2500);*/
    }

    public void navigateToNext() {
        if (index < interviewQuestionModels.size()) {
            interviewQuestionModel = interviewQuestionModels.get(index++);
            setupViews();
            setupRecyclerView();
            hideShowLifeLine();
            startTimer();
        } else {
            getActivity().finish();
        }

    }

    private void cancelTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    private String programLanguage;

    public void setProgramLanguage(String programLanguage) {
        this.programLanguage = programLanguage;
    }

    @Override
    public void onDataReadComplete(ArrayList<InterviewQuestionModel> contentArrayList) {
        interviewQuestionModels = contentArrayList;
        setupMultiRightModel();
        setupRearrangeModel();
        setupSingleRightModel();
        setupTrueFalseModel();
        navigateToNext();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InterviewNavigationListener) {
            mInterviewNavigationListener = (InterviewNavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mInterviewNavigationListener = null;
    }

    @Override
    public void onItemClick(int position) {
        if (interviewQuestionModel.getTypeOfQuestion() != TYPE_MULTIPLE_RIGHT && interviewQuestionModel.getTypeOfQuestion() != TYPE_REARRANGE) {
            cancelTimer();
            /*if (null != interviewQuestionModel.getExplanation()) {
                mInterviewNavigationListener.showExplanation(interviewQuestionModel.getExplanation());
            }*/
        }
    }
}
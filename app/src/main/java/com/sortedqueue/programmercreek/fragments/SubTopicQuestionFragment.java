package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CodeEditorRecyclerAdapter;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.adapter.SubTopicsQuestionAdapter;
import com.sortedqueue.programmercreek.database.InterviewQuestionModel;
import com.sortedqueue.programmercreek.database.OptionModel;
import com.sortedqueue.programmercreek.database.SubTopics;
import com.sortedqueue.programmercreek.interfaces.OnBackPressListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;
import com.sortedqueue.programmercreek.util.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sortedqueue.programmercreek.constants.InterviewQuestionConstants.TYPE_REARRANGE;
import static com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants.REARRANGE;

/**
 * Created by Alok on 06/10/17.
 */

public class SubTopicQuestionFragment extends Fragment implements CustomProgramRecyclerViewAdapter.AdapterClickListner {


    @BindView(R.id.questionTextView)
    TextView questionTextView;
    @BindView(R.id.codeRecyclerView)
    RecyclerView codeRecyclerView;
    @BindView(R.id.optionsRecyclerView)
    RecyclerView optionsRecyclerView;
    @BindView(R.id.questionLayout)
    LinearLayout questionLayout;
    @BindView(R.id.checkAnswerImageView)
    TextView checkAnswerImageView;
    @BindView(R.id.progressTextView)
    TextView progressTextView;
    @BindView(R.id.timerProgressBar)
    ProgressBar timerProgressBar;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;
    @BindView(R.id.timerLayout)
    RelativeLayout timerLayout;
    private String programLanguage;
    private SubTopics subTopic;
    private SubTopicsQuestionAdapter subTopicsQuestionAdapter;
    private OnBackPressListener onBackPressListener;
    private int correctAnswers = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subtopic_question, container, false);
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
        questionTextView.setText(subTopic.getQuestion());

        if( subTopic.getQuestionCode() != null ) {
            codeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            codeRecyclerView.setAdapter(new CodeEditorRecyclerAdapter(getContext(), AuxilaryUtils.INSTANCE.splitProgramIntolines(subTopic.getQuestionCode()), programLanguage));
        }

        optionsRecyclerView.setLayoutManager( new LinearLayoutManager(getContext()) );
        subTopicsQuestionAdapter = new SubTopicsQuestionAdapter(getOptions(subTopic.getOptions()), subTopic.getTestMode(), this);
        subTopicsQuestionAdapter.setCorrectAnswers(subTopic.getAnswer());
        optionsRecyclerView.setAdapter( subTopicsQuestionAdapter );

        if (subTopic.getTestMode().equals(INSTANCE.getREARRANGE())) {
            ItemTouchHelper.Callback callback =
                    new SimpleItemTouchHelperCallback(subTopicsQuestionAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(optionsRecyclerView);
        }

    }

    private ArrayList<OptionModel> getOptions(String options) {
        ArrayList<OptionModel> optionModels = new ArrayList<>();
        int optionId = 1;
        for( String option : options.split(Pattern.quote("|||")) ) {
            optionModels.add(new OptionModel(optionId++, option));
        }
        return optionModels;
    }


    private void checkAnswer() {

        if( checkAnswerImageView.getText().equals("Check") ) {
            subTopicsQuestionAdapter.isAnswerChecked(true);
            checkAnswerImageView.setText("Finish");
            correctAnswers = subTopicsQuestionAdapter.countCorrectAnswers();
        }
        else {
            CreekPreferences creekPreferences = CreekApplication.Companion.getCreekPreferences();
            creekPreferences.setUnlockedSubTopic(subTopic.getSubTopicId());
            onBackPressListener.onBackPressed();
        }

    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setProgramLanguage(String programLanguage) {
        this.programLanguage = programLanguage;
    }

    public void setSubTopic(SubTopics subTopic) {
        this.subTopic = subTopic;
    }

    @Override
    public void onItemClick(int position) {

    }

    public void setOnBackPressListener(OnBackPressListener onBackPressListener) {
        this.onBackPressListener = onBackPressListener;
    }

    public void callBackClick() {
        onBackPressListener.onBackPressed();
    }
}

package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.interfaces.InterviewNavigationListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-03-08.
 */

public class InterviewChoiceFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.cQuestionsTextView)
    TextView cQuestionsTextView;
    @BindView(R.id.cppQuestionsTextView)
    TextView cppQuestionsTextView;
    @BindView(R.id.javaQuestionsTextView)
    TextView javaQuestionsTextView;
    @BindView(R.id.sqlQuestionsTextView)
    TextView sqlQuestionsTextView;
    private InterviewNavigationListener interviewNavigationListener;
    private static InterviewChoiceFragment instance;


    public static InterviewChoiceFragment getInstance() {
        if (instance == null) {
            instance = new InterviewChoiceFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interview_choice, container, false);
        ButterKnife.bind(this, view);
        setListeners();
        return view;
    }

    private void setListeners() {
        cppQuestionsTextView.setOnClickListener(this);
        cQuestionsTextView.setOnClickListener(this);
        javaQuestionsTextView.setOnClickListener(this);
        sqlQuestionsTextView.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InterviewNavigationListener) {
            interviewNavigationListener = (InterviewNavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interviewNavigationListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onClick(View view) {
        if (view instanceof TextView) {
            String language = ((TextView) view).getText().toString().replace("questions", "").trim();
            if (interviewNavigationListener != null) {
                interviewNavigationListener.onNavigateToQuestions(language);
            }
        }
    }
}

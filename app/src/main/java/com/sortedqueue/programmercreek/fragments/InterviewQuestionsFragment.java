package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.InterviewQuestinsAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-03-08.
 */

public class InterviewQuestionsFragment extends Fragment {

    @Bind(R.id.questionTextView)
    TextView questionTextView;
    @Bind(R.id.questionCardView)
    CardView questionCardView;
    @Bind(R.id.optionsRecyclerView)
    RecyclerView optionsRecyclerView;
    @Bind(R.id.questionLayout)
    LinearLayout questionLayout;
    @Bind(R.id.lifeLine1ImageView)
    ImageView lifeLine1ImageView;
    @Bind(R.id.lifeLine2ImageView)
    ImageView lifeLine2ImageView;
    @Bind(R.id.lifeLine3ImageView)
    ImageView lifeLine3ImageView;
    @Bind(R.id.lifeLineLayout)
    RelativeLayout lifeLineLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interview_questions, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        optionsRecyclerView.setLayoutManager( new LinearLayoutManager(getContext()) );
        optionsRecyclerView.setAdapter( new InterviewQuestinsAdapter() );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.MatchOptionsDragAdapter;
import com.sortedqueue.programmercreek.adapter.MatchQuestionsDropAdapter;
import com.sortedqueue.programmercreek.database.ProgramTable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-08-14.
 */

public class NewMatchFragment extends Fragment {

    @BindView(R.id.questionRecyclerView)
    RecyclerView questionRecyclerView;
    @BindView(R.id.optionRecyclerView)
    RecyclerView optionRecyclerView;
    List<ProgramTable> mProgramTableList;
    private ArrayList<String> mProgramList;
    private ArrayList<String> mProgramExplanationList;
    private ArrayList<String> mProgramCheckList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_new_match, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        optionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mProgramList = new ArrayList<String>();
        mProgramExplanationList = new ArrayList<String>();
        mProgramCheckList = new ArrayList<String>();

        questionRecyclerView.setAdapter(new MatchQuestionsDropAdapter(mProgramList));
        optionRecyclerView.setAdapter(new MatchOptionsDragAdapter(mProgramList));
    }


}

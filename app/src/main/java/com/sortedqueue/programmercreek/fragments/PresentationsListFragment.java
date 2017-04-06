package com.sortedqueue.programmercreek.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.CreatePresentationActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-04-04.
 */
public class PresentationsListFragment extends Fragment implements View.OnClickListener {

    private static PresentationsListFragment instance;
    @Bind(R.id.presentationsRecyclerView)
    RecyclerView presentationsRecyclerView;


    public static PresentationsListFragment getInstance() {
        if (instance == null) {
            instance = new PresentationsListFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_presentations, container, false);
        ButterKnife.bind(this, view);

        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        presentationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), CreatePresentationActivity.class);
        startActivity(intent);
    }
}

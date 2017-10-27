package com.sortedqueue.programmercreek.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.CreatePresentationActivity;
import com.sortedqueue.programmercreek.activity.ViewPresentationActivity;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.adapter.PresentationsListRecyclerAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.PresentationModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-04-04.
 */
public class PresentationsListFragment extends Fragment implements View.OnClickListener, FirebaseDatabaseHandler.GetAllPresentationsListener, CustomProgramRecyclerViewAdapter.AdapterClickListner {

    private static PresentationsListFragment instance;
    @BindView(R.id.presentationsRecyclerView)
    RecyclerView presentationsRecyclerView;
    private PresentationsListRecyclerAdapter adapter;


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
        new FirebaseDatabaseHandler(getContext()).getAllPresentations(this);
        return view;
    }

    private void setupRecyclerView(ArrayList<PresentationModel> presentationModelArrayList) {
        presentationsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new PresentationsListRecyclerAdapter(getContext(), presentationModelArrayList, this);
        presentationsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), CreatePresentationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccess(ArrayList<PresentationModel> presentationModelArrayList) {
        setupRecyclerView(presentationModelArrayList);
    }

    @Override
    public void onError(DatabaseError databaseError) {

    }

    @Override
    public void onItemClick(int position) {
        PresentationModel presentationModel = adapter.getItemAtPosition(position);
        Intent intent = new Intent(getContext(), ViewPresentationActivity.class);
        intent.putExtra(ProgrammingBuddyConstants.INSTANCE.getKEY_PROG_ID(), presentationModel);
        startActivity(intent);
    }
}

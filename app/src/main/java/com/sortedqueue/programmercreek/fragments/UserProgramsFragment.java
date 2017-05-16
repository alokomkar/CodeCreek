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
import com.sortedqueue.programmercreek.activity.ProgramActivity;
import com.sortedqueue.programmercreek.activity.ProgramListActivity;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.adapter.UserProgramRecyclerAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.UserProgramDetails;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 16/05/17.
 */

public class UserProgramsFragment extends Fragment implements View.OnClickListener, CustomProgramRecyclerViewAdapter.AdapterClickListner, FirebaseDatabaseHandler.GetAllUserProgramsListener {

    private static UserProgramsFragment instance;
    @Bind(R.id.presentationsRecyclerView)
    RecyclerView presentationsRecyclerView;
    private UserProgramRecyclerAdapter adapter;


    public static UserProgramsFragment getInstance() {
        if (instance == null) {
            instance = new UserProgramsFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_presentations, container, false);
        ButterKnife.bind(this, view);
        new FirebaseDatabaseHandler(getContext()).getAllUserPrograms(this);
        return view;
    }

    private void setupRecyclerView(ArrayList<UserProgramDetails> presentationModelArrayList) {
        presentationsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new UserProgramRecyclerAdapter(getContext(), presentationModelArrayList, this);
        presentationsRecyclerView.setAdapter(adapter);
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

    @Override
    public void onSuccess(ArrayList<UserProgramDetails> userProgramDetailsArrayList) {
        setupRecyclerView(userProgramDetailsArrayList);
    }

    @Override
    public void onError(DatabaseError databaseError) {

    }

    @Override
    public void onItemClick(int position) {
        UserProgramDetails presentationModel = adapter.getItemAtPosition(position);
        Bundle newIntentBundle = new Bundle();
        Intent newIntent = null;
        newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true);
        newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, presentationModel.getProgramIndex());
        newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, 1);
        newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, presentationModel.getProgramIndex().getProgram_Description());
        newIntentBundle.putParcelableArrayList(ProgrammingBuddyConstants.KEY_USER_PROGRAM, presentationModel.getProgramTables());
        newIntent = new Intent(getContext(), ProgramActivity.class);
        newIntent.putExtras(newIntentBundle);
        startActivity(newIntent);
    }
}

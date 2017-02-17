package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.ModulesRecyclerViewAdapter;
import com.sortedqueue.programmercreek.adapter.TopLearnersRecyclerAdapter;
import com.sortedqueue.programmercreek.database.LanguageModule;
import com.sortedqueue.programmercreek.database.UserRanking;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.SyntaxNavigationListener;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-02-12.
 */

public class TopLearnerFragment extends Fragment {

    @Bind(R.id.topLearnersRecyclerView)
    RecyclerView topLearnersRecyclerView;

    private SyntaxNavigationListener syntaxNavigationListener;
    private ArrayList<LanguageModule> languageModules;
    private String TAG = ModuleFragment.class.getSimpleName();
    private ModulesRecyclerViewAdapter moduleRecyclerAdapter;
    //TODO https://github.com/AdColony/AdColony-Android-SDK-3/wiki/Showing-Interstitial-Ads

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_learners, container, false);
        ButterKnife.bind(this, view);
        calculateTopLearners();
        return view;
    }

    private void calculateTopLearners() {
        new FirebaseDatabaseHandler(getContext())
                .getTopLearners(
                        new FirebaseDatabaseHandler.GetTopLearnersInterface() {
                            @Override
                            public void onSuccess(ArrayList<UserRanking> userRankings) {
                                Collections.reverse(userRankings);
                                Log.d(TAG, "Top learners : " + userRankings);
                                setupAdapter( userRankings );
                                topLearnersRecyclerView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFailure(DatabaseError databaseError) {
                                topLearnersRecyclerView.setVisibility(View.GONE);
                            }
                        });
    }

    private void setupAdapter(ArrayList<UserRanking> userRankings) {
        topLearnersRecyclerView.setLayoutManager( new GridLayoutManager(getContext(), 2) );
        topLearnersRecyclerView.setAdapter(new TopLearnersRecyclerAdapter(getContext(), userRankings));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private static TopLearnerFragment instance;

    public static TopLearnerFragment getInstance() {
        if (instance == null) {
            instance = new TopLearnerFragment();
        }
        return instance;
    }
}

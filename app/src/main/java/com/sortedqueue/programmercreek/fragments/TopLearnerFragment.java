package com.sortedqueue.programmercreek.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
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

public class TopLearnerFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.topLearnersRecyclerView)
    RecyclerView topLearnersRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.facebookCardView)
    CardView facebookCardView;
    @Bind(R.id.googleCardView)
    CardView googleCardView;
    @Bind(R.id.twitterCardView)
    CardView twitterCardView;

    private final String TWITTER_LINK = "https://twitter.com/Programmercreek";
    private final String FACEBOOK_LINK = "https://www.facebook.com/Infinite-Programmer-1819430981602209/?fref=ts";
    private final String GOOGLE_PLUS_LINK = "https://plus.google.com/u/1/communities/117275222080442676688";
    @Bind(R.id.emptyTextView)
    TextView emptyTextView;

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
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // Setup refresh listener which triggers new data loading
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                calculateTopLearners();
            }
        });

        calculateTopLearners();
        initListeners();
        return view;
    }

    private void initListeners() {
        facebookCardView.setOnClickListener(this);
        googleCardView.setOnClickListener(this);
        twitterCardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.googleCardView:
                startIntent(GOOGLE_PLUS_LINK);
                break;
            case R.id.facebookCardView:
                startIntent(FACEBOOK_LINK);
                break;
            case R.id.twitterCardView:
                startIntent(TWITTER_LINK);
                break;
        }
    }

    private void startIntent(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void calculateTopLearners() {
        swipeRefreshLayout.setRefreshing(true);
        new FirebaseDatabaseHandler(getContext())
                .getTopLearners(
                        new FirebaseDatabaseHandler.GetTopLearnersInterface() {
                            @Override
                            public void onSuccess(ArrayList<UserRanking> userRankings) {
                                Collections.reverse(userRankings);
                                Log.d(TAG, "Top learners : " + userRankings);
                                setupAdapter(userRankings);
                                topLearnersRecyclerView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFailure(DatabaseError databaseError) {
                                topLearnersRecyclerView.setVisibility(View.GONE);
                                swipeRefreshLayout.setRefreshing(false);
                                swipeRefreshLayout.setVisibility(View.GONE);
                                emptyTextView.setVisibility(View.VISIBLE);
                            }
                        });
    }

    private void setupAdapter(ArrayList<UserRanking> userRankings) {
        topLearnersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        topLearnersRecyclerView.setAdapter(new TopLearnersRecyclerAdapter(getContext(), userRankings));
        swipeRefreshLayout.setRefreshing(false);
        if( userRankings.size() == 0 ) {
            swipeRefreshLayout.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        }
        else {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
        }

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

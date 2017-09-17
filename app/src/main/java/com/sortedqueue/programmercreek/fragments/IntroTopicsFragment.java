package com.sortedqueue.programmercreek.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.interfaces.NewIntroNavigationListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class IntroTopicsFragment extends Fragment implements View.OnClickListener {


    @BindView(R.id.javaIntroCardView)
    CardView javaIntroCardView;
    @BindView(R.id.javaClassCardView)
    CardView javaClassCardView;
    @BindView(R.id.javaMainCardView)
    CardView javaMainCardView;
    @BindView(R.id.javaKeywordsCardView)
    CardView javaKeywordsCardView;
    @BindView(R.id.javaLoopsCardView)
    CardView javaLoopsCardView;
    @BindView(R.id.javaStatementsCardView)
    CardView javaStatementsCardView;
    @BindView(R.id.javaOperatorsCardView)
    CardView javaOperatorsCardView;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public IntroTopicsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_topics, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupToolbar();
        javaIntroCardView.setOnClickListener(this);
        javaClassCardView.setOnClickListener(this);
        javaMainCardView.setOnClickListener(this);
        javaKeywordsCardView.setOnClickListener(this);
        javaLoopsCardView.setOnClickListener(this);
        javaStatementsCardView.setOnClickListener(this);
        javaOperatorsCardView.setOnClickListener(this);
        return view;
    }

    private void setupToolbar() {

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.intro) + " to " + CreekApplication.getCreekPreferences().getProgramLanguage().toUpperCase());
        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if( item.getItemId() == android.R.id.home ) {
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private NewIntroNavigationListener mNewIntroNavigationListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewIntroNavigationListener) {
            mNewIntroNavigationListener = (NewIntroNavigationListener) context;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.javaIntroCardView:
                break;
            case R.id.javaClassCardView:
                break;
            case R.id.javaMainCardView:
                break;
            case R.id.javaLoopsCardView:
                break;
            case R.id.javaKeywordsCardView:
                break;
            case R.id.javaStatementsCardView:
                break;
            case R.id.javaOperatorsCardView:
                break;
        }
        mNewIntroNavigationListener.loadTopicDetailsFragment("Any topic");
    }
}

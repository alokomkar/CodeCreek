package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.AlgorithmPagerAdapter;
import com.sortedqueue.programmercreek.database.Algorithm;
import com.sortedqueue.programmercreek.database.AlgorithmsIndex;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.AlgorithmNavigationListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-03-18.
 */

public class AlgorithmFragment extends Fragment implements FirebaseDatabaseHandler.GetAlgorithmListener {

    @Bind(R.id.algorithmTabLayout)
    TabLayout algorithmTabLayout;
    @Bind(R.id.algorithmViewPager)
    ViewPager algorithmViewPager;
    private AlgorithmNavigationListener algorithmNavigationListener;
    private static AlgorithmsIndex mAlgorithmsIndex;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AlgorithmNavigationListener) {
            algorithmNavigationListener = (AlgorithmNavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        algorithmNavigationListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_algorithm, container, false);
        ButterKnife.bind(this, view);
        fetchAlgorithmsIndex();
        return view;
    }

    private void fetchAlgorithmsIndex() {
        new FirebaseDatabaseHandler(getContext()).getAlgorithmForIndex(mAlgorithmsIndex.getProgramIndex(), this);
    }

    public static AlgorithmFragment newInstance(AlgorithmsIndex algorithm) {
        mAlgorithmsIndex = algorithm;
        return new AlgorithmFragment();
    }

    @Override
    public void onSuccess(Algorithm algorithm) {
        algorithmViewPager.setAdapter( new AlgorithmPagerAdapter(getChildFragmentManager(), algorithm));
        algorithmTabLayout.setupWithViewPager(algorithmViewPager);
    }

    @Override
    public void onError(DatabaseError databaseError) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

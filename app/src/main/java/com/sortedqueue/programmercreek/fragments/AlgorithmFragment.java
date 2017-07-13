package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.content.Intent;
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
import com.sortedqueue.programmercreek.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Alok Omkar on 2017-03-18.
 */

public class AlgorithmFragment extends Fragment implements FirebaseDatabaseHandler.GetAlgorithmListener {

    @BindView(R.id.algorithmTabLayout)
    TabLayout algorithmTabLayout;
    @BindView(R.id.algorithmViewPager)
    ViewPager algorithmViewPager;
    private AlgorithmNavigationListener algorithmNavigationListener;
    private static AlgorithmsIndex mAlgorithmsIndex;
    private Algorithm algorithm;
    private String TAG = AlgorithmFragment.class.getSimpleName();

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
        this.algorithm = algorithm;
        algorithmViewPager.setAdapter( new AlgorithmPagerAdapter(getChildFragmentManager(), algorithm));
        algorithmTabLayout.setupWithViewPager(algorithmViewPager);
    }

    @Override
    public void onError(DatabaseError databaseError) {
        CommonUtils.displaySnackBar(getActivity(), R.string.unable_to_fetch_data, R.string.retry, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAlgorithmsIndex();
            }
        });
    }

    public void shareAlgorithm() {
        String algorithmString = algorithm.toAlgorithmString();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, algorithmString);
        startActivity(Intent.createChooser(shareIntent, "Share Algorithm"));
    }
}

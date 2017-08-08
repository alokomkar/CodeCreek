package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.AllProgramsPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Alok on 08/08/17.
 */

public class AllProgramsFragment extends Fragment {

    @BindView(R.id.allProgramsTabLayout)
    TabLayout allProgramsTabLayout;
    @BindView(R.id.programsViewPager)
    ViewPager programsViewPager;
    Unbinder unbinder;

    private static AllProgramsFragment allProgramsFragment;
    public static AllProgramsFragment getInstance() {
        if( allProgramsFragment == null ) allProgramsFragment = new AllProgramsFragment();
        return allProgramsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_all_programs, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Fragment> userProgramFragments = new ArrayList<>();
        userProgramFragments.add( UserProgramsFragment.newInstance("C"));
        userProgramFragments.add( UserProgramsFragment.newInstance("C++"));
        userProgramFragments.add( UserProgramsFragment.newInstance("Java"));
        programsViewPager.setAdapter(new AllProgramsPagerAdapter(getChildFragmentManager(), userProgramFragments));
        allProgramsTabLayout.setupWithViewPager(programsViewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

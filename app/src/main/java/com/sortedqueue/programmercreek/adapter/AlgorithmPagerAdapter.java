package com.sortedqueue.programmercreek.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sortedqueue.programmercreek.constants.AlgorithmConstants;
import com.sortedqueue.programmercreek.database.Algorithm;
import com.sortedqueue.programmercreek.database.AlgorithmContent;
import com.sortedqueue.programmercreek.database.AlgorithmsIndex;
import com.sortedqueue.programmercreek.fragments.AllAlgorithmContentFragment;

import java.util.ArrayList;

/**
 * Created by Alok Omkar on 2017-03-18.
 */

public class AlgorithmPagerAdapter extends FragmentPagerAdapter {

    private Algorithm algorithm;
    private ArrayList<Fragment> fragments;
    public AlgorithmPagerAdapter(FragmentManager fm, Algorithm algorithm) {
        super(fm);
        this.algorithm = algorithm;
        fragments = new ArrayList<>();
        for(AlgorithmContent algorithmContent : algorithm.getAlgorithmContentArrayList()) {
            AllAlgorithmContentFragment allAlgorithmContentFragment = new AllAlgorithmContentFragment();
            allAlgorithmContentFragment.setParameter(algorithmContent, algorithm.getAlgorithmsIndex().getProgramLanguage());
            fragments.add(allAlgorithmContentFragment);
        }

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return algorithm.getAlgorithmContentArrayList().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return algorithm.getAlgorithmContentArrayList().get(position).getTabTitle();
    }
}

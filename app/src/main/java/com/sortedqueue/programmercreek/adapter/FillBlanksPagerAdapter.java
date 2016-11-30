package com.sortedqueue.programmercreek.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sortedqueue.programmercreek.fragments.FillBlankFragment;

import java.util.ArrayList;

/**
 * Created by cognitive on 11/30/16.
 */
public class FillBlanksPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<FillBlankFragment> fillBlankFragments = new ArrayList<>();

    public FillBlanksPagerAdapter(FragmentManager fm) {
        super(fm);
        fillBlankFragments.add(new FillBlankFragment());
        fillBlankFragments.add(new FillBlankFragment());
        fillBlankFragments.add(new FillBlankFragment());
        fillBlankFragments.add(new FillBlankFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fillBlankFragments.get(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}

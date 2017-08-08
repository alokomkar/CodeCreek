package com.sortedqueue.programmercreek.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;

/**
 * Created by Alok on 08/08/17.
 */

public class AllProgramsPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments;

    public AllProgramsPagerAdapter(FragmentManager childFragmentManager, ArrayList<Fragment> fragments) {
        super(childFragmentManager);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0 :
                return "C";
            case 1 :
                return "C++";
            case 2 :
                return "Java";
            default:
                return "C";
        }
    }
}

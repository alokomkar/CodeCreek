package com.sortedqueue.programmercreek.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sortedqueue.programmercreek.fragments.TutorialModelFragment;

import java.util.ArrayList;

/**
 * Created by Alok on 15/05/17.
 */

public class TutorialSlidesPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<TutorialModelFragment> fragmentArrayList;


    public TutorialSlidesPagerAdapter(FragmentManager fm, ArrayList<TutorialModelFragment> fragmentArrayList) {
        super(fm);
        this.fragmentArrayList = fragmentArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
}

package com.sortedqueue.programmercreek.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Alok Omkar on 2016-12-31.
 */

public class ProgramWikiPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<ProgramWikiFragment> ProgramWikiFragments = new ArrayList<>();

    public ProgramWikiPagerAdapter(FragmentManager fm) {
        super(fm);
        ProgramWikiFragment ProgramWikiFragment = new ProgramWikiFragment();
        ProgramWikiFragments.add(ProgramWikiFragment);
        ProgramWikiFragment = new ProgramWikiFragment();
        ProgramWikiFragments.add(ProgramWikiFragment);
        ProgramWikiFragment = new ProgramWikiFragment();
        ProgramWikiFragments.add(ProgramWikiFragment);
        ProgramWikiFragment = new ProgramWikiFragment();
        ProgramWikiFragments.add(ProgramWikiFragment);

    }

    @Override
    public Fragment getItem(int position) {
        return ProgramWikiFragments.get(position);
    }

    @Override
    public int getCount() {
        return ProgramWikiFragments.size();
    }
}

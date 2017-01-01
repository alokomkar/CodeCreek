package com.sortedqueue.programmercreek.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sortedqueue.programmercreek.database.ProgramWiki;
import com.sortedqueue.programmercreek.database.WikiModel;

import java.util.ArrayList;

/**
 * Created by Alok Omkar on 2016-12-31.
 */

public class ProgramWikiPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<ProgramWikiFragment> ProgramWikiFragments = new ArrayList<>();
    private ArrayList<WikiModel> programWikis;

    public ProgramWikiPagerAdapter(FragmentManager fm, ArrayList<WikiModel> programWikis) {
        super(fm);
        this.programWikis = programWikis;
        for( WikiModel programWiki : programWikis ) {
            ProgramWikiFragment ProgramWikiFragment = new ProgramWikiFragment();
            ProgramWikiFragment.setParams( programWiki );
            ProgramWikiFragments.add(ProgramWikiFragment);
        }


    }

    @Override
    public Fragment getItem(int position) {
        return ProgramWikiFragments.get(position);
    }

    @Override
    public int getCount() {
        return programWikis.size();
    }
}

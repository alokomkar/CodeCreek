package com.sortedqueue.programmercreek.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sortedqueue.programmercreek.database.LanguageModule;
import com.sortedqueue.programmercreek.database.SyntaxModule;
import com.sortedqueue.programmercreek.fragments.SyntaxLearnActivityFragment;

import java.util.ArrayList;

/**
 * Created by cognitive on 11/30/16.
 */
public class SyntaxPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<SyntaxLearnActivityFragment> syntaxLearnActivityFragments = new ArrayList<>();

    public SyntaxPagerAdapter(FragmentManager fm, LanguageModule module, ArrayList<SyntaxModule> syntaxModules) {
        super(fm);
        int index = 2;
        for( SyntaxModule syntaxModule : syntaxModules ) {
            SyntaxLearnActivityFragment fillBlankFragment = new SyntaxLearnActivityFragment();
            fillBlankFragment.setSyntaxModule( syntaxModule );
            syntaxLearnActivityFragments.add(fillBlankFragment);
        }

    }

    @Override
    public Fragment getItem(int position) {
        return syntaxLearnActivityFragments.get(position);
    }

    @Override
    public int getCount() {
        return syntaxLearnActivityFragments.size();
    }
}

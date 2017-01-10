package com.sortedqueue.programmercreek.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sortedqueue.programmercreek.database.LanguageModule;
import com.sortedqueue.programmercreek.database.SyntaxModule;
import com.sortedqueue.programmercreek.fragments.SyntaxLearnActivityFragment;
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener;

import java.util.ArrayList;

/**
 * Created by cognitive on 11/30/16.
 */
public class SyntaxPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<SyntaxLearnActivityFragment> syntaxLearnActivityFragments;
    private ModuleDetailsScrollPageListener moduleDetailsScrollPageListener;
    public SyntaxPagerAdapter(FragmentManager fm, LanguageModule module, LanguageModule nextModule, ArrayList<SyntaxModule> syntaxModules, ModuleDetailsScrollPageListener moduleDetailsScrollPageListener) {
        super(fm);
        this.moduleDetailsScrollPageListener = moduleDetailsScrollPageListener;
        syntaxLearnActivityFragments = new ArrayList<>();
        int position = 0;
        for( SyntaxModule syntaxModule : syntaxModules ) {
            position++;
            SyntaxLearnActivityFragment syntaxLearnActivityFragment = new SyntaxLearnActivityFragment();
            syntaxLearnActivityFragment.setSyntaxModule( syntaxModule );
            syntaxLearnActivityFragment.setModulteDetailsScrollPageListener( moduleDetailsScrollPageListener );
            syntaxLearnActivityFragment.setIsLastFragment( position == syntaxModules.size(), nextModule );
            syntaxLearnActivityFragments.add(syntaxLearnActivityFragment);
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

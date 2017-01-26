package com.sortedqueue.programmercreek.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.fragments.CodeViewFragment;
import com.sortedqueue.programmercreek.fragments.SubTestFragment;

import java.util.ArrayList;

/**
 * Created by Alok Omkar on 2017-01-26.
 */

public class SubTestPagerAdapter extends FragmentPagerAdapter {

    private CodeViewFragment codeViewFragment;
    private ArrayList<ProgramTable>[] programTableArray;
    private ArrayList<Fragment> subTestFragments;

    public SubTestPagerAdapter(FragmentManager fm, ArrayList<ProgramTable>[] programTableArray) {
        super(fm);
        this.programTableArray = programTableArray;
        subTestFragments = new ArrayList<>();
        int index = 0;
        for( ArrayList<ProgramTable> programTables : programTableArray ) {
            SubTestFragment subTestFragment = new SubTestFragment();
            subTestFragment.setProgramTables( programTables );
            subTestFragment.setIndex(index++);
            subTestFragments.add(subTestFragment);
        }
        codeViewFragment = new CodeViewFragment();
        subTestFragments.add(codeViewFragment);

    }

    public CodeViewFragment getCodeViewFragment() {
        return codeViewFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return subTestFragments.get(position);
    }

    @Override
    public int getCount() {
        return programTableArray.length + 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if( position == programTableArray.length ) {
            return "Output";
        }
        ArrayList<ProgramTable> programTables = programTableArray[position];

        String pageTitle = programTables.get(0).getLine_No() + " - " + programTables.get(programTables.size() - 1).getLine_No();
        return pageTitle;
    }

}

package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sortedqueue.programmercreek.fragments.DashboardFragment;
import com.sortedqueue.programmercreek.fragments.LanguageFragment;

/**
 * Created by Alok on 02/01/17.
 */

public class DashboardPagerAdapter extends FragmentPagerAdapter {

    public static final int INDEX_LANGUAGE = 0;
    public static final int INDEX_DASHBOARD = 1;
    private Context context;
    private String tabTitles[] = new String[] { "Language", "Dashboard" };

    public DashboardPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch ( position ) {
            case INDEX_LANGUAGE :
                return LanguageFragment.getInstance();
            case INDEX_DASHBOARD :
                return DashboardFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}

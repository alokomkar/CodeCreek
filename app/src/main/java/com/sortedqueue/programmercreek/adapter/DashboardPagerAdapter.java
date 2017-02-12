package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.fragments.DashboardFragment;
import com.sortedqueue.programmercreek.fragments.LanguageFragment;
import com.sortedqueue.programmercreek.fragments.TopLearnerFragment;

/**
 * Created by Alok on 02/01/17.
 */

public class DashboardPagerAdapter extends FragmentPagerAdapter {

    public static final int INDEX_LANGUAGE = 0;
    public static final int INDEX_DASHBOARD = 1;
    public static final int INDEX_LEADER_BOARD = 2;

    private Context context;
    private String tabTitles[] = new String[] { "Language", "Dashboard", "Top Learners" };

    private int[] imageResId = {
            R.drawable.ic_account_box_white_24dp,
            R.drawable.ic_dns_white_24dp,
            R.drawable.ic_star_border_black_24dp
    };

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
            case INDEX_LEADER_BOARD :
                return TopLearnerFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }
}

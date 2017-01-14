package com.sortedqueue.programmercreek.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sortedqueue.programmercreek.fragments.ChapterDetailsFragment;
import com.sortedqueue.programmercreek.fragments.ChaptersFragment;
import com.sortedqueue.programmercreek.interfaces.ChapterNavigationListener;

/**
 * Created by Alok Omkar on 2017-01-14.
 */

public class ChapterPagerAdapter extends FragmentPagerAdapter {

    private ChapterDetailsFragment chapterDetailsFragment;
    private ChapterNavigationListener chapterNavigationListener;
    public ChapterPagerAdapter(FragmentManager fm, ChapterNavigationListener chapterNavigationListener) {
        super(fm);
        this.chapterNavigationListener = chapterNavigationListener;
    }

    @Override
    public Fragment getItem(int position) {
        if( position == 0 ) {
            return new ChaptersFragment();
        }
        else {
            chapterDetailsFragment = new ChapterDetailsFragment();
            return chapterDetailsFragment;
        }
    }

    public ChapterDetailsFragment getChapterDetailsFragment() {
        return chapterDetailsFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}

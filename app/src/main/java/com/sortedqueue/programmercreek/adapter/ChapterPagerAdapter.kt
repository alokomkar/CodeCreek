package com.sortedqueue.programmercreek.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.sortedqueue.programmercreek.fragments.ChapterDetailsFragment
import com.sortedqueue.programmercreek.fragments.ChaptersFragment
import com.sortedqueue.programmercreek.interfaces.ChapterNavigationListener

/**
 * Created by Alok Omkar on 2017-01-14.
 */

class ChapterPagerAdapter(fm: FragmentManager, private val chapterNavigationListener: ChapterNavigationListener) : FragmentPagerAdapter(fm) {

    var chapterDetailsFragment: ChapterDetailsFragment? = null
        private set

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return ChaptersFragment()
        } else {
            chapterDetailsFragment = ChapterDetailsFragment()
            return chapterDetailsFragment!!
        }
    }

    override fun getCount(): Int {
        return 2
    }
}

package com.sortedqueue.programmercreek.v2.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.sortedqueue.programmercreek.fragments.TopLearnerFragment
import com.sortedqueue.programmercreek.fragments.UserProgramsFragment
import com.sortedqueue.programmercreek.v2.ui.chapters.ChaptersFragment

class HomePagerAdapter( fm : FragmentManager ) : FragmentStatePagerAdapter( fm ) {

    override fun getItem(position: Int): Fragment {
        return when( position ) {
            0 -> ChaptersFragment()
            1 -> TopLearnerFragment.instance
            2 -> UserProgramsFragment.instance
            else -> ChaptersFragment()
        }
    }

    override fun getCount(): Int = 3
}
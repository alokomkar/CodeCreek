package com.sortedqueue.programmercreek.dashboard

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.sortedqueue.programmercreek.fragments.TopLearnerFragment
import com.sortedqueue.programmercreek.fragments.UserProgramsFragment

/**
 * Created by Alok on 02/01/17.
 */

class DashboardPagerAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        when (position) {
            INDEX_DASHBOARD -> return DashboardFragment.instance
            INDEX_LEADER_BOARD -> return TopLearnerFragment.instance
            INDEX_USER_PROGRAMS -> return UserProgramsFragment.instance
        }
        return null
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return ""
    }

    companion object {
        val INDEX_DASHBOARD = 0
        val INDEX_LEADER_BOARD = 1
        val INDEX_USER_PROGRAMS = 2
    }
}

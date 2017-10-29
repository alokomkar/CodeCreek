package com.sortedqueue.programmercreek.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.fragments.AllProgramsFragment
import com.sortedqueue.programmercreek.fragments.DashboardFragment
import com.sortedqueue.programmercreek.fragments.LanguageFragment
import com.sortedqueue.programmercreek.fragments.TopLearnerFragment
import com.sortedqueue.programmercreek.fragments.UserProgramsFragment

/**
 * Created by Alok on 02/01/17.
 */

class DashboardPagerAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {
    private val tabTitles = arrayOf("Language", "Dashboard", "Top Learners")

    private val imageResId = intArrayOf(R.drawable.ic_account_box_white_24dp, R.drawable.ic_dns_white_24dp, R.drawable.ic_star_border_black_24dp)

    override fun getItem(position: Int): Fragment? {
        when (position) {
        /*case INDEX_LANGUAGE :
                return LanguageFragment.getInstance();*/
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

        /*public static final int INDEX_LANGUAGE = 0;*/
        val INDEX_DASHBOARD = 0
        val INDEX_LEADER_BOARD = 1
        val INDEX_USER_PROGRAMS = 2
    }
}

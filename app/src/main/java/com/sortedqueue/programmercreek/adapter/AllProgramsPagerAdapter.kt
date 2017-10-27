package com.sortedqueue.programmercreek.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter

import java.util.ArrayList

/**
 * Created by Alok on 08/08/17.
 */

class AllProgramsPagerAdapter(childFragmentManager: FragmentManager, private val fragments: ArrayList<Fragment>) : FragmentStatePagerAdapter(childFragmentManager) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return "C"
            1 -> return "C++"
            2 -> return "Java"
            else -> return "C"
        }
    }
}

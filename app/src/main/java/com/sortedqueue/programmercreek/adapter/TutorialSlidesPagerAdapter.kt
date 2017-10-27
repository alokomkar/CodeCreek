package com.sortedqueue.programmercreek.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import java.util.ArrayList

/**
 * Created by Alok on 15/05/17.
 */

class TutorialSlidesPagerAdapter(fm: FragmentManager, private val fragmentArrayList: ArrayList<Fragment>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragmentArrayList[position]
    }

    override fun getCount(): Int {
        return fragmentArrayList.size
    }
}

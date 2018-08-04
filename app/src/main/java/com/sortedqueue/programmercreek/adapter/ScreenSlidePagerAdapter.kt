package com.sortedqueue.programmercreek.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter

import com.sortedqueue.programmercreek.fragments.CreateSlideFragment

import java.util.ArrayList

/**
 * Created by Alok on 06/04/17.
 */
class ScreenSlidePagerAdapter(supportFragmentManager: FragmentManager, val allItems: ArrayList<Fragment>) : FragmentStatePagerAdapter(supportFragmentManager) {

    override fun getItem(position: Int): Fragment {
        return allItems[position]
    }

    override fun getCount(): Int {
        return allItems.size
    }

    fun addNewSlideFragment(createSlideFragment: CreateSlideFragment) {
        allItems.add(createSlideFragment)
    }

    fun removeCurrentFragment(currentPosition: Int) {
        allItems.removeAt(currentPosition)
    }

    override fun getItemPosition(`object`: Any): Int {
        val index = allItems.indexOf(`object`)

        if (index == -1)
            return PagerAdapter.POSITION_NONE
        else
            return index
    }
}

package com.sortedqueue.programmercreek.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import com.sortedqueue.programmercreek.database.SlideModel
import com.sortedqueue.programmercreek.fragments.ViewSlideFragment

import java.util.ArrayList

/**
 * Created by Alok on 11/04/17.
 */

class ViewSlidesPagerAdapter(fm: FragmentManager, private val slideModelArrayList: ArrayList<SlideModel>) : FragmentStatePagerAdapter(fm) {
    private val fragmentArrayList: ArrayList<ViewSlideFragment>


    init {
        this.fragmentArrayList = ArrayList<ViewSlideFragment>()
        for (slideModel in slideModelArrayList) {
            val viewSlideFragment = ViewSlideFragment()
            viewSlideFragment.setParameter(slideModel)
            fragmentArrayList.add(viewSlideFragment)
        }
    }

    override fun getItem(position: Int): Fragment {
        return fragmentArrayList[position]
    }

    override fun getCount(): Int {
        return slideModelArrayList.size
    }
}

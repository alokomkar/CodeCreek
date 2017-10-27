package com.sortedqueue.programmercreek.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.sortedqueue.programmercreek.constants.AlgorithmConstants
import com.sortedqueue.programmercreek.database.Algorithm
import com.sortedqueue.programmercreek.database.AlgorithmContent
import com.sortedqueue.programmercreek.database.AlgorithmsIndex
import com.sortedqueue.programmercreek.fragments.AllAlgorithmContentFragment

import java.util.ArrayList

/**
 * Created by Alok Omkar on 2017-03-18.
 */

class AlgorithmPagerAdapter(fm: FragmentManager, private val algorithm: Algorithm) : FragmentPagerAdapter(fm) {
    private val fragments: ArrayList<Fragment>

    init {
        fragments = ArrayList<Fragment>()
        for (algorithmContent in algorithm.algorithmContentArrayList) {
            val allAlgorithmContentFragment = AllAlgorithmContentFragment()
            allAlgorithmContentFragment.setParameter(algorithmContent, algorithm.algorithmsIndex.programLanguage)
            fragments.add(allAlgorithmContentFragment)
        }

    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return algorithm.algorithmContentArrayList.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return algorithm.algorithmContentArrayList[position].tabTitle
    }
}

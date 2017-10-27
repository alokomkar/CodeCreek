package com.sortedqueue.programmercreek.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.sortedqueue.programmercreek.database.WikiModel
import com.sortedqueue.programmercreek.fragments.ProgramWikiFragment

import java.util.ArrayList

/**
 * Created by Alok Omkar on 2016-12-31.
 */

class ProgramWikiPagerAdapter(fm: FragmentManager, private val programWikis: ArrayList<WikiModel>) : FragmentPagerAdapter(fm) {

    private val ProgramWikiFragments = ArrayList<ProgramWikiFragment>()

    init {
        for (programWiki in programWikis) {
            val ProgramWikiFragment = ProgramWikiFragment()
            ProgramWikiFragment.setParams(programWiki)
            ProgramWikiFragments.add(ProgramWikiFragment)
        }


    }

    override fun getItem(position: Int): Fragment {
        return ProgramWikiFragments[position]
    }

    override fun getCount(): Int {
        return programWikis.size
    }
}

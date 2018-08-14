package com.sortedqueue.programmercreek.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter

import com.sortedqueue.programmercreek.database.QuickReference
import com.sortedqueue.programmercreek.fragments.ReferenceFragment

import java.util.ArrayList

/**
 * Created by Alok on 11/08/17.
 */

class QuickReferencePagerAdapter(childFragmentManager: FragmentManager, quickReferences: ArrayList<QuickReference>, language: String) : FragmentStatePagerAdapter(childFragmentManager) {

    private var referenceFragments = ArrayList<ReferenceFragment>()

    init {
        for (quickReference in quickReferences) {
            referenceFragments.add(ReferenceFragment.newInstance(quickReference, language))
        }
    }

    override fun getItem(position: Int): Fragment {
        return referenceFragments[position]
    }

    override fun getCount(): Int {
        return referenceFragments.size
    }
}

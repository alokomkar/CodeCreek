package com.sortedqueue.programmercreek.v2.ui.contentshare

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.sortedqueue.programmercreek.v2.data.helper.Content
import java.util.ArrayList

class SharePagerAdapter (childFragmentManager: FragmentManager,
                         contentList: ArrayList<Content> ) :
        FragmentStatePagerAdapter(childFragmentManager) {

    private var fragments = ArrayList<ContentFragment>()

    init {
        for (quickReference in contentList) {
            fragments.add(ContentFragment.newInstance(quickReference))
        }
    }

    override fun getItem(position: Int): ContentFragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}
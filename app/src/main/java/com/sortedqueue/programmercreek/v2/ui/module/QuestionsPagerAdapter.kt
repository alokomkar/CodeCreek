package com.sortedqueue.programmercreek.v2.ui.module

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.sortedqueue.programmercreek.v2.data.helper.SimpleContent
import java.util.*

class QuestionsPagerAdapter(private val contentList : ArrayList<SimpleContent>,
                            supportFragmentManager: FragmentManager ) : FragmentStatePagerAdapter( supportFragmentManager ) {

    private val mFragmentsList = ArrayList<ModuleQuestionsFragment>()
    init {
        for( content in contentList ) {
            val fragment = ModuleQuestionsFragment()
            val bundle = Bundle()
            bundle.putParcelable(SimpleContent::class.java.simpleName, content)
            fragment.arguments = bundle
            mFragmentsList.add(fragment)
        }
    }

    override fun getItem(position: Int): Fragment = mFragmentsList[position]

    override fun getCount(): Int = contentList.size
}
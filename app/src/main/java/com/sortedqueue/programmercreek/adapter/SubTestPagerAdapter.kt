package com.sortedqueue.programmercreek.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.fragments.CodeViewFragment
import com.sortedqueue.programmercreek.fragments.SubTestFragment
import com.sortedqueue.programmercreek.interfaces.SubTestCommunicationListener

import java.util.ArrayList

/**
 * Created by Alok Omkar on 2017-01-26.
 */

class SubTestPagerAdapter(fm: FragmentManager, private val programTableArray: Array<ArrayList<ProgramTable>>, subTestCommunicationListener: SubTestCommunicationListener) : FragmentPagerAdapter(fm) {

    val codeViewFragment: CodeViewFragment
    private val subTestFragments: ArrayList<Fragment>

    init {
        subTestFragments = ArrayList<Fragment>()
        var index = 0
        for (programTables in programTableArray) {
            val subTestFragment = SubTestFragment()
            subTestFragment.setProgramTables(programTables)
            subTestFragment.setIndex(index++)
            subTestFragment.setSubmitTestCommunicationListener(subTestCommunicationListener)
            subTestFragments.add(subTestFragment)
        }
        codeViewFragment = CodeViewFragment()
        codeViewFragment.setTotalModules(programTableArray.size)
        subTestFragments.add(codeViewFragment)

    }

    override fun getItem(position: Int): Fragment {
        return subTestFragments[position]
    }

    override fun getCount(): Int {
        return programTableArray.size + 1
    }

    override fun getPageTitle(position: Int): CharSequence {
        if (position == programTableArray.size) {
            return "Preview"
        }
        if (programTableArray.size == 1) {
            return "Test"
        }
        val programTables = programTableArray[position]

        val pageTitle = programTables[0].line_No.toString() + " - " + programTables[programTables.size - 1].line_No
        return pageTitle
    }

}

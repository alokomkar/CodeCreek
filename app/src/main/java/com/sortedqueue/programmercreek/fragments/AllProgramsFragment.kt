package com.sortedqueue.programmercreek.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.AllProgramsPagerAdapter

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * Created by Alok on 08/08/17.
 */

class AllProgramsFragment : Fragment() {

    @BindView(R.id.allProgramsTabLayout)
    internal var allProgramsTabLayout: TabLayout? = null
    @BindView(R.id.programsViewPager)
    internal var programsViewPager: ViewPager? = null
    internal var unbinder: Unbinder ?= null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_all_programs, container, false)
        unbinder = ButterKnife.bind(this, fragmentView)
        return fragmentView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userProgramFragments = ArrayList<Fragment>()
        userProgramFragments.add(UserProgramsFragment.newInstance("C"))
        userProgramFragments.add(UserProgramsFragment.newInstance("C++"))
        userProgramFragments.add(UserProgramsFragment.newInstance("Java"))
        programsViewPager!!.adapter = AllProgramsPagerAdapter(childFragmentManager, userProgramFragments)
        allProgramsTabLayout!!.setupWithViewPager(programsViewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
    }

    companion object {

        private var allProgramsFragment: AllProgramsFragment? = null
        val instance: AllProgramsFragment
            get() {
                if (allProgramsFragment == null) allProgramsFragment = AllProgramsFragment()
                return allProgramsFragment!!
            }
    }
}

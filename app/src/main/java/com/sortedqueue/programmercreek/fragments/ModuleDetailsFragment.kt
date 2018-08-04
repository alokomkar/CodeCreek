package com.sortedqueue.programmercreek.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.activity.SyntaxLearnActivity
import com.sortedqueue.programmercreek.adapter.SyntaxPagerAdapter
import com.sortedqueue.programmercreek.database.LanguageModule
import com.sortedqueue.programmercreek.database.SyntaxModule
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener
import com.sortedqueue.programmercreek.interfaces.SyntaxNavigationListener
import com.sortedqueue.programmercreek.view.ScrollableViewPager
import kotlinx.android.synthetic.main.fragment_module_details.*

import java.util.ArrayList




/**
 * Created by Alok on 26/12/16.
 */

class ModuleDetailsFragment : Fragment(), ModuleDetailsScrollPageListener {

    private var module: LanguageModule? = null
    private var nextModule: LanguageModule? = null
    private var syntaxModules: ArrayList<SyntaxModule>? = null
    private var syntaxPagerAdapter: SyntaxPagerAdapter? = null
    private var syntaxNavigationListener: SyntaxNavigationListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_module_details, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {

        syntaxLearnViewPager!!.offscreenPageLimit = syntaxModules!!.size
        syntaxPagerAdapter = SyntaxPagerAdapter(childFragmentManager, module!!, nextModule, syntaxModules!!, this)
        syntaxLearnViewPager!!.adapter = syntaxPagerAdapter
        syntaxLearnViewPager!!.setCanScroll(true)
        ProgressBar!!.max = syntaxModules!!.size
        ProgressBar!!.progress = 1
        doneFAB!!.setOnClickListener { onScrollForward() }
        toggleFabDrawable(ProgressBar!!.progress)
        syntaxLearnViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                ProgressBar!!.progress = position + 1
                toggleFabDrawable(ProgressBar!!.progress)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }

    private fun toggleFabDrawable(progress: Int) {
        val drawable = if (progress == ProgressBar!!.max) R.drawable.ic_done_all else android.R.drawable.ic_media_play
        doneFAB!!.setImageDrawable(ContextCompat.getDrawable(context!!, drawable))
        syntaxNavigationListener!!.setImageDrawable(drawable)
    }

    fun setParameters(module: LanguageModule, syntaxModules: ArrayList<SyntaxModule>, nextModule: LanguageModule?) {
        this.module = module
        this.syntaxModules = syntaxModules
        this.nextModule = nextModule
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onScrollForward() {
        if (syntaxLearnViewPager!!.currentItem + 1 == syntaxPagerAdapter!!.count) {
            activity!!.onBackPressed()
        } else {
            syntaxLearnViewPager!!.currentItem = syntaxLearnViewPager!!.currentItem + 1
        }

    }

    override fun toggleFABDrawable() {
        toggleFabDrawable(ProgressBar!!.progress - 1)
    }

    fun setSyntaxNavigationListener(syntaxNavigationListener: SyntaxNavigationListener) {
        this.syntaxNavigationListener = syntaxNavigationListener
    }
}

package com.sortedqueue.programmercreek.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.adapter.LessonsAdapter
import com.sortedqueue.programmercreek.asynctask.LessonFetchTask

import com.sortedqueue.programmercreek.database.lessons.Lesson
import com.sortedqueue.programmercreek.interfaces.ChapterNavigationListener
import com.sortedqueue.programmercreek.interfaces.LessonNavigationListener
import com.sortedqueue.programmercreek.util.AnimationUtils
import com.sortedqueue.programmercreek.util.CommonUtils

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok on 29/08/17.
 */

class LessonsFragment : Fragment(), LessonFetchTask.LessonFetcherTaskListener {

    //TODO https://github.com/AdColony/AdColony-Android-SDK-3/wiki/Showing-Interstitial-Ads
    @BindView(R.id.modulesRecyclerView)
    internal var lessonsRecyclerView: RecyclerView? = null
    @BindView(R.id.adView)
    internal var adView: AdView? = null

    private val TAG = LessonsFragment::class.java.simpleName
    private var lessons: ArrayList<Lesson>? = null
    private var lessonsAdapter: LessonsAdapter? = null
    private var lessonNavigationListener: LessonNavigationListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_lessons, container, false)
        ButterKnife.bind(this, view)
        getModules()
        if (!CreekApplication.creekPreferences!!.isPremiumUser) {
            initAds()
        }
        return view
    }

    private fun initAds() {
        /*if( CreekApplication.getPreferences().getAdsEnabled() )*/ run {
            MobileAds.initialize(context, getString(R.string.mobile_banner_id))
            //For actual ads : AdRequest adRequest = new AdRequest.Builder().build();
            //For creating test ads
            val adRequest = AdRequest.Builder()
                    .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                    .build()
            adView!!.loadAd(adRequest)
            adView!!.visibility = View.GONE
            adView!!.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    adView!!.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is LessonNavigationListener) {
            lessonNavigationListener = context
        }
    }

    override fun onDetach() {
        lessonNavigationListener = null
        super.onDetach()
    }

    private fun getModules() {
        lessonsRecyclerView!!.visibility = View.INVISIBLE
        CommonUtils.displayProgressDialog(context, "Loading chapters")
        LessonFetchTask(this).execute()

    }

    override fun onResume() {
        super.onResume()
        if (lessonsAdapter != null) {
            lessonsAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onSuccess(lessons: ArrayList<Lesson>) {
        this.lessons = lessons
        this.lessonsAdapter = LessonsAdapter(context, lessons, object : CustomProgramRecyclerViewAdapter.AdapterClickListner {

            override fun onItemClick(position: Int) {
                lessonNavigationListener!!.onLessonSelected(lessonsAdapter!!.getItem(position))
            }
        })
        lessonsRecyclerView!!.layoutManager = GridLayoutManager(context, 2)
        lessonsRecyclerView!!.adapter = lessonsAdapter
        CommonUtils.dismissProgressDialog()
        Handler().postDelayed({ AnimationUtils.slideInToLeft(lessonsRecyclerView) }, 450)
    }
}

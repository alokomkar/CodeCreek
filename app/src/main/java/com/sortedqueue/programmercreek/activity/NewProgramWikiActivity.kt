package com.sortedqueue.programmercreek.activity

import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.adapter.ProgramWikiNavRecyclerAdapter
import com.sortedqueue.programmercreek.adapter.ProgramWikiPagerAdapter
import com.sortedqueue.programmercreek.database.WikiModel
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.view.ScrollableViewPager

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by Alok Omkar on 2016-12-31.
 */

class NewProgramWikiActivity : AppCompatActivity(), View.OnClickListener, CustomProgramRecyclerViewAdapter.AdapterClickListner {

    @BindView(R.id.programWikiViewPager)
    internal var programWikiViewPager: ScrollableViewPager? = null
    @BindView(R.id.ProgressBar)
    internal var progressBar: ProgressBar? = null
    @BindView(R.id.firstQuestionImageView)
    internal var firstQuestionImageView: ImageView? = null
    @BindView(R.id.prevQuestionImageView)
    internal var prevQuestionImageView: ImageView? = null
    @BindView(R.id.indexTextView)
    internal var indexTextView: TextView? = null
    @BindView(R.id.nextQuestionImageView)
    internal var nextQuestionImageView: ImageView? = null
    @BindView(R.id.lastQuestionImageView)
    internal var lastQuestionImageView: ImageView? = null
    @BindView(R.id.navigationLayout)
    internal var navigationLayout: RelativeLayout? = null
    @BindView(R.id.programRecyclerView)
    internal var programRecyclerView: RecyclerView? = null
    @BindView(R.id.nav_view)
    internal var navView: NavigationView? = null
    @BindView(R.id.drawer_layout)
    internal var drawerLayout: DrawerLayout? = null
    private var programWikiPagerAdapter: ProgramWikiPagerAdapter? = null
    private var interstitialAd: InterstitialAd? = null
    private var toolbar: Toolbar? = null

    override fun onResume() {
        super.onResume()
        CreekApplication.instance.isAppRunning = true
    }

    override fun onPause() {
        super.onPause()
        CreekApplication.instance.isAppRunning = false
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_program_wiki)
        ButterKnife.bind(this)
        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar!!.title = "Program Wiki : " + CreekApplication.creekPreferences!!.programLanguage.toUpperCase()
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout!!.addDrawerListener(toggle)
        toggle.syncState()

        CommonUtils.displayProgressDialog(this@NewProgramWikiActivity, "Loading")
        FirebaseDatabaseHandler(this@NewProgramWikiActivity).initializeProgramWiki(
                object : FirebaseDatabaseHandler.ProgramWikiInterface {
                    override fun getProgramWiki(programWikis: ArrayList<WikiModel>) {
                        setupNavRecyclerView(programWikis)
                        programWikiPagerAdapter = ProgramWikiPagerAdapter(supportFragmentManager, programWikis)
                        programWikiViewPager!!.adapter = programWikiPagerAdapter
                        programWikiViewPager!!.setCanScroll(false)
                        progressBar!!.max = programWikis.size
                        progressBar!!.progress = 1
                        indexTextView!!.text = 1.toString() + "/" + programWikiPagerAdapter!!.count
                        toggleVisiblity(0)
                        programWikiViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                            }

                            override fun onPageSelected(position: Int) {
                                progressBar!!.progress = position + 1
                                toggleVisiblity(position)
                                indexTextView!!.text = (position + 1).toString() + "/" + programWikiPagerAdapter!!.count
                            }

                            override fun onPageScrollStateChanged(state: Int) {

                            }
                        })
                        setupNavigationListener()
                        CommonUtils.dismissProgressDialog()
                    }


                    override fun onError(error: DatabaseError) {
                        CommonUtils.dismissProgressDialog()
                    }
                })
        if (!CreekApplication.creekPreferences!!.isPremiumUser) {
            initAds()
        }
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)


    }

    private fun setupNavRecyclerView(programWikis: ArrayList<WikiModel>) {
        programRecyclerView!!.layoutManager = LinearLayoutManager(this@NewProgramWikiActivity, LinearLayoutManager.VERTICAL, false)
        programRecyclerView!!.adapter = ProgramWikiNavRecyclerAdapter(this@NewProgramWikiActivity, programWikis)
    }

    private fun initAds() {
        MobileAds.initialize(applicationContext, getString(R.string.mobile_banner_id))
        interstitialAd = InterstitialAd(this)
        interstitialAd!!.adUnitId = getString(R.string.interstital_wiki_ad_id)
        interstitialAd!!.adListener = object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                finish()
            }

            override fun onAdFailedToLoad(i: Int) {
                super.onAdFailedToLoad(i)
            }

            override fun onAdLeftApplication() {
                super.onAdLeftApplication()
            }
        }
        requestNewInterstitial()

    }

    private fun requestNewInterstitial() {
        val adRequest = AdRequest.Builder()
                .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                .build()
        interstitialAd!!.loadAd(adRequest)
    }

    private fun setupNavigationListener() {
        firstQuestionImageView!!.setOnClickListener(this)
        prevQuestionImageView!!.setOnClickListener(this)
        nextQuestionImageView!!.setOnClickListener(this)
        lastQuestionImageView!!.setOnClickListener(this)
    }

    private fun toggleVisiblity(position: Int) {
        firstQuestionImageView!!.visibility = View.VISIBLE
        prevQuestionImageView!!.visibility = View.VISIBLE
        lastQuestionImageView!!.visibility = View.VISIBLE
        nextQuestionImageView!!.visibility = View.VISIBLE

        if (position == 0) {
            firstQuestionImageView!!.visibility = View.GONE
            prevQuestionImageView!!.visibility = View.GONE
        } else if (position == programWikiPagerAdapter!!.count - 1) {
            lastQuestionImageView!!.visibility = View.GONE
            nextQuestionImageView!!.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.firstQuestionImageView -> programWikiViewPager!!.currentItem = 0
            R.id.prevQuestionImageView -> programWikiViewPager!!.currentItem = programWikiViewPager!!.currentItem - 1
            R.id.nextQuestionImageView -> programWikiViewPager!!.currentItem = programWikiViewPager!!.currentItem + 1
            R.id.lastQuestionImageView -> programWikiViewPager!!.currentItem = programWikiPagerAdapter!!.count - 1
        }
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }

    internal var isAdShown = false

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
            return
        }
        if (!isAdShown && interstitialAd != null && interstitialAd!!.isLoaded && !CreekApplication.creekPreferences!!.isPremiumUser) {
            interstitialAd!!.show()
            isAdShown = true
            return
        }
        finish()
    }

    override fun onItemClick(position: Int) {
        if (programWikiViewPager != null) {
            programWikiViewPager!!.currentItem = position
            drawerLayout!!.closeDrawer(GravityCompat.START)
        }
    }
}

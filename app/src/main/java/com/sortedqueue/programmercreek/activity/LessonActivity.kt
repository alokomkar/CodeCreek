package com.sortedqueue.programmercreek.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.database.lessons.Lesson
import com.sortedqueue.programmercreek.fragments.LessonDetailsFragment
import com.sortedqueue.programmercreek.fragments.LessonsFragment
import com.sortedqueue.programmercreek.interfaces.LessonNavigationListener
import com.sortedqueue.programmercreek.util.AnimationUtils
import com.sortedqueue.programmercreek.util.CreekPreferences

import butterknife.BindView
import butterknife.ButterKnife
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by Alok on 29/08/17.
 */

class LessonActivity : AppCompatActivity(), View.OnClickListener, LessonNavigationListener {

    @BindView(R.id.toolbar)
    internal var toolbar: Toolbar? = null
    @BindView(R.id.shareTextView)
    internal var shareTextView: TextView? = null
    @BindView(R.id.shareNowTextView)
    internal var shareNowTextView: TextView? = null
    @BindView(R.id.laterTextView)
    internal var laterTextView: TextView? = null
    @BindView(R.id.shareLayout)
    internal var shareLayout: RelativeLayout? = null
    @BindView(R.id.reputationProgressBar)
    internal var reputationProgressBar: ProgressBar? = null
    @BindView(R.id.reputationTextView)
    internal var reputationTextView: TextView? = null
    @BindView(R.id.progressLayout)
    internal var progressLayout: LinearLayout? = null
    @BindView(R.id.container)
    internal var container: FrameLayout? = null

    private var mFragmentTransaction: FragmentTransaction? = null
    private var lessonsFragment: LessonsFragment? = null
    private val isFirstTime: Boolean = false
    private var lessonDetailsFragment: LessonDetailsFragment? = null
    private var interstitialAd: InterstitialAd? = null
    private var currentLesson: Lesson? = null
    private var handler: Handler? = null
    private var creekPreferences: CreekPreferences? = null
    private var creekUserStats: CreekUserStats? = null
    private var runnable: Runnable? = null
    private var previousLevel: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        loadLessons()
        if (!CreekApplication.creekPreferences!!.isPremiumUser) {
            initAds()
        }
        shareNowTextView!!.setOnClickListener(this)
        laterTextView!!.setOnClickListener(this)
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)

    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
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

    override fun onClick(v: View) {
        when (v.id) {
            R.id.shareNowTextView -> {
                shareNow("")
                AnimationUtils.slideOutToLeft(shareLayout)
            }
            R.id.laterTextView -> AnimationUtils.slideOutToLeft(shareLayout)
        }
    }

    private fun shareNow(shareMessage: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage + "\n\nCheck out this app : \n" + getString(R.string.app_url))
        startActivity(Intent.createChooser(shareIntent, "Level up on : " + getString(R.string.app_name) + " App"))
        if (shareLayout!!.visibility != View.GONE)
            shareLayout!!.visibility = View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadLessons() {
        supportActionBar!!.title = "Bits & Bytes : " + CreekApplication.creekPreferences!!.programLanguage.toUpperCase()
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        lessonsFragment = supportFragmentManager.findFragmentByTag(LessonsFragment::class.java.simpleName) as LessonsFragment
        if (lessonsFragment == null) {
            lessonsFragment = LessonsFragment()
        }
        mFragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        mFragmentTransaction!!.replace(R.id.container, lessonsFragment, LessonsFragment::class.java.simpleName)
        mFragmentTransaction!!.commit()
    }

    override fun onLessonSelected(lesson: Lesson) {
        supportActionBar!!.title = lesson.title
        currentLesson = lesson
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        lessonDetailsFragment = supportFragmentManager.findFragmentByTag(LessonDetailsFragment::class.java.simpleName) as LessonDetailsFragment
        if (lessonDetailsFragment == null) {
            lessonDetailsFragment = LessonDetailsFragment()
        }
        lessonDetailsFragment!!.setLesson(lesson)

        mFragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        mFragmentTransaction!!.replace(R.id.container, lessonDetailsFragment, LessonDetailsFragment::class.java.simpleName)
        mFragmentTransaction!!.commit()
    }

    override fun onProgessStatsUpdate(points: Int) {
        progressLayout!!.visibility = View.VISIBLE
        animateProgress(points)
    }

    private var progressBarStatus: Int = 0

    fun animateProgress(points: Int) {
        try {
            if (reputationProgressBar != null) {

                if (handler == null) {
                    handler = Handler()
                }
                if (creekPreferences == null) {
                    creekPreferences = CreekApplication.creekPreferences
                }
                creekUserStats = creekPreferences!!.creekUserStats
                if (creekUserStats == null) {
                    reputationProgressBar!!.visibility = View.GONE
                    reputationTextView!!.visibility = View.GONE
                    progressLayout!!.visibility = View.GONE
                    return
                }
                val progress = creekUserStats!!.creekUserReputation % 100
                reputationProgressBar!!.visibility = View.VISIBLE
                reputationTextView!!.visibility = View.VISIBLE
                runnable = Runnable {
                    progressBarStatus = 0
                    while (progressBarStatus <= progress) {

                        handler!!.post {
                            if (reputationProgressBar != null) {
                                reputationProgressBar!!.progress = progressBarStatus

                                reputationTextView!!.text = "You've gained " + points + "xp\n" + progressBarStatus + "% Complete"
                                val level = creekUserStats!!.creekUserReputation / 100
                                if (level > 0) {
                                    reputationTextView!!.text = "You've gained " + points + "xp\n" + progressBarStatus + "% Complete : Level : " + level
                                }
                            }
                        }
                        try {
                            Thread.sleep(40)
                        } catch (ex: Exception) {
                        }

                        progressBarStatus++
                    }
                    handler!!.postDelayed({
                        progressLayout!!.visibility = View.GONE
                        val level = creekUserStats!!.creekUserReputation / 100
                        if (level > 0) {
                            showShareLayout(level)
                        }
                    }, 1500)
                }
                Thread(runnable).start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (progressLayout != null) {
                progressLayout!!.visibility = View.GONE
            }
        }

    }

    private fun showShareLayout(level: Int) {
        previousLevel = creekPreferences!!.level
        if (previousLevel < level) {
            previousLevel = level - 1
            shareTextView!!.text = "Congratulations on cracking the level " + (level - 1) + ". You are moving on to next level. Let's share your progress..."
            AnimationUtils.slideInToLeft(shareLayout)
            creekPreferences!!.level = level
        }
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }

    internal var isAdShown = false

    override fun onBackPressed() {

        val title = supportActionBar!!.title!!.toString()
        if (currentLesson != null && title == currentLesson!!.title) {
            if (lessonDetailsFragment!!.currentFragment == null) {
                loadLessons()
            } else {
                if (lessonDetailsFragment!!.currentFragment.isTestLoaded) {
                    lessonDetailsFragment!!.currentFragment.onBackPressed()
                } else {
                    loadLessons()
                }
            }

        } else {
            if (!isAdShown && interstitialAd != null && interstitialAd!!.isLoaded && !CreekApplication.creekPreferences!!.isPremiumUser) {
                interstitialAd!!.show()
                isAdShown = true
                return
            }
            finish()

        }
    }
}

package com.sortedqueue.programmercreek.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.Chapter
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.fragments.ChapterDetailsFragment
import com.sortedqueue.programmercreek.fragments.ChaptersFragment
import com.sortedqueue.programmercreek.interfaces.ChapterNavigationListener
import com.sortedqueue.programmercreek.util.AnimationUtils
import com.sortedqueue.programmercreek.util.CreekPreferences
import com.startapp.android.publish.adsCommon.StartAppAd



import kotlinx.android.synthetic.main.activity_wizard_module.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/*import com.tappx.sdk.android.TappxAdError;
import com.tappx.sdk.android.TappxInterstitial;
import com.tappx.sdk.android.TappxInterstitialListener;*/

/**
 * Created by Alok on 05/01/17.
 */

class ChaptersActivity : AppCompatActivity(), ChapterNavigationListener, View.OnClickListener {

    //TODO https://github.com/AdColony/AdColony-Android-SDK-3/wiki/Showing-Interstitial-Ads

    private var mFragmentTransaction: FragmentTransaction? = null
    private var chapterDetailsFragment: ChapterDetailsFragment? = null
    private var chaptersFragment: ChaptersFragment? = null
    private var handler: Handler? = null
    private var creekPreferences: CreekPreferences? = null
    private var creekUserStats: CreekUserStats? = null
    private var runnable: Runnable? = null
    private var previousLevel: Int = 0

    override fun onResume() {
        super.onResume()
        CreekApplication.instance.isAppRunning = (true)
    }

    override fun onPause() {
        super.onPause()
        CreekApplication.instance.isAppRunning = (false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wizard_module)

        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        loadChapterFragment()
        //loadTappxFullScreenAd();
        checkFAB!!.setOnClickListener(this)
        shareNowTextView!!.setOnClickListener(this)
        laterTextView!!.setOnClickListener(this)
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)
    }

    override fun onDestroy() {
        super.onDestroy()
        //if (tappxInterstitial != null) tappxInterstitial.destroy();
    }

    private var isFirstTime = true

    private fun loadChapterFragment() {
        supportActionBar!!.title = "Chapters : " + CreekApplication.creekPreferences!!.programLanguage.toUpperCase()
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        if (chaptersFragment == null) {
            chaptersFragment = ChaptersFragment()
        }
        checkFAB!!.setImageDrawable(ContextCompat.getDrawable(this@ChaptersActivity, android.R.drawable.ic_media_play))
        if (isFirstTime) {
            checkFAB?.visibility = View.GONE
            isFirstTime = false
        } else {
            AnimationUtils.exitReveal(checkFAB)
        }
        mFragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        mFragmentTransaction!!.replace(R.id.container, chaptersFragment!!, ChaptersFragment::class.java.simpleName)
        mFragmentTransaction!!.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_syntax_learn, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (shareLayout!!.visibility == View.VISIBLE) {
            AnimationUtils.slideOutToLeft(shareLayout)
        }
        val title = supportActionBar!!.title!!.toString()
        if (title != "Chapters : " + CreekApplication.creekPreferences!!.programLanguage.toUpperCase()) {
            loadChapterFragment()
        } else {
            if (CreekApplication.creekPreferences!!.adsEnabled) {
                StartAppAd.onBackPressed(this)
                super.onBackPressed()
            } else {
                finish()
            }
        }
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onChapterSelected(chapter: Chapter, nextChapter: Chapter?) {
        Log.d("ChaptersActivity", "Selected chapter : " + chapter.toString())
        supportActionBar!!.title = chapter.chapterName
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        if (chapterDetailsFragment == null) {
            chapterDetailsFragment = ChapterDetailsFragment()
        }
        //checkFAB.setVisibility(View.VISIBLE);
        AnimationUtils.enterReveal(checkFAB)
        chapterDetailsFragment!!.setChapter(chapter)
        chapterDetailsFragment!!.setNextChapter(nextChapter)
        chapterDetailsFragment!!.setOnChapterNavigationListener(this)
        mFragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        mFragmentTransaction!!.replace(R.id.container, chapterDetailsFragment!!, ChapterDetailsFragment::class.java.simpleName)
        mFragmentTransaction!!.commit()
    }

    override fun toggleFabDrawable(drawable: Int) {
        checkFAB!!.setImageDrawable(ContextCompat.getDrawable(this@ChaptersActivity, drawable))
    }

    override fun onProgessStatsUpdate(points: Int) {
        progressLayout!!.visibility = View.VISIBLE
        animateProgress(points)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.checkFAB -> if (chapterDetailsFragment != null) {
                chapterDetailsFragment!!.onScrollForward()
            }
            R.id.shareNowTextView -> {
                shareNow()
                AnimationUtils.slideOutToLeft(shareLayout)
            }
            R.id.laterTextView -> AnimationUtils.slideOutToLeft(shareLayout)
        }

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

    private fun shareNow() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey Friends, I've just completed level " + previousLevel + " on " + getString(R.string.app_name) + "\n\nCheck out this app : \n" + getString(R.string.app_url))
        startActivity(Intent.createChooser(shareIntent, "Level up on : " + getString(R.string.app_name) + " App"))
        if (shareLayout!!.visibility != View.GONE)
            shareLayout!!.visibility = View.GONE
    }


}

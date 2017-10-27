package com.sortedqueue.programmercreek.activity

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.fragments.NewFillBlankFragment
import com.sortedqueue.programmercreek.fragments.NewMatchFragment
import com.sortedqueue.programmercreek.fragments.QuizFragment
import com.sortedqueue.programmercreek.fragments.TestDragNDropFragment
import com.sortedqueue.programmercreek.interfaces.WizardNavigationListener
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.startapp.android.publish.adsCommon.Ad
import com.startapp.android.publish.adsCommon.StartAppAd
import com.startapp.android.publish.adsCommon.VideoListener
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class WizardActivity : AppCompatActivity(), WizardNavigationListener {

    @BindView(R.id.container)
    internal var container: FrameLayout? = null
    private var mFragmentTransaction: FragmentTransaction? = null
    private var matchMakerFragment: NewMatchFragment? = null
    private var testDragNDropFragment: TestDragNDropFragment? = null
    private var quizFragment: QuizFragment? = null
    private var fillBlankFragment: NewFillBlankFragment? = null
    private val isAdShown: Boolean = false

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.wizard, menu)
        return true
    }

    private var isRewardVideoShown = false
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_hint) {
            if (!isRewardVideoShown && !CreekApplication.creekPreferences!!.isPremiumUser)
                showRewardedVideoDialog()
            else
                showSolutionFromFragment()
        }
        return true


    }

    fun showRewardedVideoDialog() {
        AuxilaryUtils.displayInformation(this@WizardActivity, R.string.hint_video, R.string.reward_video_description,
                { dialogInterface, i -> showRewardedClick() }

        ) { showRewardedClick() }
    }

    private fun showRewardedClick() {
        val rewardedVideo = StartAppAd(this@WizardActivity)

        /**
         * This is very important: set the video listener to be triggered after video
         * has finished playing completely
         */
        rewardedVideo.setVideoListener {
            isRewardVideoShown = true
            showSolutionFromFragment()
        }

        /**
         * Load rewarded by specifying AdMode.REWARDED
         * We are using AdEventListener to trigger ad show
         */
        rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, object : AdEventListener {

            override fun onReceiveAd(arg0: Ad) {
                rewardedVideo.showAd()
            }

            override fun onFailedToReceiveAd(arg0: Ad) {
                /**
                 * Failed to load rewarded video:
                 * 1. Check that FullScreenActivity is declared in AndroidManifest.xml:
                 * See https://github.com/StartApp-SDK/Documentation/wiki/Android-InApp-Documentation#activities
                 * 2. Is android API level above 16?
                 */
                Log.e("MainActivity", "Failed to load rewarded video with reason: " + arg0.errorMessage)
            }
        })
    }

    private fun showSolutionFromFragment() {
        if (matchMakerFragment != null) {
            showSolutionDialog(matchMakerFragment!!.getmProgramList())
        } else if (testDragNDropFragment != null) {
            showSolutionDialog(testDragNDropFragment!!.getmProgramList())
        } else if (fillBlankFragment != null) {
            //showSolutionDialog( fillBlankFragment.getmProgramList() );
            fillBlankFragment!!.addHintsToBlanks()
        } else if (quizFragment != null) {
            showSolutionDialog(quizFragment!!.getmProgramList())
        }
    }

    private fun showSolutionDialog(solutionList: ArrayList<String>) {
        var solution = ""
        for (string in solutionList) {
            solution += string + "\n"
        }
        Log.d("SolutionProgram", solution)
        AuxilaryUtils.displayAlert("Solution", solution, this@WizardActivity)
    }

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
        setContentView(R.layout.activity_wizard)
        ButterKnife.bind(this)
        val bundle = intent.extras
        when (bundle!!.getInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST)) {
            ProgrammingBuddyConstants.KEY_MATCH -> loadMatchMakerFragment(bundle)
            ProgrammingBuddyConstants.KEY_TEST -> loadTestFragment(bundle)
            ProgrammingBuddyConstants.KEY_QUIZ -> loadQuizFragment(bundle)
            ProgrammingBuddyConstants.KEY_FILL_BLANKS -> loadFillBlanksFragment(bundle)
        }

        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)

    }

    override fun loadMatchMakerFragment(bundle: Bundle) {
        isRewardVideoShown = false
        title = "Match : " + bundle.getString(ProgrammingBuddyConstants.KEY_PROG_TITLE, "")
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        matchMakerFragment = NewMatchFragment()
        matchMakerFragment!!.setBundle(bundle)
        mFragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        mFragmentTransaction!!.replace(R.id.container, matchMakerFragment, NewMatchFragment::class.java.simpleName)
        mFragmentTransaction!!.commit()
    }

    override fun loadTestFragment(bundle: Bundle) {
        isRewardVideoShown = false
        title = "Test : " + (bundle.getParcelable<Parcelable>(ProgrammingBuddyConstants.KEY_PROG_ID) as ProgramIndex).program_Description
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        testDragNDropFragment = supportFragmentManager.findFragmentByTag(TestDragNDropFragment::class.java.simpleName) as TestDragNDropFragment
        if (testDragNDropFragment == null) {
            testDragNDropFragment = TestDragNDropFragment()
        }
        testDragNDropFragment!!.setBundle(bundle)
        mFragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        mFragmentTransaction!!.replace(R.id.container, testDragNDropFragment, TestDragNDropFragment::class.java.simpleName)
        mFragmentTransaction!!.commit()
    }

    override fun loadQuizFragment(bundle: Bundle) {
        isRewardVideoShown = false
        title = "Quiz : " + (bundle.getParcelable<Parcelable>(ProgrammingBuddyConstants.KEY_PROG_ID) as ProgramIndex).program_Description
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        quizFragment = supportFragmentManager.findFragmentByTag(QuizFragment::class.java.simpleName) as QuizFragment
        if (quizFragment == null) {
            quizFragment = QuizFragment()
        }
        quizFragment!!.setBundle(bundle)
        mFragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        mFragmentTransaction!!.replace(R.id.container, quizFragment, QuizFragment::class.java.simpleName)
        mFragmentTransaction!!.commit()
    }

    override fun loadFillBlanksFragment(bundle: Bundle) {
        isRewardVideoShown = false
        title = "Fill blanks : " + (bundle.getParcelable<Parcelable>(ProgrammingBuddyConstants.KEY_PROG_ID) as ProgramIndex).program_Description
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        fillBlankFragment = supportFragmentManager.findFragmentByTag(NewFillBlankFragment::class.java.simpleName) as NewFillBlankFragment
        if (fillBlankFragment == null) {
            fillBlankFragment = NewFillBlankFragment()
        }
        fillBlankFragment!!.setBundle(bundle)
        fillBlankFragment!!.setmProgram_Index((bundle.getParcelable<Parcelable>(ProgrammingBuddyConstants.KEY_PROG_ID) as ProgramIndex).program_index)
        fillBlankFragment!!.setWizardMode(true)
        mFragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        mFragmentTransaction!!.replace(R.id.container, fillBlankFragment, NewFillBlankFragment::class.java.simpleName)
        mFragmentTransaction!!.commit()
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }


    override fun onBackPressed() {
        val title = title.toString()
        if (title.startsWith("Match")) {
            if (matchMakerFragment != null) {
                matchMakerFragment!!.onBackPressed()
            }
        } else if (title.startsWith("Test")) {
            if (testDragNDropFragment != null) {
                testDragNDropFragment!!.onBackPressed()
            }
        } else if (title.startsWith("Quiz")) {
            if (quizFragment != null) {
                quizFragment!!.onBackPressed()
            }
        } else if (title.startsWith("Fill")) {
            if (fillBlankFragment != null) {
                fillBlankFragment!!.onBackPressed()
            }
        } else
            super.onBackPressed()

    }

}

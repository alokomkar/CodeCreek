package com.sortedqueue.programmercreek.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.fragments.InterviewChoiceFragment
import com.sortedqueue.programmercreek.fragments.InterviewQuestionsFragment
import com.sortedqueue.programmercreek.interfaces.InterviewNavigationListener



import kotlinx.android.synthetic.main.activity_interview.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by Alok Omkar on 2017-03-08.
 */
@SuppressLint("CommitTransaction")
class InterviewActivity : AppCompatActivity(), InterviewNavigationListener {

    private var mFragmentTransaction: FragmentTransaction? = null
    private var interviewQuestionsFragment: InterviewQuestionsFragment? = null
    private var interviewChoiceFragment: InterviewChoiceFragment? = null
    private var programLanguage: String? = null

    override fun onResume() {
        super.onResume()
        CreekApplication.instance.isAppRunning = (true)
    }

    override fun onPause() {
        super.onPause()
        CreekApplication.instance.isAppRunning = (false)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview)

        checkFAB!!.visibility = View.GONE
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        onNavigateToChoice()
        nextTextView!!.setOnClickListener {
            hintLayout!!.visibility = View.GONE
            if (interviewQuestionsFragment != null)
                interviewQuestionsFragment!!.navigateToNext()
        }
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)
    }


    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    fun fabClick(view: View) {
        if (interviewQuestionsFragment != null) {
            interviewQuestionsFragment!!.checkAnswer()
        }
    }


    override fun onNavigateToQuestions(programLanguage: String) {
        supportActionBar!!.title = "$programLanguage Questions"
        this.programLanguage = programLanguage
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        interviewQuestionsFragment = supportFragmentManager.findFragmentByTag(InterviewQuestionsFragment::class.java.simpleName) as InterviewQuestionsFragment?
        if (interviewQuestionsFragment == null) {
            interviewQuestionsFragment = InterviewQuestionsFragment()
        }
        //AnimationUtils.enterReveal(checkFAB);
        interviewQuestionsFragment!!.setProgramLanguage(programLanguage)
        mFragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        mFragmentTransaction!!.replace(R.id.container, interviewQuestionsFragment, InterviewQuestionsFragment::class.java.simpleName)
        mFragmentTransaction!!.commit()
    }

    private val isFirstTime = true

    override fun onNavigateToChoice() {
        /*if (isFirstTime) {
            checkFAB.setVisibility(View.GONE);
            isFirstTime = false;
        } else {
            AnimationUtils.exitReveal(checkFAB);
        }*/
        hintLayout!!.visibility = View.GONE
        supportActionBar!!.title = "Interview Questions"
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        interviewChoiceFragment = InterviewChoiceFragment.instance
        mFragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        mFragmentTransaction!!.replace(R.id.container, interviewChoiceFragment, InterviewChoiceFragment::class.java.simpleName)
        mFragmentTransaction!!.commit()
    }

    override fun showExplanation(explanation: String) {
        hintLayout!!.visibility = View.VISIBLE
        explanationTextView!!.text = explanation
    }

    override fun onBackPressed() {
        val title = supportActionBar!!.title!!.toString()
        if (title == programLanguage!! + " Questions") {
            onNavigateToChoice()
        } else {
            finish()
        }
    }


}

package com.sortedqueue.programmercreek.activity

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.firebase.Code
import com.sortedqueue.programmercreek.fragments.ChaptersFragment
import com.sortedqueue.programmercreek.fragments.CodeEditorFragment
import com.sortedqueue.programmercreek.fragments.CodeLanguageFragment
import com.sortedqueue.programmercreek.fragments.CompileCodeFragment
import com.sortedqueue.programmercreek.interfaces.CodeLabNavigationListener
import com.sortedqueue.programmercreek.util.AnimationUtils



import kotlinx.android.synthetic.main.activity_wizard_module.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by Alok on 04/04/17.
 */

class CodeLabActivity : AppCompatActivity(), CodeLabNavigationListener, View.OnClickListener {

    //TODO https://github.com/AdColony/AdColony-Android-SDK-3/wiki/Showing-Interstitial-Ads

    private var mFragmentTransaction: FragmentTransaction? = null
    private var codeLanguageFragment: CodeLanguageFragment? = null
    private var compileCodeFragment: CompileCodeFragment? = null
    private val bundle: Bundle? = null
    private val TAG = CodeLabActivity::class.java.simpleName

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

        /*bundle = getIntent().getExtras();
        if( bundle == null ) {
            loadCompileCodeFragment(null);
        }
        else {
            Code code = bundle.getParcelable(ProgrammingBuddyConstants.KEY_PROG_ID);
            loadCompileCodeFragment(code);
        }*/
        loadCodeEditorFragment()
        checkFAB!!.setOnClickListener(this)
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)
    }

    private fun loadCodeEditorFragment() {
        supportActionBar!!.title = "Code Editor"
        mFragmentTransaction = supportFragmentManager.beginTransaction()

        checkFAB!!.setImageDrawable(ContextCompat.getDrawable(this@CodeLabActivity, android.R.drawable.ic_media_play))
        AnimationUtils.enterReveal(checkFAB)
        mFragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        mFragmentTransaction!!.replace(R.id.container, CodeEditorFragment(), CodeEditorFragment::class.java.simpleName)
        mFragmentTransaction!!.commit()
    }


    private var isFirstTime = true
    override fun loadCompileCodeFragment(code: Code) {
        supportActionBar!!.title = "Code Lab : Hello world"
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        compileCodeFragment = supportFragmentManager.findFragmentByTag(ChaptersFragment::class.java.simpleName) as CompileCodeFragment
        if (compileCodeFragment == null) {
            compileCodeFragment = CompileCodeFragment()
        }
        compileCodeFragment!!.setParameter(code)
        checkFAB!!.setImageDrawable(ContextCompat.getDrawable(this@CodeLabActivity, android.R.drawable.ic_media_play))
        AnimationUtils.enterReveal(checkFAB)
        mFragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        mFragmentTransaction!!.replace(R.id.container, compileCodeFragment, ChaptersFragment::class.java.simpleName)
        mFragmentTransaction!!.commit()
    }

    override fun loadCodeLanguagesFragment() {
        supportActionBar!!.title = "Code Lab"
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        codeLanguageFragment = supportFragmentManager.findFragmentByTag(CodeLanguageFragment::class.java.simpleName) as CodeLanguageFragment
        if (codeLanguageFragment == null) {
            codeLanguageFragment = CodeLanguageFragment.instance
        }
        checkFAB!!.setImageDrawable(ContextCompat.getDrawable(this@CodeLabActivity, android.R.drawable.ic_media_play))
        if (isFirstTime) {
            checkFAB!!.visibility = View.GONE
            isFirstTime = false
        } else {
            AnimationUtils.exitReveal(checkFAB)
        }
        mFragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        mFragmentTransaction!!.replace(R.id.container, codeLanguageFragment, CodeLanguageFragment::class.java.simpleName)
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
        /*String title = getSupportActionBar().getTitle().toString();
        Log.d(TAG, "Title : " + title);
        if( bundle == null ) {
            if (!title.equals("Code Lab")) {
                loadCodeLanguagesFragment();
            } else {
                finish();
            }
        }
        else {
            finish();
        }*/
        finish()

    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onClick(view: View) {
        if (compileCodeFragment != null) {
            compileCodeFragment!!.executeProgram()
        }
    }
}

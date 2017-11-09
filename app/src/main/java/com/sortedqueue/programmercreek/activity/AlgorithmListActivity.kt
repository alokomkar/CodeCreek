package com.sortedqueue.programmercreek.activity

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.AlgorithmsIndex
import com.sortedqueue.programmercreek.fragments.AlgorithmFragment
import com.sortedqueue.programmercreek.interfaces.AlgorithmNavigationListener
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.startapp.android.publish.adsCommon.StartAppAd



import kotlinx.android.synthetic.main.activity_wizard_module.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by Alok Omkar on 2017-03-17.
 */

class AlgorithmListActivity : AppCompatActivity(), AlgorithmNavigationListener {

    private var mFragmentTransaction: FragmentTransaction? = null

    private var algorithmFragment: AlgorithmFragment? = null

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
        checkFAB!!.visibility = View.GONE
        loadAlgorithmFragment(intent.getParcelableExtra<Parcelable>(ProgrammingBuddyConstants.KEY_PROG_ID) as AlgorithmsIndex)
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)

    }


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_algorithm_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        } else if (item.itemId == R.id.action_share) {
            shareProgram()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareProgram() {
        if (algorithmFragment != null) {
            algorithmFragment!!.shareAlgorithm()
        }
    }

    override fun loadAlgorithmFragment(algorithm: AlgorithmsIndex) {
        supportActionBar!!.title = algorithm.programTitle
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        mFragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        algorithmFragment = AlgorithmFragment.newInstance(algorithm)
        mFragmentTransaction!!.replace(R.id.container, algorithmFragment, AlgorithmFragment::class.java.simpleName)
        mFragmentTransaction!!.commit()
    }

    override fun onBackPressed() {
        if (CreekApplication.creekPreferences!!.adsEnabled) {
            StartAppAd.onBackPressed(this)
            super.onBackPressed()
        } else {
            finish()
        }

    }
}

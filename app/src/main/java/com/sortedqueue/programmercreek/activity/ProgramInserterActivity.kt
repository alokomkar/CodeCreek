package com.sortedqueue.programmercreek.activity

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.RelativeLayout

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.fragments.ProgramInserterFragment




class ProgramInserterActivity : AppCompatActivity() {

    private var mFragmentTransaction: FragmentTransaction? = null
    private var programInserterFragment: ProgramInserterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program_inserter)

        loadProgramInserterFragment()
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)
    }

    override fun onResume() {
        super.onResume()
        CreekApplication.instance.isAppRunning = (true)
    }

    override fun onPause() {
        super.onPause()
        CreekApplication.instance.isAppRunning = (false)
    }

    private fun loadProgramInserterFragment() {
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        programInserterFragment = supportFragmentManager.findFragmentByTag(ProgramInserterFragment::class.java.simpleName) as ProgramInserterFragment
        if (programInserterFragment == null) {
            programInserterFragment = ProgramInserterFragment()
        }
        mFragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        mFragmentTransaction!!.replace(R.id.container, programInserterFragment, ProgramInserterFragment::class.java.simpleName)
        mFragmentTransaction!!.commit()
    }
}

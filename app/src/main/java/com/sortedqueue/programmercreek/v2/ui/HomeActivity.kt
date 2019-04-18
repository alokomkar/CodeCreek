package com.sortedqueue.programmercreek.v2.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.*

import com.sortedqueue.programmercreek.v2.ui.codelanguage.CodeLanguageFragment
import com.sortedqueue.programmercreek.v2.ui.contentshare.ContentShareActivity
import com.sortedqueue.programmercreek.v2.ui.revision.RevisionFragment
import kotlinx.android.synthetic.main.activity_home.*

@SuppressLint("CommitTransaction")
class HomeActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when( item.itemId ) {
            R.id.action_dashboard -> homePager.setCurrentItem(0, true )
            R.id.action_learners -> homePager.setCurrentItem( 1, true )
            R.id.action_programs -> homePager.setCurrentItem( 2, true )
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        selectedLanguageCardView.setOnClickListener {
            showLanguageFragment()
        }

        addUserCodeFAB.setOnClickListener { startActivity(Intent( this, ContentShareActivity::class.java)) }
        setLanguageSelection()
        addUserCodeFAB.hide()

        //CodeLanguageHelper( this )
        //MasterContentHelper( this )
        //handleSendText()
    }

    fun practiceNow() {

        val transaction = supportFragmentManager.beginTransaction()
        val fragment = supportFragmentManager.findFragmentByTag("RevisionFragment")

        if( fragment != null )
            transaction.remove(fragment)

        transaction.addToBackStack(null)

        RevisionFragment.getInstance().show(transaction, "RevisionFragment")
    }

    private fun setLanguageSelection() {

        if( languageSelectionTextView != null ) {
            if( mBasePreferencesAPI.getLanguage() == null )
                showLanguageFragment()
            else
                setViewPager()

            languageSelectionTextView.apply {
                text = mBasePreferencesAPI.getLanguage()?.language ?: ""
            }
        }
    }

    private fun setViewPager() {
        homePager.adapter = HomePagerAdapter( supportFragmentManager )
        homePager.setCanScroll(false)
        bottom_navigation.setOnNavigationItemSelectedListener( this )
    }

    private fun showLanguageFragment() {

        if( mCodeLanguageFragment == null ) {
            mCodeLanguageFragment = CodeLanguageFragment()
        }

        if( supportFragmentManager.findFragmentByTag( CodeLanguageFragment::class.java.simpleName ) == null ) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                addUserCodeFAB.hide()
                bottom_navigation.hide()
                setCustomAnimations(R.anim.slide_in_up,
                        R.anim.slide_in_down,
                        R.anim.slide_out_down,
                        R.anim.slide_out_up)
                addToBackStack(null)
                mCodeLanguageFragment?.apply {
                    replace(R.id.container, this, CodeLanguageFragment::class.java.simpleName).commit()
                }

            }
        }
        else {
            onBackPressed()
        }

    }

    override fun onBackPressed() {

        if( mBasePreferencesAPI.getLanguage() == null ) {
            languageSelectionTextView.showSnackBar(R.string.choose_language)
            return
        }

        if( supportFragmentManager.backStackEntryCount > 0 ) {
            setLanguageSelection()
            //addUserCodeFAB.show()
            //bottom_navigation.show()
            bottom_navigation.hide()
            addUserCodeFAB.hide()
            supportFragmentManager.popBackStack()
        }
        else
            super.onBackPressed()

    }



    private var mCodeLanguageFragment: CodeLanguageFragment?= null
}
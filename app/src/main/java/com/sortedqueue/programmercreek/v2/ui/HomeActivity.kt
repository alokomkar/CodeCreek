package com.sortedqueue.programmercreek.v2.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.*
import com.sortedqueue.programmercreek.v2.ui.chapters.ChaptersFragment
import com.sortedqueue.programmercreek.v2.ui.codelanguage.CodeLanguageFragment
import kotlinx.android.synthetic.main.activity_home.*

@SuppressLint("CommitTransaction")
class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mBasePreferencesAPI = PCPreferences( this )
        selectedLanguageCardView.setOnClickListener {
            showLanguageFragment()
        }

        if( mBasePreferencesAPI.getLanguage() == null ) showLanguageFragment()
        else setViewPager()
        //CodeLanguageHelper( this )
        //MasterContentHelper( this )
        //handleSendText()
    }

    override fun onResume() {
        super.onResume()
        if( languageSelectionTextView != null )
            languageSelectionTextView.apply {
                text = mBasePreferencesAPI.getLanguage()?.language ?: ""
            }
    }

    private fun setViewPager() {
        if( supportFragmentManager.findFragmentByTag( ChaptersFragment::class.java.simpleName ) == null ) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                addUserCodeFAB.hide()
                bottom_navigation.hide()
                setCustomAnimations(R.anim.slide_in_up,
                        R.anim.slide_in_down,
                        R.anim.slide_out_down,
                        R.anim.slide_out_up)
                addToBackStack(null)
                replace(R.id.container, ChaptersFragment(), ChaptersFragment::class.java.simpleName).commit()
            }
        }
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
                replace(R.id.container, mCodeLanguageFragment, CodeLanguageFragment::class.java.simpleName).commit()
            }
        }

    }

    override fun onBackPressed() {

        if( mBasePreferencesAPI.getLanguage() == null ) {
            languageSelectionTextView.showSnackBar(R.string.choose_language)
            return
        }

        if( supportFragmentManager.backStackEntryCount > 0 ) {
            addUserCodeFAB.show()
            bottom_navigation.show()
            supportFragmentManager.popBackStack()
        }
        else
            super.onBackPressed()

    }

    private fun handleSendText() {

        val intent = intent
        val action = intent.action
        val type = intent.type

        if (Intent.ACTION_SEND == action && type != null) {
            if ("text/plain" == type) {
                val sharedText = intent!!.getStringExtra(Intent.EXTRA_TEXT)
                if (sharedText != null) {
                    ContentShareDialog( this, sharedText )
                }
            }
        }


    }

    private var mCodeLanguageFragment: CodeLanguageFragment?= null
}
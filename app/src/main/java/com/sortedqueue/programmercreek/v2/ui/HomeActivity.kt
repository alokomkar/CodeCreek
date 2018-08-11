package com.sortedqueue.programmercreek.v2.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.hide
import com.sortedqueue.programmercreek.v2.base.show
import com.sortedqueue.programmercreek.v2.ui.codelanguage.CodeLanguageFragment
import kotlinx.android.synthetic.main.activity_home.*

@SuppressLint("CommitTransaction")
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        selectedLanguageCardView.setOnClickListener {
            showLanguageFragment()
        }

        //CodeLanguageHelper( this )
        //MasterContentHelper( this )
        //handleSendText()
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
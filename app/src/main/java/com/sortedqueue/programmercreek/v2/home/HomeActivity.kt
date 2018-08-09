package com.sortedqueue.programmercreek.v2.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.hide
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        selectedLanguageCardView.setOnClickListener {
            bottom_navigation.hide()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                setCustomAnimations(R.anim.slide_in_up,
                        R.anim.slide_in_down,
                        R.anim.slide_out_down,
                        R.anim.slide_out_up)
                addToBackStack(null)
                replace(R.id.container, CodeLanguageFragment()).commit()
            }

        }

        //CodeLanguageHelper( this )
        //MasterContentHelper( this )
        //handleSendText()
    }

    override fun onBackPressed() {
        if( supportFragmentManager.backStackEntryCount > 1 ) {
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
}
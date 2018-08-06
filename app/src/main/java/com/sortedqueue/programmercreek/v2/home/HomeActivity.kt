package com.sortedqueue.programmercreek.v2.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.util.CreekAnalytics
import com.sortedqueue.programmercreek.v2.data.helper.CodeLanguageHelper
import com.sortedqueue.programmercreek.v2.data.helper.MasterContentHelper

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        //CodeLanguageHelper( this )
        //MasterContentHelper( this )
        //handleSendText()
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
package com.sortedqueue.programmercreek.v2.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var mBasePreferencesAPI: BasePreferencesAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBasePreferencesAPI = PCPreferences( this )
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
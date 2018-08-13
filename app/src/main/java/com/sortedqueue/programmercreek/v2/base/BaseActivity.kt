package com.sortedqueue.programmercreek.v2.base

import android.support.v7.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var mBasePreferencesAPI: BasePreferencesAPI
}
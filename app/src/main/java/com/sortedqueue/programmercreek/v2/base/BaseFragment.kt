package com.sortedqueue.programmercreek.v2.base

import android.content.Context
import android.support.v4.app.Fragment

abstract class BaseFragment : Fragment() {

    protected lateinit var mPreferencesAPI : BasePreferencesAPI

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if( context != null )
            mPreferencesAPI = PCPreferences( context )
    }
}
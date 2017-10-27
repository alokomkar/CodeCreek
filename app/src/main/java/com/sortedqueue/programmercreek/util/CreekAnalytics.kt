package com.sortedqueue.programmercreek.util

import android.os.Bundle

import com.google.firebase.analytics.FirebaseAnalytics
import com.sortedqueue.programmercreek.CreekApplication

import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Alok on 08/09/17.
 */

object CreekAnalytics {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private val instance: FirebaseAnalytics
        get() {
            if (mFirebaseAnalytics == null)
                mFirebaseAnalytics = FirebaseAnalytics.getInstance(CreekApplication.instance)
            return mFirebaseAnalytics!!
        }

    fun logEvent(TAG: String, jsonObject: JSONObject) {
        val bundle = Bundle()
        bundle.putString("ScreenName", TAG)
        bundle.putString("EventDetails", jsonObject.toString())
        val it = jsonObject.keys()
        while (it.hasNext()) {
            val key = it.next()
            try {
                bundle.putString(key, jsonObject.get(key).toString())
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
        instance.logEvent("pcEvent", bundle)
    }

    fun logEvent(TAG: String, event: String) {
        val bundle = Bundle()
        bundle.putString("ScreenName", TAG)
        bundle.putString("EventDetails", event)
        instance.logEvent("pcEvent", bundle)
    }
}

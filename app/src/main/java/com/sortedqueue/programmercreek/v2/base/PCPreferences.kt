package com.sortedqueue.programmercreek.v2.base

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson

class PCPreferences( application: Context ) : BasePreferencesAPI {

    companion object {

        private const val dbVersionMap = "dbVersionMap"

        private var basePreferencesAPI : BasePreferencesAPI ?= null
        fun getPreferencesAPI( application: Application ) : BasePreferencesAPI {

            if( basePreferencesAPI == null )
                basePreferencesAPI = PCPreferences( application )
            return basePreferencesAPI!!

        }

    }

    private val mSharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences( application )

    override fun getDBVersions(): Map<String, Long> {
        val mapValues = getPreference(dbVersionMap)
        if( mapValues.isEmpty() ) {
            return HashMap()
        }
        else {
            return Gson().fromJson<Map<String, Long>>(mapValues, Map::class.java)
        }

    }

    override fun setDBVersions(versionMap: HashMap<String, Long>) {
        setPreference( dbVersionMap, Gson().toJson( versionMap ) )
    }

    private fun setPreference( prefKey : String, prefValue : String )
            = mSharedPreferences.edit().putString( prefKey, prefValue ).apply()

    private fun getPreference( prefKey: String ) : String
            = mSharedPreferences.getString(prefKey, "")


}
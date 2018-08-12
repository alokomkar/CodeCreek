package com.sortedqueue.programmercreek.v2.base

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import android.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.sortedqueue.programmercreek.v2.data.model.CodeLanguage

class PCPreferences( application: Context ) : BasePreferencesAPI {

    companion object {

        private const val dbVersionMap = "dbVersionMap"
        private const val selectedLanguage = "selectedLanguage"

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

    override fun setLanguage(codeLanguage: CodeLanguage) {
        setGsonPref( "${getUserId()}_$selectedLanguage", codeLanguage )
    }

    override fun getLanguage(): CodeLanguage? {
        val language = getPreference("${getUserId()}_$selectedLanguage")
        return if( language == "" ) null else Gson().fromJson(language, CodeLanguage::class.java)
    }

    override fun setDBVersions(versionMap: HashMap<String, Long>) {
        setPreference( dbVersionMap, Gson().toJson( versionMap ) )
    }

    override fun getUserId(): String {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.uid ?: ""
    }

    private fun setGsonPref( prefKey: String, parcelable: Parcelable )
        = mSharedPreferences.edit().putString( prefKey, Gson().toJson(parcelable) ).apply()


    private fun setPreference( prefKey : String, prefValue : String )
            = mSharedPreferences.edit().putString( prefKey, prefValue ).apply()

    private fun getPreference( prefKey: String ) : String
            = mSharedPreferences.getString(prefKey, "")


}
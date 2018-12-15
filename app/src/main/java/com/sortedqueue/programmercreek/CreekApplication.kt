package com.sortedqueue.programmercreek

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log

import com.facebook.appevents.AppEventsLogger
import com.sortedqueue.programmercreek.activity.SplashActivity
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.Algorithm
import com.sortedqueue.programmercreek.database.AlgorithmContent
import com.sortedqueue.programmercreek.database.AlgorithmsIndex
import com.sortedqueue.programmercreek.database.Chapter
import com.sortedqueue.programmercreek.database.ChapterDetails
import com.sortedqueue.programmercreek.database.CreekUserDB
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.database.IntroChapter
import com.sortedqueue.programmercreek.database.LanguageModule
import com.sortedqueue.programmercreek.database.ModuleOption
import com.sortedqueue.programmercreek.database.PresentationModel
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.database.ProgramLanguage
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.database.ProgramWiki
import com.sortedqueue.programmercreek.database.SlideModel
import com.sortedqueue.programmercreek.database.SyntaxModule
import com.sortedqueue.programmercreek.database.UserProgramDetails
import com.sortedqueue.programmercreek.database.WikiModel
import com.sortedqueue.programmercreek.util.CreekPreferences
import com.startapp.android.publish.adsCommon.StartAppSDK

import java.util.ArrayList

import co.uk.rushorm.android.AndroidInitializeConfig
import co.uk.rushorm.core.Rush
import co.uk.rushorm.core.RushCore
import com.google.android.gms.ads.MobileAds
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * Created by Alok Omkar on 2016-12-22.
 */

//https://github.com/StartApp-SDK/Documentation/wiki/Android-InApp-Documentation
class CreekApplication : Application() {

    var creekUserStats: CreekUserStats? = null
        get() {
            if (field == null) {
                this.creekUserStats = getPreferences().creekUserStats
            }
            return field
        }

    override fun onCreate() {
        super.onCreate()
        instance = this
        creekPreferences = getPreferences()
        //initAdsSdk();
        //FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this)
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath(ProgrammingBuddyConstants.FONT_VARELA)
                .setFontAttrId(R.attr.fontPath)
                .build()
        )

        val dbClasses = ArrayList<Class<out Rush>>()

        dbClasses.add(CreekUserDB::class.java)
        dbClasses.add(LanguageModule::class.java)
        dbClasses.add(ModuleOption::class.java)
        dbClasses.add(ProgramWiki::class.java)
        dbClasses.add(SyntaxModule::class.java)
        dbClasses.add(WikiModel::class.java)
        dbClasses.add(ProgramTable::class.java)
        dbClasses.add(ProgramIndex::class.java)
        dbClasses.add(Chapter::class.java)
        dbClasses.add(ChapterDetails::class.java)
        dbClasses.add(IntroChapter::class.java)
        dbClasses.add(ProgramLanguage::class.java)
        dbClasses.add(AlgorithmsIndex::class.java)
        dbClasses.add(AlgorithmContent::class.java)
        dbClasses.add(Algorithm::class.java)
        dbClasses.add(SlideModel::class.java)
        dbClasses.add(PresentationModel::class.java)
        dbClasses.add(UserProgramDetails::class.java)

        val config = AndroidInitializeConfig(applicationContext)
        config.setClasses(dbClasses)
        RushCore.initialize(config)
        //setupExceptionHandler();
    }

    fun initAdsSdk() {
        StartAppSDK.init(this, "207164192", creekPreferences!!.adsEnabled)
        MobileAds.initialize(this, getString(R.string.ad_mob_id));
    }

    private fun setupExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler { thread, ex ->
            if (ex != null) {
                Log.e("ExceptionHandler", ex.message)
                ex.printStackTrace()
            }
            Log.d("ExceptionHandler", "Restarted App")
            val mStartActivity = Intent(instance, SplashActivity::class.java)
            val mPendingIntentId = 123456
            val mPendingIntent = PendingIntent.getActivity(instance, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT)
            val mgr = instance.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent)
            System.exit(0)
        }
    }

    var isAppRunning: Boolean = false

    companion object {

        @SuppressLint("StaticFieldLeak")
        var creekPreferences: CreekPreferences? = null
        fun getPreferences(): CreekPreferences {
            if (creekPreferences == null) {
                creekPreferences = CreekPreferences(instance)
            }
            return creekPreferences as CreekPreferences
        }

        lateinit var instance: CreekApplication
        private set


    }

}

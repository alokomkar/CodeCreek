package com.sortedqueue.programmercreek;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.appevents.AppEventsLogger;
import com.sortedqueue.programmercreek.activity.SplashActivity;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.AlgorithmsIndex;
import com.sortedqueue.programmercreek.database.Chapter;
import com.sortedqueue.programmercreek.database.ChapterDetails;
import com.sortedqueue.programmercreek.database.CreekUserDB;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.IntroChapter;
import com.sortedqueue.programmercreek.database.LanguageModule;
import com.sortedqueue.programmercreek.database.ModuleOption;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.ProgramLanguage;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.ProgramWiki;
import com.sortedqueue.programmercreek.database.SyntaxModule;
import com.sortedqueue.programmercreek.database.WikiModel;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCore;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Alok Omkar on 2016-12-22.
 */

public class CreekApplication extends Application {

    static CreekApplication creekApplication;
    private ArrayList<ProgramIndex> programIndexes;
    private HashMap<Integer, ProgramIndex> program_indexHashMap;
    private CreekUserStats creekUserStats;

    public static CreekApplication getInstance() {
        return creekApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        creekApplication = this;
        program_indexHashMap = new HashMap<>();
        //FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(ProgrammingBuddyConstants.FONT_ROBOTO)
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        List<Class<? extends Rush>> dbClasses = new ArrayList<Class<? extends Rush>>() ;

        dbClasses.add(CreekUserDB.class);
        dbClasses.add(LanguageModule.class);
        dbClasses.add(ModuleOption.class);
        dbClasses.add(ProgramWiki.class);
        dbClasses.add(SyntaxModule.class);
        dbClasses.add(WikiModel.class);
        dbClasses.add(ProgramTable.class);
        dbClasses.add(ProgramIndex.class);
        dbClasses.add(Chapter.class);
        dbClasses.add(ChapterDetails.class);
        dbClasses.add(IntroChapter.class);
        dbClasses.add(ProgramLanguage.class);
        dbClasses.add(AlgorithmsIndex.class);
        AndroidInitializeConfig config = new AndroidInitializeConfig(getApplicationContext());
        config.setClasses(dbClasses) ;
        RushCore.initialize(config);
        //setupExceptionHandler();
    }

    private void setupExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                if( ex != null ) {
                    Log.e("ExceptionHandler", ex.getMessage());
                    ex.printStackTrace();
                }
                Log.d("ExceptionHandler", "Restarted App");
                Intent mStartActivity = new Intent(getInstance(), SplashActivity.class);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(creekApplication, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager) creekApplication.getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                System.exit(0);
            }
        });
    }


    public void setProgramIndexes(ArrayList<ProgramIndex> programIndexes) {
        this.programIndexes = programIndexes;
        program_indexHashMap.clear();
        for( ProgramIndex program_index : programIndexes ) {
            program_indexHashMap.put(program_index.getProgram_index(), program_index);
        }
    }

    public HashMap<Integer, ProgramIndex> getProgram_indexHashMap() {
        return program_indexHashMap;
    }

    public ArrayList<ProgramIndex> getProgramIndexes() {
        return programIndexes;
    }

    public CreekUserStats getCreekUserStats( ) {
        if( creekUserStats == null ) {
            creekUserStats = new CreekPreferences(getApplicationContext()).getCreekUserStats();
        }
        return creekUserStats;
    }

    public void setCreekUserStats(CreekUserStats creekUserStats) {
        this.creekUserStats = creekUserStats;
    }

    private boolean isAppRunning;

    public boolean isAppRunning() {
        return isAppRunning;
    }

    public void setAppRunning(boolean appRunning) {
        isAppRunning = appRunning;
    }
}

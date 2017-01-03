package com.sortedqueue.programmercreek;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.sortedqueue.programmercreek.database.Program_Index;

import java.util.ArrayList;
import java.util.HashMap;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.RushCore;

/**
 * Created by Alok Omkar on 2016-12-22.
 */

public class CreekApplication extends Application {

    static CreekApplication creekApplication;
    private ArrayList<Program_Index> programIndexes;
    private HashMap<Integer, Program_Index> program_indexHashMap;

    public static CreekApplication getInstance() {
        return creekApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        creekApplication = this;
        program_indexHashMap = new HashMap<>();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        AndroidInitializeConfig config = new AndroidInitializeConfig(getApplicationContext());
        RushCore.initialize(config);
    }


    public void setProgramIndexes(ArrayList<Program_Index> programIndexes) {
        this.programIndexes = programIndexes;
        program_indexHashMap.clear();
        for( Program_Index program_index : programIndexes ) {
            program_indexHashMap.put(program_index.getIndex(), program_index);
        }
    }

    public HashMap<Integer, Program_Index> getProgram_indexHashMap() {
        return program_indexHashMap;
    }

    public ArrayList<Program_Index> getProgramIndexes() {
        return programIndexes;
    }
}

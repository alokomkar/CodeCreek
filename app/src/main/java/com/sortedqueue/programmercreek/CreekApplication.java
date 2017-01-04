package com.sortedqueue.programmercreek;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.sortedqueue.programmercreek.database.CreekUserDB;
import com.sortedqueue.programmercreek.database.LanguageModule;
import com.sortedqueue.programmercreek.database.ModuleOption;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.ProgramWiki;
import com.sortedqueue.programmercreek.database.SyntaxModule;
import com.sortedqueue.programmercreek.database.WikiModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCore;

/**
 * Created by Alok Omkar on 2016-12-22.
 */

public class CreekApplication extends Application {

    static CreekApplication creekApplication;
    private ArrayList<ProgramIndex> programIndexes;
    private HashMap<Integer, ProgramIndex> program_indexHashMap;

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

        List<Class<? extends Rush>> dbClasses = new ArrayList<Class<? extends Rush>>() ;

        dbClasses.add(CreekUserDB.class);
        dbClasses.add(LanguageModule.class);
        dbClasses.add(ModuleOption.class);
        dbClasses.add(ProgramWiki.class);
        dbClasses.add(SyntaxModule.class);
        dbClasses.add(WikiModel.class);
        dbClasses.add(ProgramTable.class);
        dbClasses.add(ProgramIndex.class);
        AndroidInitializeConfig config = new AndroidInitializeConfig(getApplicationContext());
        config.setClasses(dbClasses) ;
        RushCore.initialize(config);
    }


    public void setProgramIndexes(ArrayList<ProgramIndex> programIndexes) {
        this.programIndexes = programIndexes;
        program_indexHashMap.clear();
        for( ProgramIndex program_index : programIndexes ) {
            program_indexHashMap.put(program_index.getIndex(), program_index);
        }
    }

    public HashMap<Integer, ProgramIndex> getProgram_indexHashMap() {
        return program_indexHashMap;
    }

    public ArrayList<ProgramIndex> getProgramIndexes() {
        return programIndexes;
    }
}

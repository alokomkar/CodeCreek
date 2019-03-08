package com.sortedqueue.programmercreek.v2.data.db

import android.app.Application
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.sortedqueue.programmercreek.v2.data.model.CodeLanguage
import com.sortedqueue.programmercreek.v2.data.model.MasterContent


@Database(entities = [MasterContent::class, CodeLanguage::class], version = 1)
abstract class PracticeCodeRoomDatabase : RoomDatabase() {

    abstract fun masterContentDao( ) : MasterContentDao

    abstract fun codeLanguageDao() : CodeLanguageDao

    companion object {

        private var dbInstance : PracticeCodeRoomDatabase?= null

        fun getDbInstance( application: Application ) : PracticeCodeRoomDatabase {
            if (dbInstance == null) {
                synchronized(PracticeCodeRoomDatabase::class.java) {
                    if (dbInstance == null) {
                        dbInstance = Room.databaseBuilder(application,
                                PracticeCodeRoomDatabase::class.java, "practiceCodeDatabase")
                                .addCallback(object  : RoomDatabase.Callback() {
                                    //val dbInstance = getDbInstance(application)
                                })
                                .build()

                    }
                }
            }
            return dbInstance!!
        }
    }
}
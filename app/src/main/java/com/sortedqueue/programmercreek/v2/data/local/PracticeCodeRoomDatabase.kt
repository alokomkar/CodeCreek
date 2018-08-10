package com.sortedqueue.programmercreek.v2.data.local

import android.app.Application
import android.arch.persistence.room.Database
import android.content.Context
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase


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
                                .build()

                    }
                }
            }
            return dbInstance!!
        }
    }
}
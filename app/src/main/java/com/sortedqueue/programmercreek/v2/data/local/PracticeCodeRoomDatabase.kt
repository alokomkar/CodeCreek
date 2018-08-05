package com.sortedqueue.programmercreek.v2.data.local

import android.arch.persistence.room.Database
import android.content.Context
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase


@Database(entities = [(MasterContent::class)], version = 1)
abstract class PracticeCodeRoomDatabase : RoomDatabase() {

    abstract fun masterContentDao( ) : MasterContentDao

    abstract fun codeLanguageDao() : CodeLanguageDao

    companion object {

        private var dbInstance : PracticeCodeRoomDatabase?= null

        fun getDbInstance( context: Context ) : PracticeCodeRoomDatabase {
            if (dbInstance == null) {
                synchronized(PracticeCodeRoomDatabase::class.java) {
                    if (dbInstance == null) {
                        dbInstance = Room.databaseBuilder(context.applicationContext,
                                PracticeCodeRoomDatabase::class.java, "practiceCodeDatabase")
                                .build()

                    }
                }
            }
            return dbInstance!!
        }
    }
}
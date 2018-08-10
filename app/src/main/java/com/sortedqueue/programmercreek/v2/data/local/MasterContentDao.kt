package com.sortedqueue.programmercreek.v2.data.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.util.Log

@Dao
abstract class MasterContentDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert( masterContent: MasterContent) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update( masterContent: MasterContent)

    @Query("DELETE FROM MasterContent WHERE id = :contentId")
    abstract fun deleteById( contentId : String )

    @Query("DELETE FROM MasterContent")
    abstract fun deleteAll()

    @Query("SELECT * FROM MasterContent ORDER BY id ASC")
    abstract fun listAll() : List<MasterContent>

    @Query("SELECT * FROM MasterContent ORDER BY id ASC")
    abstract fun listAllLive() : LiveData<List<MasterContent>>

    @Query("SELECT * FROM MasterContent WHERE languageId = :languageId ORDER BY id ASC")
    abstract fun listAllLiveByID( languageId : String ) : LiveData<List<MasterContent>>

    @Query("SELECT * FROM MasterContent WHERE id = :contentId")
    abstract fun findLiveById( contentId: String ) : LiveData<MasterContent>

    @Transaction
    fun insertOrUpdate( masterContent: MasterContent ) {
        Log.d( MasterContentDao::class.java.simpleName, "insertOrUpdate : attempt insert : $masterContent")
        val id = insert( masterContent )
        if( id == -1L ) {
            Log.d( CodeLanguageDao::class.java.simpleName, "insertOrUpdate : attempt update : $masterContent")
            update( masterContent )
        }
    }

}
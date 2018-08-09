package com.sortedqueue.programmercreek.v2.data.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface MasterContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert( masterContent: MasterContent)

    @Query("DELETE FROM MasterContent WHERE id = :contentId")
    fun deleteById( contentId : String )

    @Query("DELETE FROM MasterContent")
    fun deleteAll()

    @Query("SELECT * FROM MasterContent ORDER BY id ASC")
    fun listAll() : List<MasterContent>

    @Query("SELECT * FROM MasterContent ORDER BY id ASC")
    fun listAllLive() : LiveData<List<MasterContent>>

    @Query("SELECT * FROM MasterContent WHERE languageId = :languageId ORDER BY id ASC")
    fun listAllLiveByID( languageId : String ) : LiveData<List<MasterContent>>

    @Query("SELECT * FROM MasterContent WHERE id = :contentId")
    fun findLiveById( contentId: String ) : LiveData<MasterContent>

}
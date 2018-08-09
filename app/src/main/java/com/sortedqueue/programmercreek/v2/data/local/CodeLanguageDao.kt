package com.sortedqueue.programmercreek.v2.data.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface CodeLanguageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert( codeLanguage: CodeLanguage )

    @Query("DELETE FROM CodeLanguage WHERE id = :languageId")
    fun deleteById( languageId : String )

    @Query("DELETE FROM CodeLanguage")
    fun deleteAll()

    @Query("SELECT * FROM CodeLanguage ORDER BY id ASC")
    fun listAll() : List<CodeLanguage>

    @Query("SELECT * FROM CodeLanguage ORDER BY id ASC")
    fun listAllLive() : LiveData<List<CodeLanguage>>

    @Query("SELECT * FROM CodeLanguage WHERE id = :languageId")
    fun findLiveById( languageId: String ) : LiveData<CodeLanguage>


}
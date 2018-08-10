package com.sortedqueue.programmercreek.v2.data.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.util.Log

@Dao
abstract class CodeLanguageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert( codeLanguage: CodeLanguage ) : Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract fun update( codeLanguage: CodeLanguage )

    @Query("DELETE FROM CodeLanguage WHERE id = :languageId")
    abstract fun deleteById( languageId : String )

    @Query("DELETE FROM CodeLanguage")
    abstract fun deleteAll()

    @Query("SELECT * FROM CodeLanguage ORDER BY id ASC")
    abstract fun listAll() : List<CodeLanguage>

    @Query("SELECT * FROM CodeLanguage ORDER BY id ASC")
    abstract fun listAllLive() : LiveData<List<CodeLanguage>>

    @Query("SELECT * FROM CodeLanguage WHERE id = :languageId")
    abstract fun findLiveById( languageId: String ) : LiveData<CodeLanguage>

    @Transaction
    fun insertOrUpdate( codeLanguage: CodeLanguage ) {
        Log.d( CodeLanguageDao::class.java.simpleName, "insertOrUpdate : attempt insert : $codeLanguage")
        val id = insert( codeLanguage )
        if( id == -1L ) {
            Log.d( CodeLanguageDao::class.java.simpleName, "insertOrUpdate : attempt update : $codeLanguage")
            update( codeLanguage )
        }
    }


}
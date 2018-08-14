package com.sortedqueue.programmercreek.v2.base

import com.sortedqueue.programmercreek.v2.data.helper.Content
import com.sortedqueue.programmercreek.v2.data.model.CodeLanguage

interface BasePreferencesAPI {

    fun getDBVersions() : Map<String, Long>
    fun setDBVersions( versionMap : HashMap<String, Long> )
    fun setLanguage( codeLanguage: CodeLanguage )
    fun getLanguage( ) : CodeLanguage?
    fun getUserId() : String
    fun getSavedNotes(): String
    fun setSavedNotes( notes : String )
    fun setContentList( contentList : ArrayList<Content> )
    fun getContentList( ) : ArrayList<Content>

}
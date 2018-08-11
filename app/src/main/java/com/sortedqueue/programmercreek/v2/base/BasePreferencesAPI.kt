package com.sortedqueue.programmercreek.v2.base

interface BasePreferencesAPI {

    fun getDBVersions() : Map<String, Long>
    fun setDBVersions( versionMap : HashMap<String, Long> )

}
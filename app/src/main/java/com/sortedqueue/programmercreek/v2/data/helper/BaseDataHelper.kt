package com.sortedqueue.programmercreek.v2.data.helper

interface BaseDataHelper<in T> {

    fun insertData( obj : T )
    fun insertData( obj : ArrayList<out T> )

}
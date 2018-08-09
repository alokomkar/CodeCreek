package com.sortedqueue.programmercreek.v2.data.remote

import android.arch.lifecycle.LiveData

interface BaseAPI<in T> {

    fun insertOrUpdate( obj : T )
    fun insertOrUpdate( vararg obj : T )
    fun insertOrUpdate( obj: ArrayList<out T> )

}
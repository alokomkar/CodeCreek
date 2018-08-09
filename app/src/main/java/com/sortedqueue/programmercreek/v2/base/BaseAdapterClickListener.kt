package com.sortedqueue.programmercreek.v2.base

interface BaseAdapterClickListener<in T> {
    fun onItemClick( position : Int, item : T )
}
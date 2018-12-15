package com.sortedqueue.programmercreek.v2.data.remote

import android.arch.lifecycle.LiveData
import android.os.Parcelable

interface SyncDataManager {
    fun isAvailableOffline() : Boolean
    fun fetchFromFirebase() : LiveData<List<Parcelable>>
    fun fetchFromRoom() : LiveData<List<Parcelable>>
}
package com.sortedqueue.programmercreek.v2.data.remote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.Parcelable

class SyncDataManagerImpl( private val firebaseService : FirebaseDatabaseProvider
                           ) : SyncDataManager {

    val mMutableLiveDataList = MutableLiveData<List<Parcelable>>()

    override fun isAvailableOffline(): Boolean {
        return true
    }

    override fun fetchFromFirebase(): LiveData<List<Parcelable>> {
       return mMutableLiveDataList
    }

    override fun fetchFromRoom(): LiveData<List<Parcelable>> {
       return mMutableLiveDataList
    }
}
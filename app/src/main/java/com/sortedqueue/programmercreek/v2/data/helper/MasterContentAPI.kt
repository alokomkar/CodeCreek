package com.sortedqueue.programmercreek.v2.data.helper

import android.arch.lifecycle.LiveData
import com.sortedqueue.programmercreek.v2.data.local.MasterContent
import com.sortedqueue.programmercreek.v2.data.remote.BaseAPI

interface MasterContentAPI : BaseAPI<MasterContent> {
    fun fetchLiveMasterContents() : LiveData<List<MasterContent>>
    fun fetchLiveMasterContentById( id : String ) : LiveData<MasterContent>
    fun fetchLiveMasterContentsByLanguage( languageId: String ) : LiveData<List<MasterContent>>
}
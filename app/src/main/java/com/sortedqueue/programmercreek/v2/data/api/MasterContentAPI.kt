package com.sortedqueue.programmercreek.v2.data.api

import android.arch.lifecycle.LiveData
import com.sortedqueue.programmercreek.v2.data.model.MasterContent
import com.sortedqueue.programmercreek.v2.data.api.BaseAPI

interface MasterContentAPI : BaseAPI<MasterContent> {
    fun fetchLiveMasterContents() : LiveData<List<MasterContent>>
    fun fetchLiveMasterContentById( id : String ) : LiveData<MasterContent>
    fun fetchLiveMasterContentsByLanguage( languageId: String ) : LiveData<List<MasterContent>>
}
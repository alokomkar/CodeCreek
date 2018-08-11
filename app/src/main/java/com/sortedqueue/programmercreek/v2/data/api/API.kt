package com.sortedqueue.programmercreek.v2.data.api

import android.arch.lifecycle.LiveData
import android.os.Parcelable
import com.sortedqueue.programmercreek.v2.data.model.CodeLanguage
import com.sortedqueue.programmercreek.v2.data.model.MasterContent

interface API : BaseAPI<Parcelable> {

    fun fetchLiveCodeLanguages() : LiveData<List<CodeLanguage>>
    fun fetchLiveCodeLanguageById( id : String ) : LiveData<CodeLanguage>

    fun fetchLiveMasterContents() : LiveData<List<MasterContent>>
    fun fetchLiveMasterContentById( id : String ) : LiveData<MasterContent>
    fun fetchLiveMasterContentsByLanguage( languageId: String ) : LiveData<List<MasterContent>>

}
package com.sortedqueue.programmercreek.v2.data.remote

import android.arch.lifecycle.LiveData
import android.os.Parcelable
import com.sortedqueue.programmercreek.v2.data.local.CodeLanguage
import com.sortedqueue.programmercreek.v2.data.local.MasterContent

interface API : BaseAPI<Parcelable> {

    fun getAllCodeLanguage() : ArrayList<CodeLanguage>
    fun getAllMasterContent() : ArrayList<MasterContent>
    fun getAllMasterContent( languageId : String ) : ArrayList<MasterContent>

    fun fetchLiveCodeLanguages() : LiveData<List<CodeLanguage>>
    fun fetchLiveCodeLanguageById( id : String ) : LiveData<CodeLanguage>

    fun fetchLiveMasterContents() : LiveData<List<MasterContent>>
    fun fetchLiveMasterContentById( id : String ) : LiveData<MasterContent>
    fun fetchLiveMasterContentsByLanguage( languageId: String ) : LiveData<List<MasterContent>>

}
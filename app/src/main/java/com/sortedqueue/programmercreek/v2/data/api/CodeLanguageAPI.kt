package com.sortedqueue.programmercreek.v2.data.api

import android.arch.lifecycle.LiveData
import com.sortedqueue.programmercreek.v2.data.model.CodeLanguage
import com.sortedqueue.programmercreek.v2.data.api.BaseAPI

interface CodeLanguageAPI : BaseAPI<CodeLanguage> {
    fun fetchLiveCodeLanguages() : LiveData<List<CodeLanguage>>
    fun fetchLiveCodeLanguageById( id : String ) : LiveData<CodeLanguage>
}
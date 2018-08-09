package com.sortedqueue.programmercreek.v2.data.helper

import android.arch.lifecycle.LiveData
import com.sortedqueue.programmercreek.v2.data.local.CodeLanguage
import com.sortedqueue.programmercreek.v2.data.remote.BaseAPI

interface CodeLanguageAPI : BaseAPI<CodeLanguage> {
    fun fetchLiveCodeLanguages() : LiveData<List<CodeLanguage>>
    fun fetchLiveCodeLanguageById( id : String ) : LiveData<CodeLanguage>
}
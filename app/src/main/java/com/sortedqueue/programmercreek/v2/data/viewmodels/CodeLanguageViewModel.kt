package com.sortedqueue.programmercreek.v2.data.viewmodels

import android.app.Application
import android.arch.lifecycle.LiveData
import com.sortedqueue.programmercreek.v2.data.helper.CodeLanguageAPI
import com.sortedqueue.programmercreek.v2.data.helper.CodeLanguageHelper
import com.sortedqueue.programmercreek.v2.data.local.CodeLanguage

class CodeLanguageViewModel( application: Application )
    : BaseViewModel( application ),
        CodeLanguageAPI {

    private val codeLanguageAPI = CodeLanguageHelper( application )

    override fun fetchLiveCodeLanguages(): LiveData<List<CodeLanguage>>
            = codeLanguageAPI.fetchLiveCodeLanguages()

    override fun fetchLiveCodeLanguageById(id: String): LiveData<CodeLanguage>
            = codeLanguageAPI.fetchLiveCodeLanguageById( id )

    override fun insertOrUpdate(obj: CodeLanguage)
            = codeLanguageAPI.insertOrUpdate(obj)

    override fun insertOrUpdate(vararg obj: CodeLanguage) {}

    override fun insertOrUpdate(obj: ArrayList<out CodeLanguage>)
            = codeLanguageAPI.insertOrUpdate(obj)

}
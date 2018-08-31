package com.sortedqueue.programmercreek.v2.ui.codelanguage

import android.app.Application
import android.arch.lifecycle.LiveData
import com.sortedqueue.programmercreek.v2.base.BaseViewModel
import com.sortedqueue.programmercreek.v2.data.api.CodeLanguageAPI
import com.sortedqueue.programmercreek.v2.data.db.CodeLanguageRepository
import com.sortedqueue.programmercreek.v2.data.model.CodeLanguage
import java.util.*

class CodeLanguageViewModel( application: Application )
    : BaseViewModel( application ),
        CodeLanguageAPI {

    private val codeLanguageAPI = CodeLanguageRepository(application)

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
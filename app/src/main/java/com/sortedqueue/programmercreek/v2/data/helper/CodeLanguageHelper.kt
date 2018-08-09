package com.sortedqueue.programmercreek.v2.data.helper

import android.app.Application
import android.arch.lifecycle.LiveData
import android.content.Context
import com.sortedqueue.programmercreek.v2.data.local.CodeLanguage
import com.sortedqueue.programmercreek.v2.data.remote.BaseAPI
import com.sortedqueue.programmercreek.v2.data.remote.PCFirebaseHandler

class CodeLanguageHelper( application: Application ) : CodeLanguageAPI {

    override fun fetchLiveCodeLanguages(): LiveData<List<CodeLanguage>>
            = api.fetchLiveCodeLanguages()

    override fun fetchLiveCodeLanguageById(id: String): LiveData<CodeLanguage>
            = api.fetchLiveCodeLanguageById(id)

    private val api = PCFirebaseHandler.getAPI( application )

    override fun insertOrUpdate(obj: CodeLanguage) = api.insertOrUpdate(obj)

    override fun insertOrUpdate(vararg obj: CodeLanguage) {}

    override fun insertOrUpdate(obj: ArrayList<out CodeLanguage>)
            = api.insertOrUpdate(obj)

    init {

        val codeLanguages = ArrayList<CodeLanguage>()
        codeLanguages.add( CodeLanguage(
                "",
                "Java",
                "Java is a general-purpose computer " +
                        "programming language that is concurrent, " +
                        "class-based, object-oriented, and specifically " +
                        "designed to have as few implementation " +
                        "dependencies as possible.",
                ".java",
                System.currentTimeMillis(),
                System.currentTimeMillis()))

        insertOrUpdate( codeLanguages )

    }


}
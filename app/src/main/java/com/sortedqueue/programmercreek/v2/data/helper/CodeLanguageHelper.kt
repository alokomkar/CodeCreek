package com.sortedqueue.programmercreek.v2.data.helper

import android.content.Context
import com.sortedqueue.programmercreek.v2.data.local.CodeLanguage
import com.sortedqueue.programmercreek.v2.data.remote.BaseAPI
import com.sortedqueue.programmercreek.v2.data.remote.PCFirebaseHandler

class CodeLanguageHelper( private val context: Context ) : BaseAPI<CodeLanguage> {

    private val api = PCFirebaseHandler.getAPI( context )

    override fun insertOrUpdate(obj: CodeLanguage) = api.insertOrUpdate(obj)

    override fun insertOrUpdate(vararg obj: CodeLanguage) {}

    override fun insertOrUpdate(obj: ArrayList<out CodeLanguage>) = api.insertOrUpdate(obj)


}
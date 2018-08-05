package com.sortedqueue.programmercreek.v2.data.remote

import android.os.Parcelable
import com.sortedqueue.programmercreek.v2.data.local.CodeLanguage
import com.sortedqueue.programmercreek.v2.data.local.MasterContent

interface API : BaseAPI<Parcelable> {

    fun getAllCodeLanguage() : ArrayList<CodeLanguage>
    fun getAllMasterContent() : ArrayList<MasterContent>
    fun getAllMasterContent( languageId : String ) : ArrayList<MasterContent>

}
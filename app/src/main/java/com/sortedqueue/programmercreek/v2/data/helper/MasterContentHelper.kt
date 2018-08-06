package com.sortedqueue.programmercreek.v2.data.helper

import android.content.Context
import com.sortedqueue.programmercreek.v2.data.local.MasterContent
import com.sortedqueue.programmercreek.v2.data.remote.BaseAPI
import com.sortedqueue.programmercreek.v2.data.remote.PCFirebaseHandler

class MasterContentHelper( context: Context) : BaseAPI<MasterContent> {

    private val api = PCFirebaseHandler.getAPI( context )

    override fun insertOrUpdate(obj: MasterContent) = api.insertOrUpdate(obj)

    override fun insertOrUpdate(vararg obj: MasterContent) {}

    override fun insertOrUpdate(obj: ArrayList<out MasterContent>) = api.insertOrUpdate(obj)

    init {

        val masterContents = ArrayList<MasterContent>()
        masterContents.add( MasterContent(
                "",
                "Sample Content",
                "-LJC3tkCGw79agAINOfK",
                MasterContent.typeCode,
                System.currentTimeMillis(),
                System.currentTimeMillis()))

        insertOrUpdate( masterContents )

    }


}
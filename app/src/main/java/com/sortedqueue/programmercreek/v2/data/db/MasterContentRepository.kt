package com.sortedqueue.programmercreek.v2.data.db

import android.app.Application
import android.arch.lifecycle.LiveData
import com.sortedqueue.programmercreek.v2.data.api.MasterContentAPI
import com.sortedqueue.programmercreek.v2.data.model.MasterContent
import com.sortedqueue.programmercreek.v2.data.remote.PCFirebaseHandler

class MasterContentRepository(application: Application) : MasterContentAPI {

    override fun fetchLiveMasterContents(): LiveData<List<MasterContent>>
            = api.fetchLiveMasterContents()

    override fun fetchLiveMasterContentById(id: String): LiveData<MasterContent>
            = api.fetchLiveMasterContentById( id )

    override fun fetchLiveMasterContentsByLanguage( languageId: String ): LiveData<List<MasterContent>>
            = api.fetchLiveMasterContentsByLanguage( languageId )

    private val api = PCFirebaseHandler.getAPI( application )

    override fun insertOrUpdate(obj: MasterContent)
            = api.insertOrUpdate(obj)

    override fun insertOrUpdate(vararg obj: MasterContent) {}

    override fun insertOrUpdate(obj: ArrayList<out MasterContent>) = api.insertOrUpdate(obj)

    init {

        /*val masterContents = ArrayList<MasterContent>()
        masterContents.add( MasterContent(
                "",
                "Sample Content",
                "-LJC3tkCGw79agAINOfK",
                MasterContent.typeCode,
                System.currentTimeMillis(),
                System.currentTimeMillis()))

        insertOrUpdate( masterContents )*/

    }


}
package com.sortedqueue.programmercreek.v2.ui.mastercontent

import android.app.Application
import android.arch.lifecycle.LiveData
import com.sortedqueue.programmercreek.v2.base.BaseViewModel
import com.sortedqueue.programmercreek.v2.data.api.MasterContentAPI
import com.sortedqueue.programmercreek.v2.data.db.MasterContentRepository
import com.sortedqueue.programmercreek.v2.data.model.MasterContent
import java.util.*

class MasterContentViewModel( application: Application ) :
        BaseViewModel( application ),
        MasterContentAPI {

    private val masterContentAPI = MasterContentRepository(application)

    override fun fetchLiveMasterContents(): LiveData<List<MasterContent>>
            = masterContentAPI.fetchLiveMasterContents()

    override fun fetchLiveMasterContentById(id: String): LiveData<MasterContent>
            = masterContentAPI.fetchLiveMasterContentById( id )

    override fun fetchLiveMasterContentsByLanguage(languageId: String): LiveData<List<MasterContent>>
            = masterContentAPI.fetchLiveMasterContentsByLanguage( languageId )

    override fun insertOrUpdate(obj: MasterContent)
            = masterContentAPI.insertOrUpdate( obj )

    override fun insertOrUpdate(vararg obj: MasterContent) {}

    override fun insertOrUpdate(obj: ArrayList<out MasterContent>)
            = masterContentAPI.insertOrUpdate(obj)

}
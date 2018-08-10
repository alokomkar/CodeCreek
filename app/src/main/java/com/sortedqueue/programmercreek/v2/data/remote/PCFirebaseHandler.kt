package com.sortedqueue.programmercreek.v2.data.remote

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.AsyncTask
import android.os.Parcelable
import android.util.Log
import com.google.firebase.database.*
import com.sortedqueue.programmercreek.v2.data.local.*
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("StaticFieldLeak")
class PCFirebaseHandler : API, ValueEventListener {


    override fun onCancelled(error: DatabaseError) {

    }

    override fun onDataChange(snapshot: DataSnapshot) {
        if( snapshot.hasChildren() ) {

            codeLanguages.clear()
            masterContentMap.clear()
            masterContents.clear()
            Log.d(PCFirebaseHandler::class.java.simpleName, "CodeLanguages : " + snapshot.children.count())
            for( child in snapshot.children ) {
                val codeLanguage = child.getValue(CodeLanguage::class.java)
                insertAsync( codeLanguage )
                codeLanguages.add(codeLanguage!!)
                masterContentMap[codeLanguage.id] = ArrayList()
            }
            getAllContent()
        }
    }

    private fun insertAsync( obj: Parcelable? ) =
        object : AsyncTask<Void, Void, Unit>() {
            override fun doInBackground(vararg p0: Void?) {
                when( obj ) {
                    is CodeLanguage -> {
                        codeLanguageDao.insertOrUpdate( obj )
                    }
                    is MasterContent -> {
                        masterContentDao.insertOrUpdate( obj )
                    }
                }
            }
        }.executeOnExecutor( AsyncTask.THREAD_POOL_EXECUTOR )





    private fun getAllContent() {
        for( language in masterContentMap.keys ) {
            getFirebaseDBReference("$masterContentDB/$language" )
                    .addListenerForSingleValueEvent( object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {

                        }

                        override fun onDataChange( snapshot: DataSnapshot) {
                            for( child in snapshot.children ) {
                                val masterContent = child.getValue(MasterContent::class.java)
                                insertAsync( masterContent )
                                masterContentMap[masterContent?.languageId]!!.add(masterContent!!)
                            }
                        }

                    })
        }
    }


    companion object {

        private var singleInstance : PCFirebaseHandler?= null
        private const val pcDBVersion = "v2"
        private lateinit var dbInstance : PracticeCodeRoomDatabase
        private lateinit var codeLanguageDao : CodeLanguageDao
        private lateinit var masterContentDao: MasterContentDao

        private fun getInstance(): PCFirebaseHandler {

            if( singleInstance == null ) {
                singleInstance = PCFirebaseHandler()
            }
            return singleInstance!!

        }

        fun getAPI( application: Application ) : API {

            if( singleInstance == null ) {
                singleInstance = getInstance()
                dbInstance = PracticeCodeRoomDatabase.getDbInstance(application)
                codeLanguageDao = dbInstance.codeLanguageDao()
                masterContentDao = dbInstance.masterContentDao()
            }
            return singleInstance!!

        }

        private const val codeLanguageDB = "CodeLanguage"
        private const val masterContentDB = "MasterContent"

        private val codeLanguages : ArrayList<CodeLanguage> = ArrayList()
        private var masterContents : ArrayList<MasterContent> = ArrayList()
        private var masterContentMap : HashMap<String, ArrayList<MasterContent>> = HashMap()

    }

    init {
        getFirebaseDBReference(codeLanguageDB).addListenerForSingleValueEvent( this )
    }

    private fun getFirebaseDBReference( dbUrl : String ): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference( "$pcDBVersion/$dbUrl")
    }

    override fun insertOrUpdate(obj: Parcelable) {
        when (obj) {
            is CodeLanguage -> {
                if( obj.id.isEmpty() )
                    obj.id = getFirebaseSortedId(codeLanguageDB)

                insertToFirebase(codeLanguageDB, obj.id, obj )

            }
            is MasterContent -> {
                if( obj.id.isEmpty() )
                    obj.id = getFirebaseSortedId("$masterContentDB/${obj.languageId}")

                insertToFirebase( "$masterContentDB/${obj.languageId}", obj.id, obj )
            }
        }
    }

    private fun insertToFirebase( database: String, id: String, obj: Parcelable) =
        getFirebaseDBReference( "$database/$id" ).setValue(obj)


    override fun insertOrUpdate(vararg obj: Parcelable) {
        for( data in obj ) {
            insertOrUpdate(data)
        }
    }

    override fun insertOrUpdate(obj: ArrayList<out Parcelable>) {
        for( data in obj ) {
            insertOrUpdate(data)
        }
    }

    private fun getFirebaseSortedId( dbUrl: String ) : String =
         getFirebaseDBReference(dbUrl).push().key!!


    override fun getAllCodeLanguage(): ArrayList<CodeLanguage> = codeLanguages


    override fun getAllMasterContent(): ArrayList<MasterContent> =
         masterContents


    override fun getAllMasterContent(languageId: String): ArrayList<MasterContent> {
        return if( masterContentMap.containsKey(languageId)) masterContentMap[languageId]!! else ArrayList()
    }

    override fun fetchLiveCodeLanguages(): LiveData<List<CodeLanguage>>
            = codeLanguageDao.listAllLive()

    override fun fetchLiveCodeLanguageById(id: String): LiveData<CodeLanguage>
            = codeLanguageDao.findLiveById(id)

    override fun fetchLiveMasterContents(): LiveData<List<MasterContent>>
            = masterContentDao.listAllLive()

    override fun fetchLiveMasterContentById(id: String): LiveData<MasterContent>
            = masterContentDao.findLiveById(id)

    override fun fetchLiveMasterContentsByLanguage(languageId: String): LiveData<List<MasterContent>>
            = masterContentDao.listAllLiveByID(languageId)


}
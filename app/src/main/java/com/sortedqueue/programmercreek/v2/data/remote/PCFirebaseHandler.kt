package com.sortedqueue.programmercreek.v2.data.remote

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import android.os.Parcelable
import android.util.Log
import com.google.firebase.database.*
import com.sortedqueue.programmercreek.v2.base.BasePreferencesAPI
import com.sortedqueue.programmercreek.v2.base.PCPreferences
import com.sortedqueue.programmercreek.v2.data.api.API
import com.sortedqueue.programmercreek.v2.data.api.DBIntentService
import com.sortedqueue.programmercreek.v2.data.db.*
import com.sortedqueue.programmercreek.v2.data.model.CodeLanguage
import com.sortedqueue.programmercreek.v2.data.model.MasterContent
import java.util.*
import kotlin.collections.ArrayList

@Suppress("PrivatePropertyName")
@SuppressLint("StaticFieldLeak")
class PCFirebaseHandler( private val application: Application ) : API, ValueEventListener {

    private val TAG = PCFirebaseHandler::class.java.simpleName

    override fun onCancelled(error: DatabaseError) { updateInProgress = false }

    override fun onDataChange(snapshot: DataSnapshot) {
        if( snapshot.hasChildren() ) {
            Log.d(TAG, "CodeLanguages : " + snapshot.children.count())
            val codeLanguages = ArrayList<CodeLanguage>()
            for( child in snapshot.children ) {
                val codeLanguage = child.getValue(CodeLanguage::class.java)
                insertAsync( codeLanguage )
                getAllContent( codeLanguage )
            }
            mMutableCodeLanguageLiveData.value = codeLanguages
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

    private fun getAllContent( codeLanguage: CodeLanguage? ) {

        getFirebaseDBReference("$masterContentDB/${codeLanguage!!.id}" )
                .addListenerForSingleValueEvent( object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        updateInProgress = false
                    }

                    override fun onDataChange( snapshot: DataSnapshot) {
                        for( child in snapshot.children ) {
                            val masterContent = child.getValue(MasterContent::class.java)
                            insertAsync( masterContent )
                        }
                        updateInProgress = false
                    }

                })

    }


    companion object {

        private var singleInstance : PCFirebaseHandler?= null
        private var dbPreferencesAPI : BasePreferencesAPI ?= null

        private lateinit var dbInstance : PracticeCodeRoomDatabase
        private lateinit var codeLanguageDao : CodeLanguageDao
        private lateinit var masterContentDao: MasterContentDao

        private var updateInProgress = false

        private fun getInstance( application: Application ): PCFirebaseHandler {
            if( singleInstance == null ) {
                singleInstance = PCFirebaseHandler( application )
            }
            return singleInstance!!
        }

        fun getAPI( application: Application ) : API {

            if( singleInstance == null ) {
                singleInstance = getInstance( application )
                dbInstance = PracticeCodeRoomDatabase.getDbInstance(application)
                dbPreferencesAPI = PCPreferences.getPreferencesAPI( application )
                codeLanguageDao = dbInstance.codeLanguageDao()
                masterContentDao = dbInstance.masterContentDao()
            }
            return singleInstance!!

        }

        private const val pcDBVersion = "v2"
        private const val codeLanguageDB = "CodeLanguage"
        private const val masterContentDB = "MasterContent"
        private const val dbVersions = "dbVersions"
    }

    init {
        checkForDataUpdates()
    }

    private fun checkForDataUpdates() {

        if( updateInProgress ) {
            return
        }

        getFirebaseDBReference(dbVersions).addListenerForSingleValueEvent( object : ValueEventListener {

            override fun onCancelled( error : DatabaseError) { updateInProgress = false }

            override fun onDataChange( snapshot: DataSnapshot ) {

                if( dbPreferencesAPI != null ) {

                    val remoteDBMap = HashMap<String, Long>()
                    if( snapshot.children.count() > 0 ) {
                        for( child in snapshot.children ) {
                            for( gChild in child.children ) {
                                if( gChild.value is Long )
                                    remoteDBMap["${child.key}/${gChild.key!!}"] = gChild.value.toString().toLong()
                            }
                        }
                    }

                    val localDBMap = dbPreferencesAPI?.getDBVersions()
                    Log.d(TAG, "DbUpdates : $localDBMap")
                    if( localDBMap!!.isEmpty() ) {
                        initCodeLanguages()
                        dbPreferencesAPI?.setDBVersions(remoteDBMap)
                    }
                    else {
                        checkForIndividualUpdates( remoteDBMap )
                        mMutableCodeLanguageLiveData.value = null
                        codeLanguageDao.listAllLive().observeForever { t -> mMutableCodeLanguageLiveData.value = t }
                    }

                }

            }

        })

    }

    private fun initCodeLanguages() {
        Log.d(TAG, "initCodeLanguages" )
        getFirebaseDBReference( codeLanguageDB ).addListenerForSingleValueEvent( this )
    }

    @Synchronized
    private fun checkForIndividualUpdates( dbMap: HashMap<String, Long> ) {
        DBIntentService.startDBUpdate( application, dbMap )
    }

    private fun getFirebaseDBReference( dbUrl : String ): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference( "$pcDBVersion/$dbUrl")
    }

    override fun insertOrUpdate(obj: Parcelable) {
        when (obj) {
            is CodeLanguage -> {
                if( obj.id.isEmpty() )
                    obj.id = getFirebaseSortedId(codeLanguageDB)

                insertToFirebase(codeLanguageDB, obj.id, obj, obj.updated )

            }
            is MasterContent -> {
                if( obj.id.isEmpty() )
                    obj.id = getFirebaseSortedId("$masterContentDB/${obj.languageId}")

                insertToFirebase( "$masterContentDB/${obj.languageId}", obj.id, obj, obj.updated )
            }
        }
    }

    private fun insertToFirebase( database: String, id: String, obj: Parcelable, updatedAt : Long ) {
        getFirebaseDBReference( "$database/$id" ).setValue(obj)
        val key = if( obj is CodeLanguage ) "$database/$id" else database
        getFirebaseDBReference( "$dbVersions/$key" ).setValue(updatedAt)
    }


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

    private val mMutableCodeLanguageLiveData = MutableLiveData<List<CodeLanguage>>()

    override fun fetchLiveCodeLanguages(): LiveData<List<CodeLanguage>> //= mMutableCodeLanguageLiveData
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
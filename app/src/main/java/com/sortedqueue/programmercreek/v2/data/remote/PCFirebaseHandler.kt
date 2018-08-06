package com.sortedqueue.programmercreek.v2.data.remote

import android.content.Context
import android.os.Parcelable
import com.google.firebase.database.*
import com.sortedqueue.programmercreek.v2.data.local.*
import java.util.*

class PCFirebaseHandler( context: Context ) : API, ValueEventListener {


    override fun onCancelled(error: DatabaseError) {

    }

    override fun onDataChange(snapshot: DataSnapshot) {
        if( snapshot.hasChildren() ) {

            codeLanguages.clear()
            masterContentMap.clear()
            masterContents.clear()

            for( child in snapshot.children ) {
                val codeLanguage = child.getValue(CodeLanguage::class.java)
                codeLanguageDao.insert(codeLanguage!!)
                codeLanguages.add(codeLanguage)
                masterContentMap[codeLanguage.id] = ArrayList()
            }
            getAllContent()
        }
    }

    private fun getAllContent() {
        for( language in masterContentMap.keys ) {
            getFirebaseDBReference("${masterContentDB}/$language" )
                    .addListenerForSingleValueEvent( object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {

                        }

                        override fun onDataChange( snapshot: DataSnapshot) {
                            for( child in snapshot.children ) {
                                val masterContent = child.getValue(MasterContent::class.java)
                                dbInstance.masterContentDao().insert(masterContent!!)
                                masterContentMap[masterContent.id]!!.add(masterContent)
                            }
                        }

                    })
        }
    }


    companion object {

        private var instance : PCFirebaseHandler?= null
        private const val pcDBVersion = "v2"
        private lateinit var dbInstance : PracticeCodeRoomDatabase
        private lateinit var codeLanguageDao : CodeLanguageDao
        private lateinit var masterContentDao: MasterContentDao

        private fun getInstance( context: Context ) : PCFirebaseHandler {

            if( instance == null ) {
                instance = PCFirebaseHandler(context.applicationContext)
            }
            return instance!!

        }

        fun getAPI( context: Context ) : API {

            if( instance == null ) {
                instance = getInstance( context )
                dbInstance = PracticeCodeRoomDatabase.getDbInstance(context.applicationContext)
                codeLanguageDao = dbInstance.codeLanguageDao()
                masterContentDao = dbInstance.masterContentDao()
            }
            return instance!!

        }

        private const val codeLanguageDB = "CodeLanguage"
        private const val masterContentDB = "MasterContent"

        private val codeLanguages : ArrayList<CodeLanguage> = ArrayList()
        private var masterContents : ArrayList<MasterContent> = ArrayList()
        private var masterContentMap : HashMap<String, ArrayList<MasterContent>> = HashMap()

    }

    init {
        getFirebaseDBReference(codeLanguageDB).addValueEventListener( this )
    }

    private fun getFirebaseDBReference( dbUrl : String ): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference( "${pcDBVersion}/$dbUrl")
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
                    obj.id = getFirebaseSortedId("${masterContentDB}/${obj.languageId}")

                insertToFirebase( "${masterContentDB}/${obj.languageId}", obj.id, obj )
            }
        }
    }

    private fun insertToFirebase( database: String, id: String, obj: Parcelable) {
        getFirebaseDBReference( "$database/$id" ).setValue(obj)
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

    private fun getFirebaseSortedId( dbUrl: String ) : String {
        return getFirebaseDBReference(dbUrl).push().key!!
    }

    override fun getAllCodeLanguage(): ArrayList<CodeLanguage> {
        return codeLanguages
    }

    override fun getAllMasterContent(): ArrayList<MasterContent> {
        return masterContents
    }

    override fun getAllMasterContent(languageId: String): ArrayList<MasterContent> {
        return if( masterContentMap.containsKey(languageId)) masterContentMap[languageId]!! else ArrayList()
    }



}
package com.sortedqueue.programmercreek.v2.data.remote

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseDatabaseProvider {

    fun getFirebaseDBReference( dbUrl : String ): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference( "$pcDBVersion/$dbUrl")
    }

    companion object {
        private const val pcDBVersion = "v2"
        const val codeLanguageDB = "CodeLanguage"
        const val masterContentDB = "MasterContent"
        const val dbVersions = "dbVersions"

        private var instance : FirebaseDatabaseProvider = FirebaseDatabaseProvider()

        @Synchronized
        fun getInstance() : FirebaseDatabaseProvider {
            return instance
        }

    }

}
package com.sortedqueue.programmercreek.auth

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.database.CreekUser
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.util.CreekPreferences

class SaveUserIntentService : IntentService(SaveUserIntentService::class.java.simpleName) {

    private lateinit var creekPreferences : CreekPreferences

    override fun onHandleIntent(intent: Intent?) {
        val email = intent?.getStringExtra(ARG_EMAIL) ?: ""
        val creekUser : CreekUser? = intent?.getParcelableExtra(ARG_USER)

        creekPreferences = CreekApplication.creekPreferences ?: CreekPreferences(this)

        FirebaseDatabaseHandler(this).getCreekUser(email, object : FirebaseDatabaseHandler.GetCreekUserListner {
            override fun onSuccess(creekUser: CreekUser) {
                if (creekUser.userId.equals("", ignoreCase = true)) {
                    creekUser.userId = creekPreferences.userId
                    creekUser.wasAnonUser = if (creekPreferences.isAnonAccount) "Yes" else "No"
                }
                saveUser()
            }

            override fun onFailure(databaseError: DatabaseError?) {
                //New signup
                creekUser?.save(this@SaveUserIntentService)
                saveUser()
            }
        })
    }

    private fun saveUser() {
        FirebaseDatabaseHandler(this ).getCreekUser(creekPreferences.getSignInAccount(),
                object : FirebaseDatabaseHandler.GetCreekUserListner {
            override fun onSuccess(creekUser: CreekUser) {

            }

            override fun onFailure(databaseError: DatabaseError?) {

            }
        })

    }

    companion object {

        private val ARG_EMAIL = "email"
        private val ARG_USER = "user"

        fun startService(context: Context, email : String, creekUser: CreekUser? ) {
            context.startService(Intent(context, SaveUserIntentService::class.java).apply {
                putExtra(ARG_EMAIL, email)
                putExtra(ARG_USER, creekUser)
            })
        }
    }

}
package com.sortedqueue.programmercreek.v2.data.api

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.sortedqueue.programmercreek.v2.base.PCPreferences

@Suppress("UNCHECKED_CAST")
class DBIntentService : IntentService( DBIntentService::class.java.simpleName ) {

    private val TAG = DBIntentService::class.java.simpleName

    override fun onHandleIntent( intent: Intent? ) {

        if( intent != null ) {

            val localDbMap : Map<String, Long> = PCPreferences(this).getDBVersions()
            val remoteDbMap : HashMap<String, Long> = intent.getSerializableExtra(remoteMap) as HashMap<String, Long>

            for( key in remoteDbMap.keys ) {
                if( localDbMap.containsKey(key) && remoteDbMap[key]!! > localDbMap[key]!! ) {

                }
                else {

                }

            }
            //val API = PCFirebaseHandler( this.application )


        }


    }

    companion object {

        private const val remoteMap = "remoteMap"

        fun startDBUpdate( context: Context,
                           remoteDbMap : HashMap<String, Long> ) {

            val intent = Intent( context, DBIntentService::class.java )
            intent.putExtra( remoteMap, remoteDbMap )
            context.startService(intent)

        }

    }
}
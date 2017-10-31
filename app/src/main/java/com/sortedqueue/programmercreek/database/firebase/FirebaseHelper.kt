package com.sortedqueue.programmercreek.database.firebase

import android.content.Context
import android.util.Log
import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.dashboard.DashboardNavigationListener
import com.sortedqueue.programmercreek.database.*
import com.sortedqueue.programmercreek.util.CommonUtils
import java.util.*

/**
 * Created by Alok on 31/10/17.
 */
class FirebaseHelper( context: Context, val dashboardNavigationListener: DashboardNavigationListener ) :
        FirebaseDatabaseHandler.ModuleInterface,
        FirebaseDatabaseHandler.ProgramIndexInterface,
        FirebaseDatabaseHandler.ProgramTableInterface,
        FirebaseDatabaseHandler.ProgramWikiInterface,
        FirebaseDatabaseHandler.SyntaxInterface {
    override fun getSyntaxModules(syntaxModules: ArrayList<SyntaxModule>) {
        incrementAndFinish()
    }

    private val TAG: String? = FirebaseHelper::class.java.simpleName

    private fun incrementAndFinish() {
        progressCount++
        Log.d(TAG,  progressCount.toString() + " == " + (3 + languageModuleCount) )
        if( progressCount == (3 + languageModuleCount) ) {
            CommonUtils.dismissProgressDialog()
            dashboardNavigationListener.hideLanguageFragment()
        }
    }

    val firebaseDatabaseHandler = FirebaseDatabaseHandler(context)
    var progressCount = 0
    var languageModuleCount = 0

    init {
        CommonUtils.displayProgressDialog(context, R.string.downloading_content)
        firebaseDatabaseHandler.initializeModules(this)
    }

    override fun getModules(languageModules: ArrayList<LanguageModule>) {
        languageModuleCount = languageModules.size
        for( languageModule in languageModules ) {
            firebaseDatabaseHandler.initializeSyntax(languageModule, this)
        }
        firebaseDatabaseHandler.initializeProgramIndexes( this)
        firebaseDatabaseHandler.initializeProgramTables( this)
        firebaseDatabaseHandler.initializeProgramWiki(this)
    }

    override fun getProgramIndexes(program_indices: ArrayList<ProgramIndex>) {
        incrementAndFinish()
    }

    override fun onError(error: DatabaseError) {
        incrementAndFinish()
    }

    override fun getProgramTables(program_tables: ArrayList<ProgramTable>) {
        incrementAndFinish()
    }

    override fun getProgramWiki(programWikis: ArrayList<WikiModel>) {
        incrementAndFinish()
    }


}
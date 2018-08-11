package com.sortedqueue.programmercreek.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log

import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.database.CreekUser
import com.sortedqueue.programmercreek.database.CreekUserDB
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.database.LanguageModule
import com.sortedqueue.programmercreek.database.SyntaxModule
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler

import org.json.JSONException
import org.json.JSONObject

import java.util.HashSet

import co.uk.rushorm.core.RushCore
import co.uk.rushorm.core.RushSearch
import co.uk.rushorm.core.RushSearchCallback

/**
 * Created by Alok on 07/12/16.
 */

class CreekPreferences(private val context: Context) {

    private var programIndex: Int = 0
    private var programTables: Int = 0

    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val WIKI_HELP = "Wiki_help"
    private val KEY_WELCOME_DONE = "welcome_done"
    private val TAG = CreekPreferences::class.java.simpleName

    private val runMode = RUN_AUTO
    val textSize = 16


    fun getProgramIndex(): Int {
        val programLanguage = programLanguage
        when (programLanguage) {
            "c" -> programIndex = sharedPreferences.getInt(KEY_PROG_INDEX_INSERT, -1)
            "java" -> programIndex = sharedPreferences.getInt(KEY_PROG_INDEX_INSERT_JAVA, -1)
            "cpp", "c++" -> programIndex = sharedPreferences.getInt(KEY_PROG_INDEX_INSERT_CPP, -1)
            "usp" -> programIndex = sharedPreferences.getInt(KEY_PROG_TABLE_INSERT_USP, -1)
            "sql" -> programIndex = sharedPreferences.getInt(KEY_PROG_TABLE_INSERT_SQL, -1)
        }
        return programIndex
    }

    fun setProgramIndex(programIndex: Int) {
        this.programIndex = programIndex
        when (programLanguage) {
            "c" -> setCProgramIndex(programIndex)
            "java" -> setJavaProgramIndex(programIndex)
            "cpp", "c++" -> setCppProgramIndex(programIndex)
            "usp" -> setUspProgramIndex(programIndex)
        }
    }

    var isAnonAccount: Boolean
        get() = getSignInAccount() == userId
        set(isAnonAccount) = sharedPreferences.edit().putBoolean(KEY_ANON_ACCOUNT, isAnonAccount).apply()

    private fun setUspProgramIndex(programIndex: Int) {
        sharedPreferences.edit().putInt(KEY_PROG_INDEX_INSERT_USP, programIndex).apply()
    }

    private fun setCppProgramIndex(programIndex: Int) {
        sharedPreferences.edit().putInt(KEY_PROG_INDEX_INSERT_CPP, programIndex).apply()
    }

    private fun setCProgramIndex(programIndex: Int) {
        sharedPreferences.edit().putInt(KEY_PROG_INDEX_INSERT, programIndex).apply()
    }

    private fun setJavaProgramIndex(programIndex: Int) {
        sharedPreferences.edit().putInt(KEY_PROG_INDEX_INSERT_JAVA, programIndex).apply()
    }

    fun getProgramTables(): Int {

        when (programLanguage) {
            "c" -> programTables = sharedPreferences.getInt(KEY_PROG_TABLE_INSERT, -1)
            "java" -> programTables = sharedPreferences.getInt(KEY_PROG_TABLE_INSERT_JAVA, -1)
            "cpp", "c++" -> programTables = sharedPreferences.getInt(KEY_PROG_TABLE_INSERT_CPP, -1)
            "usp" -> programTables = sharedPreferences.getInt(KEY_PROG_TABLE_INSERT_USP, -1)
            "sql" -> programTables = sharedPreferences.getInt(KEY_PROG_TABLE_INSERT_SQL, -1)
        }
        return programTables
    }

    fun setProgramTables(programTables: Int) {
        this.programTables = programTables
        when (programLanguage) {
            "c" -> setCProgramTablesIndex(programTables)
            "java" -> setJavaProgramTablesIndex(programTables)
            "cpp", "c++" -> setCPPProgramTablesIndex(programTables)
            "usp" -> setUSPProgramTablesIndex(programTables)
            "sql" -> setSqlProgramTablesIndex(programTables)
        }
    }

    private fun setSqlProgramTablesIndex(programTables: Int) {
        sharedPreferences.edit().putInt(KEY_PROG_TABLE_INSERT_SQL, programTables).apply()
    }

    private fun setUSPProgramTablesIndex(programTables: Int) {
        sharedPreferences.edit().putInt(KEY_PROG_TABLE_INSERT_USP, programTables).apply()
    }

    private fun setCPPProgramTablesIndex(programTables: Int) {
        sharedPreferences.edit().putInt(KEY_PROG_TABLE_INSERT_CPP, programTables).apply()
    }

    private fun setJavaProgramTablesIndex(programTables: Int) {
        sharedPreferences.edit().putInt(KEY_PROG_TABLE_INSERT_JAVA, programTables).apply()
    }

    private fun setCProgramTablesIndex(programTables: Int) {
        sharedPreferences.edit().putInt(KEY_PROG_TABLE_INSERT, programTables).apply()
    }

    fun getSignInAccount(): String {
        return sharedPreferences.getString(SIGN_IN_ACCOUNT, "")
    }

    fun setSignInAccount(signInAccount: String) {
        sharedPreferences.edit().putString(SIGN_IN_ACCOUNT, signInAccount).apply()
    }

    fun getAccountName(): String {
        return sharedPreferences.getString(ACCOUNT_NAME, "")
    }

    fun setAccountName(accountName: String) {
        sharedPreferences.edit().putString(ACCOUNT_NAME, accountName).apply()
    }

    fun getAccountPhoto(): String {
        return sharedPreferences.getString(ACCOUNT_PHOTO, "")
    }

    fun setAccountPhoto(accountPhoto: String) {
        sharedPreferences.edit().putString(ACCOUNT_PHOTO, accountPhoto).apply()
    }

    var programLanguage: String
        get() = sharedPreferences.getString(PROGRAM_LANGUAGE, "")
        set(language) {
            sharedPreferences.edit().putString(PROGRAM_LANGUAGE, language).apply()

            val creekUser = CreekUser()
            creekUser.userFullName = getAccountName()
            creekUser.programLanguage = programLanguage
            creekUser.userPhotoUrl = getAccountPhoto()
            creekUser.emailId = getSignInAccount()
            creekUser.userId = userId
            creekUser.wasAnonUser = if (isAnonAccount) "Yes" else "No"
            FirebaseDatabaseHandler(context).writeCreekUser(creekUser)

            try {
                CreekAnalytics.logEvent(TAG, "setProgramLanguage : " + language)
                CreekAnalytics.logEvent(TAG, JSONObject(Gson().toJson(creekUser)))
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }

    val programWiki: String
        get() {
            var wikiUrl = "https://programercreek.blogspot.in/2016/12/c-programming-hello-world.html"
            if (programLanguage.equals("java", ignoreCase = true)) {
                wikiUrl = "http://www.instanceofjava.com/2014/12/program-to-print-prime-numbers-in-java.html"
            }
            return wikiUrl
        }

    val wikiHelp: Boolean
        get() = sharedPreferences.getBoolean(WIKI_HELP, false)

    fun setWikihelp(wikiHelp: Boolean) {
        sharedPreferences.edit().putBoolean(WIKI_HELP, wikiHelp).apply()
    }

    var modulesInserted: String
        get() {
            when (programLanguage) {
                "java" -> return sharedPreferences.getString(KEY_JAVA_MODULE, "0")
                "c" -> return sharedPreferences.getString(KEY_C_MODULE, "0")
                "c++", "cpp" -> return sharedPreferences.getString(KEY_CPP_MODULE, "0")
                "sql" -> return sharedPreferences.getString(KEY_SQL_MODULE, "0")
            }
            return "0"
        }
        set(modulesInserted) {
            when (programLanguage) {
                "java" -> setJavaModulesInserted(modulesInserted)
                "c" -> setCModulesInserted(modulesInserted)
                "c++", "cpp" -> setCPPModulesInserted(modulesInserted)
                "sql" -> setSqlModulesInserted(modulesInserted)
            }
        }

    private fun setSqlModulesInserted(modulesInserted: String) {
        sharedPreferences.edit().putString(KEY_SQL_MODULE, modulesInserted).apply()
    }

    private fun setCPPModulesInserted(modulesInserted: String) {
        sharedPreferences.edit().putString(KEY_CPP_MODULE, modulesInserted).apply()
    }

    private fun setCModulesInserted(modulesInserted: String) {
        sharedPreferences.edit().putString(KEY_C_MODULE, modulesInserted).apply()
    }

    private fun setJavaModulesInserted(modulesInserted: String) {
        sharedPreferences.edit().putString(KEY_JAVA_MODULE, modulesInserted).apply()
    }

    var programWikiInserted: String
        get() {
            when (programLanguage.toLowerCase()) {
                "java" -> return sharedPreferences.getString(KEY_JAVA_WIKI, "0")
                "c" -> return sharedPreferences.getString(KEY_C_WIKI, "0")
                "c++", "cpp" -> return sharedPreferences.getString(KEY_CPP_WIKI, "0")
                "usp" -> return sharedPreferences.getString(KEY_USP_WIKI, "0")
                "sql" -> return sharedPreferences.getString(KEY_SQL_WIKI, "0")
            }
            return "0"
        }
        set(modulesInserted) {
            when (programLanguage.toLowerCase()) {
                "java" -> setJavaWikiInserted(modulesInserted)
                "c" -> setCWikiInserted(modulesInserted)
                "c++", "cpp" -> setCPPWikiInserted(modulesInserted)
                "sql" -> setSqlWikiInserted(modulesInserted)
            }
        }

    var syntaxInserted: String
        get() {
            when (programLanguage.toLowerCase()) {
                "java" -> return sharedPreferences.getString(KEY_JAVA_SYNTAX, "0")
                "c" -> return sharedPreferences.getString(KEY_C_SYNTAX, "0")
                "c++", "cpp" -> return sharedPreferences.getString(KEY_CPP_SYNTAX, "0")
            }
            return "0"
        }
        set(modulesInserted) {
            when (programLanguage.toLowerCase()) {
                "java" -> setJavaSyntaxInserted(modulesInserted)
                "c" -> setCSyntaxInserted(modulesInserted)
                "c++", "cpp" -> setCPPSyntaxInserted(modulesInserted)
                "sql" -> setSqlSyntaxInserted(modulesInserted)
            }
        }

    private fun setSqlSyntaxInserted(modulesInserted: String) {
        sharedPreferences.edit().putString(KEY_SQL_SYNTAX, modulesInserted).apply()
    }

    private fun setCPPSyntaxInserted(modulesInserted: String) {
        sharedPreferences.edit().putString(KEY_CPP_SYNTAX, modulesInserted).apply()
    }

    private fun setCSyntaxInserted(modulesInserted: String) {
        sharedPreferences.edit().putString(KEY_C_SYNTAX, modulesInserted).apply()
    }

    private fun setJavaSyntaxInserted(modulesInserted: String) {
        sharedPreferences.edit().putString(KEY_JAVA_SYNTAX, modulesInserted).apply()
    }

    private fun setCPPWikiInserted(modulesInserted: String) {
        sharedPreferences.edit().putString(KEY_CPP_WIKI, modulesInserted).apply()
    }

    private fun setSqlWikiInserted(modulesInserted: String) {
        sharedPreferences.edit().putString(KEY_SQL_WIKI, modulesInserted).apply()
    }

    private fun setCWikiInserted(modulesInserted: String) {
        sharedPreferences.edit().putString(KEY_C_WIKI, modulesInserted).apply()
    }

    private fun setJavaWikiInserted(modulesInserted: String) {
        sharedPreferences.edit().putString(KEY_JAVA_WIKI, modulesInserted).apply()
    }

    fun checkUpdateDB(creekUserDB: CreekUserDB) {
        RushSearch().find(CreekUserDB::class.java) { list ->
            if (list == null || list.size == 0) {
                //save creekDB
                creekUserDB.save()

            } else {
                for (localDB in list) {
                    if (creekUserDB != localDB) {
                        //Check which db needs to be updated
                        if (creekUserDB.getcModuleDBVersion() != localDB.getcModuleDBVersion()) {
                            //TODO
                        }
                        if (creekUserDB.getcProgramIndexDBVersion() > localDB.getcProgramIndexDBVersion()) {
                            setCProgramIndex(-1)
                        }
                        if (creekUserDB.getcProgramTableDBVersion() > localDB.getcProgramTableDBVersion()) {
                            setCProgramTablesIndex(-1)
                        }
                        if (creekUserDB.getcSyntaxDBVersion() != localDB.getcSyntaxDBVersion()) {
                            setCSyntaxInserted("0")
                            RushSearch().whereEqual("syntaxLanguage", "c").find(SyntaxModule::class.java
                            ) { moduleList ->
                                if (moduleList != null) {
                                    RushCore.getInstance().delete(moduleList)
                                }
                            }

                        }

                        //Cpp
                        if (creekUserDB.cppModuleDBVersion != localDB.cppModuleDBVersion) {
                            setCPPModulesInserted("0")
                            RushSearch().whereEqual("moduleLanguage", "cpp").or().whereEqual("moduleLanguage", "c++").find(LanguageModule::class.java) { list ->
                                if (list != null) {
                                    RushCore.getInstance().delete(list)
                                }
                            }
                        }
                        if (creekUserDB.cppProgramIndexDBVersion > localDB.cppProgramIndexDBVersion) {
                            setCppProgramIndex(-1)
                        }
                        if (creekUserDB.cppProgramTableDBVersion > localDB.cppProgramTableDBVersion) {
                            setCPPProgramTablesIndex(-1)
                        }
                        if (creekUserDB.cppSyntaxDBVersion != localDB.cppSyntaxDBVersion) {
                            setCPPSyntaxInserted("0")
                            RushSearch().whereEqual("syntaxLanguage", "cpp").or().whereEqual("syntaxLanguage", "c++").find(SyntaxModule::class.java
                            ) { moduleList ->
                                if (moduleList != null) {
                                    RushCore.getInstance().delete(moduleList)
                                }
                            }
                        }

                        //Java
                        if (creekUserDB.javaModuleDBVersion != localDB.javaModuleDBVersion) {
                            setJavaModulesInserted("0")
                            RushSearch().whereEqual("moduleLanguage", "java").find(LanguageModule::class.java) { list ->
                                if (list != null) {
                                    RushCore.getInstance().delete(list)
                                }
                            }
                        }
                        if (creekUserDB.javaProgramIndexDBVersion > localDB.javaProgramIndexDBVersion) {
                            setJavaProgramIndex(-1)
                        }
                        if (creekUserDB.javaProgramTableDBVersion > localDB.javaProgramTableDBVersion) {
                            setJavaProgramTablesIndex(-1)
                        }
                        if (creekUserDB.javaSyntaxDBVersion != localDB.javaSyntaxDBVersion) {
                            setJavaSyntaxInserted("0")
                            RushSearch().whereEqual("syntaxLanguage", "java").find(SyntaxModule::class.java
                            ) { list ->
                                if (list != null) {
                                    RushCore.getInstance().delete(list)
                                }
                            }
                        }

                        //C Premium
                        if (creekUserDB.getcModuleDBVersionPremium() == localDB.getcModuleDBVersionPremium()) {

                        }
                        if (creekUserDB.getcProgramIndexDBVersionPremium() > localDB.getcProgramIndexDBVersionPremium()) {

                        }
                        if (creekUserDB.getcProgramTableDBVersionPremium() > localDB.getcProgramTableDBVersionPremium()) {

                        }
                        if (creekUserDB.getcSyntaxDBVersionPremium() == localDB.getcSyntaxDBVersionPremium()) {

                        }

                        //CPP Premium
                        if (creekUserDB.cppModuleDBVersionPremium != localDB.cppModuleDBVersionPremium) {

                        }
                        if (creekUserDB.cppProgramIndexDBVersionPremium > localDB.cppProgramIndexDBVersionPremium) {

                        }
                        if (creekUserDB.cppProgramTableDBVersionPremium > localDB.cppProgramTableDBVersionPremium) {

                        }
                        if (creekUserDB.cppSyntaxDBVersionPremium == localDB.cppSyntaxDBVersionPremium) {

                        }


                        //Java Premium
                        if (creekUserDB.javaModuleDBVersionPremium != localDB.javaModuleDBVersionPremium) {

                        }
                        if (creekUserDB.javaProgramIndexDBVersionPremium > localDB.javaProgramIndexDBVersionPremium) {

                        }
                        if (creekUserDB.javaProgramTableDBVersionPremium > localDB.javaProgramTableDBVersionPremium) {

                        }
                        if (creekUserDB.javaSyntaxDBVersionPremium != localDB.javaSyntaxDBVersionPremium) {

                        }
                    }
                }
            }
        }
    }

    var isWelcomeDone: Boolean
        get() = sharedPreferences.getBoolean(KEY_WELCOME_DONE, false)
        set(welcomeDone) = sharedPreferences.edit().putBoolean(KEY_WELCOME_DONE, welcomeDone).apply()

    fun saveCreekUserStats(creekUserStats: CreekUserStats) {
        val gson = Gson()
        val creekUserDBString = gson.toJson(creekUserStats)
        sharedPreferences.edit().putString("creekUserStats", creekUserDBString).apply()
        CreekApplication.instance.creekUserStats = creekUserStats
    }

    val creekUserStats: CreekUserStats?
        get() {
            val jsonString = sharedPreferences.getString("creekUserStats", "")
            if (jsonString != "") {
                return Gson().fromJson(jsonString, CreekUserStats::class.java)
            } else {
                return null
            }
        }

    var creekUserDB: CreekUserDB?
        get() {
            val jsonString = sharedPreferences.getString("CreekUserDB", "")
            if (jsonString != "") {
                return Gson().fromJson(jsonString, CreekUserDB::class.java)
            } else {
                return null
            }
        }
        set(creekUserDB) {
            val gson = Gson()
            val creekUserDBString = gson.toJson(creekUserDB)
            sharedPreferences.edit().putString("CreekUserDB", creekUserDBString).apply()
        }

    fun checkProgramIndexUpdate(): Boolean {
        val creekUserDB = creekUserDB
        if( creekUserDB == null )
            return false

        var result = true
        when (programLanguage) {
            "c" -> result = getProgramIndex().toDouble() == creekUserDB!!.getcProgramIndexDBVersion()
            "c++", "cpp" -> result = getProgramIndex() == creekUserDB!!.cppProgramIndexDBVersion.toInt()
            "java" -> result = getProgramIndex() == creekUserDB!!.javaProgramIndexDBVersion.toInt()
            "usp" -> result = getProgramIndex() == creekUserDB!!.uspProgramIndexDBVersion.toInt()
            "sql" -> result = getProgramIndex() == creekUserDB!!.sqlProgramIndexDBVersion.toInt()
        }
        return result
    }

    fun checkProgramTableUpdate(): Boolean {
        val creekUserDB = creekUserDB
        var result = true
        if (creekUserDB == null) {
            return false
        }
        when (programLanguage) {
            "c" -> result = getProgramTables() == creekUserDB.getcProgramTableDBVersion().toInt()
            "c++", "cpp" -> result = getProgramTables() == creekUserDB.cppProgramTableDBVersion.toInt()
            "java" -> result = getProgramTables() == creekUserDB.javaProgramTableDBVersion.toInt()
            "usp" -> result = getProgramTables() == creekUserDB.uspProgramTableDBVersion.toInt()
            "sql" -> result = getProgramTables() == creekUserDB.sqlProgramTableDBVersion.toInt()
        }
        return result
    }

    val programIndexDifference: Int
        get() {
            val creekUserDB = creekUserDB
            var result: Int
            result = getProgramIndex()
            if (result == -1) {
                result = 0
            }

            when (programLanguage) {
                "c" -> result = (creekUserDB!!.getcProgramIndexDBVersion() - result).toInt()
                "c++", "cpp" -> result = (creekUserDB!!.cppProgramIndexDBVersion - result).toInt()
                "java" -> result = (creekUserDB!!.javaProgramIndexDBVersion - result).toInt()
                "usp" -> result = (creekUserDB!!.uspProgramIndexDBVersion - result).toInt()
                "sql" -> if (creekUserDB!!.sqlProgramIndexDBVersion - result != 0.0) {
                    result = (creekUserDB.sqlProgramIndexDBVersion - result).toInt()
                }
            }
            return result
        }

    val programTableDifference: Int
        get() {
            val creekUserDB = creekUserDB
            var result: Int
            result = getProgramTables()
            if (result == -1) {
                result = 0
            }

            when (programLanguage) {
                "c" -> result = (creekUserDB!!.getcProgramTableDBVersion() - result).toInt()
                "c++", "cpp" -> result = (creekUserDB!!.cppProgramTableDBVersion - result).toInt()
                "java" -> result = (creekUserDB!!.javaProgramTableDBVersion - result).toInt()
                "usp" -> result = (creekUserDB!!.uspProgramTableDBVersion - result).toInt()
                "sql" -> result = (creekUserDB!!.sqlProgramTableDBVersion - result).toInt()
            }
            return result
        }

    fun checkModulesUpdate(): Int {
        val creekUserDB = creekUserDB
        val alphaNumComparator = AlphaNumComparator()
        val modulesInserted = modulesInserted
        var result = 0
        when (programLanguage) {
            "c" -> result = alphaNumComparator.compare(modulesInserted, creekUserDB!!.getcModuleDBVersion())
            "c++", "cpp" -> result = alphaNumComparator.compare(modulesInserted, creekUserDB!!.cppModuleDBVersion)
            "java" -> result = alphaNumComparator.compare(modulesInserted, creekUserDB!!.cppModuleDBVersion)
            "sql" -> result = alphaNumComparator.compare(modulesInserted, creekUserDB!!.sqlModuleDBVersion)
        }
        return result
    }

    fun checkWikiUpdate(): Int {
        val creekUserDB = creekUserDB
        val alphaNumComparator = AlphaNumComparator()
        val wikiInserted = programWikiInserted
        var result = 0
        when (programLanguage) {
            "c" -> result = alphaNumComparator.compare(wikiInserted, creekUserDB!!.getcWikiDBVersion())
            "c++", "cpp" -> result = alphaNumComparator.compare(wikiInserted, creekUserDB!!.cppWikiDBVersion)
            "java" -> result = alphaNumComparator.compare(wikiInserted, creekUserDB!!.javaWikiDBVersion)
            "usp" -> result = alphaNumComparator.compare(wikiInserted, creekUserDB!!.uspWikiDBVersion)
            "sql" -> result = alphaNumComparator.compare(wikiInserted, creekUserDB!!.sqlWikiDBVersion)
        }
        return result
    }

    fun checkSyntaxUpdate(): Int {

        val creekUserDB = creekUserDB
        val alphaNumComparator = AlphaNumComparator()
        val syntaxInserted = syntaxInserted
        var result = 0

        when (programLanguage) {
            "c" -> {
                result = alphaNumComparator.compare(syntaxInserted, creekUserDB!!.getcSyntaxDBVersion())
                Log.d(TAG, "Compare : checkSyntaxUpdate : " + syntaxInserted + " : " + creekUserDB.getcSyntaxDBVersion() + " : result : " + result)
            }
            "c++", "cpp" -> {
                result = alphaNumComparator.compare(syntaxInserted, creekUserDB!!.cppSyntaxDBVersion)
                Log.d(TAG, "Compare : checkSyntaxUpdate : " + syntaxInserted + " : " + creekUserDB.cppSyntaxDBVersion + " : result : " + result)
            }
            "java" -> {
                result = alphaNumComparator.compare(syntaxInserted, creekUserDB!!.javaSyntaxDBVersion)
                Log.d(TAG, "Compare : checkSyntaxUpdate : " + syntaxInserted + " : " + creekUserDB.javaSyntaxDBVersion + " : result : " + result)
            }
            "sql" -> {
                result = alphaNumComparator.compare(syntaxInserted, creekUserDB!!.sqlSyntaxDBVersion)
                Log.d(TAG, "Compare : checkSyntaxUpdate : " + syntaxInserted + " : " + creekUserDB.sqlSyntaxDBVersion + " : result : " + result)
            }
        }
        return result
    }

    var introChapters: Boolean
        get() {

            when (programLanguage) {
                "c" -> return cIntroChapters
                "c++", "cpp" -> return cppIntroChapters
                "java" -> return javaIntroChapters
            }
            return false
        }
        set(isChapters) {
            when (programLanguage) {
                "c" -> cIntroChapters = isChapters
                "c++", "cpp" -> cppIntroChapters = isChapters
                "java" -> javaIntroChapters = isChapters
            }
        }
    private var cIntroChapters: Boolean
        get() = sharedPreferences.getBoolean(KEY_C_INTRO, false)
        set(isChapters) = sharedPreferences.edit().putBoolean(KEY_C_INTRO, isChapters).apply()

    private var cppIntroChapters: Boolean
        get() = sharedPreferences.getBoolean(KEY_CPP_INTRO, false)
        set(isChapters) = sharedPreferences.edit().putBoolean(KEY_CPP_INTRO, isChapters).apply()

    private var javaIntroChapters: Boolean
        get() = sharedPreferences.getBoolean(KEY_JAVA_INTRO, false)
        set(isChapters) = sharedPreferences.edit().putBoolean(KEY_JAVA_INTRO, isChapters).apply()

    fun getShowDialog(preferenceString: String): Boolean {
        return sharedPreferences.getBoolean(preferenceString, true)
    }

    fun setShowDialog(preferenceString: String, isShowDialog: Boolean) {
        sharedPreferences.edit().putBoolean(preferenceString, isShowDialog).apply()
    }

    fun clearCacheDetails() {
        sharedPreferences.edit().clear().apply()
        FirebaseDatabaseHandler(context).clearAllTables()
    }

    var isNotificationScheduled: Boolean
        get() = sharedPreferences.getBoolean("isNotification", false)
        set(isNotification) = sharedPreferences.edit().putBoolean("isNotification", isNotification).apply()

    var totalLanguages: Int
        get() = sharedPreferences.getInt("totalLanguages", 0)
        set(totalLanguages) = sharedPreferences.edit().putInt("totalLanguages", totalLanguages).apply()

    var isProgramsOnly: Boolean
        get() = sharedPreferences.getBoolean("programsOnly", false)
        set(programsOnly) = sharedPreferences.edit().putBoolean("programsOnly", programsOnly).apply()

    var showInviteDialog: Boolean
        get() = sharedPreferences.getBoolean("showInviteDialog", false)
        set(showInviteDialog) = sharedPreferences.edit().putBoolean("showInviteDialog", showInviteDialog).apply()

    fun setUnlockedByInviteIndex(mToBeUnlockedIndex: Int) {
        var programLanguage = programLanguage
        if (programLanguage == "c++") {
            programLanguage = "cpp"
        }
        val unlockedPrograms = unlockedByInviteIndex
        if (!unlockedPrograms.contains(mToBeUnlockedIndex.toString())) {
            unlockedPrograms.add(mToBeUnlockedIndex.toString())
            Log.d(TAG, "UnlockByInvite : $programLanguage : Adding $mToBeUnlockedIndex")
            Log.d(TAG, "UnlockByInvite : $programLanguage : Post addition $unlockedPrograms")
            sharedPreferences.edit().putStringSet(programLanguage + "_" + UNLOCK_BY_INVITE, unlockedPrograms).apply()
        }
    }

    val unlockedByInviteIndex: MutableSet<String>
        get() {
            var programLanguage = programLanguage
            if (programLanguage == "c++") {
                programLanguage = "cpp"
            }
            var unlockedPrograms = sharedPreferences.getStringSet(programLanguage + "_" + UNLOCK_BY_INVITE, null)
            if (unlockedPrograms == null) {
                unlockedPrograms = HashSet<String>()
            }
            Log.d(TAG, "UnlockByInvite : $programLanguage : $unlockedPrograms")
            return unlockedPrograms
        }

    fun isUnlockedByInvite(programIndex: Int): Boolean {
        Log.d(TAG, "UnlockByInvite : $programLanguage : Checking $programIndex")
        val isPresent = unlockedByInviteIndex.contains(programIndex.toString())
        Log.d(TAG, "UnlockByInvite : $programLanguage : Tag Present ? $isPresent")
        return isPresent
    }

    fun addUserFile(md5: String): Boolean {
        val creekUserStats = creekUserStats
        if (creekUserStats != null) {
            if (creekUserStats.addToUserAddedPrograms(md5)) {
                creekUserStats.calculateReputation()
                FirebaseDatabaseHandler(context).writeCreekUserStats(creekUserStats)
                return true
            } else {
                return false
            }
        } else {
            return true
        }

    }

    var isFavoritesStored: Boolean
        get() = sharedPreferences.getBoolean("isFavoritesStored", false)
        set(isFavoritesStored) = sharedPreferences.edit().putBoolean("isFavoritesStored", true).apply()

    var level: Int
        get() = sharedPreferences.getInt("previousLevel", 0)
        set(currentLevel) = sharedPreferences.edit().putInt("previousLevel", currentLevel).apply()

    var adsEnabled: Boolean
        get() {
            Log.d(TAG, "getAdsEnabled : " + sharedPreferences.getBoolean("adsEnabled", false))
            return sharedPreferences.getBoolean("adsEnabled", false)
        }
        set(adsEnabled) = sharedPreferences.edit().putBoolean("adsEnabled", adsEnabled).apply()

    fun showUpgradeDialog(): Boolean {
        return sharedPreferences.getBoolean("showUpgradeDialog", false)
    }

    fun setUpgradeDialog(showDialog: Boolean) {
        sharedPreferences.edit().putBoolean("showUpgradeDialog", showDialog).apply()
    }

    var userId: String
        get() {
            var userId: String = sharedPreferences.getString("userId", "")
            if (userId.equals("", ignoreCase = true)) {
                userId = FirebaseAuth.getInstance().currentUser!!.uid
                userId = userId
            }
            return userId
        }
        set(payload) = sharedPreferences.edit().putString("userId", payload).apply()

    var isPremiumUser: Boolean
        get() = sharedPreferences.getBoolean("isPremiumUser", false)
        set(isPremiumUser) = sharedPreferences.edit().putBoolean("isPremiumUser", isPremiumUser).apply()

    val updateDelay = 1000

    val tabWidth = 4

    fun doesRunOnChange(): Boolean {
        return runMode == RUN_AUTO
    }

    fun setUnlockedSubTopic(subTopicId: String) {
        var programLanguage = programLanguage
        if (programLanguage == "c++") {
            programLanguage = "cpp"
        }
        val unlockedPrograms = unlockedTopics
        if (!unlockedPrograms.contains(subTopicId)) {
            unlockedPrograms.add(subTopicId)
            sharedPreferences.edit().putStringSet(programLanguage + "_unlockedSubTopics", unlockedPrograms).apply()
        }
    }

    private val unlockedTopics: MutableSet<String>
        get() {
            var programLanguage = programLanguage
            if (programLanguage == "c++") {
                programLanguage = "cpp"
            }
            var unlockedPrograms = sharedPreferences.getStringSet(programLanguage + "_unlockedSubTopics", null)
            if (unlockedPrograms == null) {
                unlockedPrograms = HashSet<String>()
            }
            return unlockedPrograms
        }

    fun isUnlockedTopic(programIndex: String): Boolean {
        return unlockedTopics.contains(programIndex)
    }

    companion object {

        val SIGN_IN_ACCOUNT = "SIGN_IN_ACCOUNT"
        val ACCOUNT_NAME = "ACCOUNT_NAME"
        val ACCOUNT_PHOTO = "ACCOUNT_PHOTO"
        val KEY_PROG_TABLE_INSERT = "insertProgramTable"
        val KEY_PROG_INDEX_INSERT = "insertProgramIndex"
        val KEY_PROG_INDEX_INSERT_JAVA = "insertProgramIndexJava"
        val KEY_PROG_TABLE_INSERT_JAVA = "insertProgramTableJava"
        val KEY_PROG_INDEX_INSERT_CPP = "insertProgramIndexCpp"
        val KEY_PROG_TABLE_INSERT_CPP = "insertProgramTableCpp"
        val KEY_PROG_TABLE_INSERT_USP = "insertProgramTableUsp"
        val KEY_PROG_TABLE_INSERT_SQL = "insertProgramTableSQL"
        val KEY_PROG_INDEX_INSERT_USP = "insertProgramIndexUsp"
        val KEY_C_MODULE = "keyCModule"
        val KEY_CPP_MODULE = "keyCppModule"
        val KEY_SQL_MODULE = "keySqlModule"

        val KEY_JAVA_MODULE = "keyJavaModule"
        val KEY_C_SYNTAX = "keyCSyntax"
        val KEY_CPP_SYNTAX = "keyCppSyntax"
        val KEY_JAVA_SYNTAX = "keyJavaSyntax"
        val KEY_SQL_SYNTAX = "keySqlSyntax"

        val KEY_C_WIKI = "keyCWiki"
        val KEY_CPP_WIKI = "keyCppWiki"
        val KEY_USP_WIKI = "keyUspWiki"
        val KEY_SQL_WIKI = "keySqlWiki"
        val KEY_JAVA_WIKI = "keyJavaWiki"

        private val PROGRAM_LANGUAGE = "program_language"
        val KEY_ANON_ACCOUNT = "isAnonAccount"

        private val RUN_AUTO = 1
        private val RUN_MANUALLY = 2
        private val RUN_MANUALLY_EXTRA = 3
        private val RUN_MANUALLY_EXTRA_NEW = 4

        val KEY_C_INTRO = "keyCIntro"
        val KEY_CPP_INTRO = "keyCppIntro"
        val KEY_JAVA_INTRO = "keyJavaIntro"

        val UNLOCK_BY_INVITE = "unlock_by_invite"
    }

    var isAlgorithmsInserted: Boolean
        get() {
            return sharedPreferences.getBoolean("isAlgorithmsInserted", false)
        }
        set(isAlgorithmsInserted) = sharedPreferences.edit().putBoolean("isAlgorithmsInserted", isAlgorithmsInserted).apply()
}

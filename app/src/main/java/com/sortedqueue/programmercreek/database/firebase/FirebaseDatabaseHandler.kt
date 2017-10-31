package com.sortedqueue.programmercreek.database.firebase

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.util.Log

import com.anjlab.android.iab.v3.TransactionDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Query
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.billing.Purchase
import com.sortedqueue.programmercreek.database.Algorithm
import com.sortedqueue.programmercreek.database.AlgorithmsIndex
import com.sortedqueue.programmercreek.database.Chapter
import com.sortedqueue.programmercreek.database.CreekUser
import com.sortedqueue.programmercreek.database.CreekUserDB
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.database.IntroChapter
import com.sortedqueue.programmercreek.database.LanguageModule
import com.sortedqueue.programmercreek.database.PresentationModel
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.database.ProgramLanguage
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.database.SlideModel
import com.sortedqueue.programmercreek.database.SyntaxModule
import com.sortedqueue.programmercreek.database.TagModel
import com.sortedqueue.programmercreek.database.UserProgramDetails
import com.sortedqueue.programmercreek.database.UserRanking
import com.sortedqueue.programmercreek.database.WikiModel
import com.sortedqueue.programmercreek.util.AlphaNumComparator
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekPreferences

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.util.ArrayList
import java.util.HashMap

import co.uk.rushorm.core.RushCallback
import co.uk.rushorm.core.RushCore
import co.uk.rushorm.core.RushSearch

/**
 * Created by binay on 05/12/16.
 */

class FirebaseDatabaseHandler(private val mContext: Context) {

    private var mProgramDatabase: DatabaseReference? = null
    private var mUserProgramDatabase: DatabaseReference? = null
    private var mLanguageModuleDatabase: DatabaseReference? = null
    private var mSyntaxModuleDatabase: DatabaseReference? = null
    private var mProgramWikiDatabase: DatabaseReference? = null
    private var mChapterDatabase: DatabaseReference? = null
    private var mUserDatabase: DatabaseReference? = null
    private var mUserStatsDatabase: DatabaseReference? = null
    private var mUserDetailsDatabase: DatabaseReference? = null
    private var mIntroChapterDatabase: DatabaseReference? = null
    private var mProgramLanguageDatabase: DatabaseReference? = null
    private var mPresentationDatabase: DatabaseReference? = null
    private var mTagDatabase: DatabaseReference? = null
    private var mUserMessageTokenDatabase: DatabaseReference? = null

    private val PROGRAM_INDEX_CHILD = "program_indexes"
    private val PROGRAM_TABLE_CHILD = "program_tables"
    private val CREEK_USER_CHILD = "users"
    private val CREEK_USER_PROGRAM_DETAILS_CHILD = "user_program_details"
    private val LANGUAGE_MODULE = "language_module"
    private val SYNTAX_MODULE = "syntax_module"
    private val CREEK_BASE_FIREBASE_URL = "https://creek-55ef6.firebaseio.com/"
    private var programLanguage = "java"
    private val creekPreferences: CreekPreferences

    private val TAG = FirebaseDatabaseHandler::class.java.simpleName
    private var program_tables: ArrayList<ProgramTable>? = null
    private var mCreekUserDBDatabase: DatabaseReference? = null
    private val CREEK_INTRO_DB = "intro_db"
    private val CREEK_USER_DB = "creek_user_db_version"
    private val WIKI_MODULE = "wiki_module"
    private val CREEK_USER_STATS = "user_stats"
    private val CREEK_CHAPTERS = "creek_chapters"
    private val CREEK_PROGRAM_LANGUAGE = "program_language"
    private var programLanguages: ArrayList<ProgramLanguage>? = null
    private val ALGORITHM_INDEX = "algorithm_index"
    private val ALGORITHM = "algorithm"
    private val CREEK_PRESENTATIONS_SLIDES = "presentations_slides"
    private val CREEK_PRESENTATIONS = "presentations"
    private val TO_BE_APPROVED = "to_be_approved"
    private val CREEK_TAGS = "language_tags"

    private var mPresentationSlidesDatabase: DatabaseReference? = null

    /***
     * Program Index storage :
     * CREEK_BASE_FIREBASE_URL/programs/c/program_indexes

     * Program storage :
     * CREEK_BASE_FIREBASE_URL/programs/c/program_storage

     * User profile storage :
     * CREEK_BASE_FIREBASE_URL/users/email_id/

     * User program details :
     * CREEK_BASE_FIREBASE_URL/user_program_details/email_id/program_language
     */

    val programDatabase: DatabaseReference
        get() {
            mProgramDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/programs/" + programLanguage)
            mProgramDatabase!!.keepSynced(true)
            return mProgramDatabase!!
        }

    fun getmUserMessageTokenDatabase(): DatabaseReference {
        mUserMessageTokenDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/user_tokens")
        mUserMessageTokenDatabase!!.keepSynced(true)
        return mUserMessageTokenDatabase!!
    }

    val userProgramDatabase: DatabaseReference
        get() {
            mUserProgramDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/user_programs")
            mUserProgramDatabase!!.keepSynced(true)
            return mUserProgramDatabase!!
        }

    fun getmTagDatabase(): DatabaseReference {
        mTagDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + CREEK_TAGS)
        mTagDatabase!!.keepSynced(true)
        return mTagDatabase!!
    }

    val userDatabase: DatabaseReference
        get() {
            mUserDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + CREEK_USER_CHILD)
            mUserDatabase!!.keepSynced(true)
            return mUserDatabase!!
        }

    val userStatsDatabase: DatabaseReference
        get() {
            mUserStatsDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + CREEK_USER_STATS)
            mUserStatsDatabase!!.keepSynced(true)
            return mUserStatsDatabase!!
        }

    val userDetailsDatabase: DatabaseReference
        get() {
            mUserDetailsDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + CREEK_USER_PROGRAM_DETAILS_CHILD)
            mUserDetailsDatabase!!.keepSynced(true)
            return mUserDetailsDatabase!!
        }

    fun getmPresentationSlidesDatabase(): DatabaseReference {
        mPresentationSlidesDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + CREEK_PRESENTATIONS_SLIDES)
        mPresentationSlidesDatabase!!.keepSynced(true)
        return mPresentationSlidesDatabase!!
    }

    fun getmPresentationDatabase(): DatabaseReference {
        mPresentationDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + CREEK_PRESENTATIONS)
        mPresentationDatabase!!.keepSynced(true)
        return mPresentationDatabase!!
    }

    fun getCreekUserDBDatabase() {
        mCreekUserDBDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + CREEK_USER_DB)
        mCreekUserDBDatabase!!.keepSynced(true)
    }

    fun getIntroDB() {
        mIntroChapterDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("$CREEK_BASE_FIREBASE_URL/$CREEK_INTRO_DB/$programLanguage")
    }

    fun getProgramLanguageDB() {
        mProgramLanguageDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + CREEK_PROGRAM_LANGUAGE)
    }

    fun writeProgramLanguage(programLanguage: ProgramLanguage) {
        getProgramLanguageDB()
        mProgramLanguageDatabase!!.push().setValue(programLanguage)
    }

    fun searchPrograms(queryText: String, programIndexInterface: ProgramIndexInterface) {
        programDatabase
        mProgramDatabase!!.child(PROGRAM_INDEX_CHILD).orderByChild("program_Description")
                .startAt("*" + queryText)
                .endAt(queryText + "\uf8ff")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val programIndices = ArrayList<ProgramIndex>()
                        for (child in dataSnapshot.children) {
                            val programIndex = child.getValue(ProgramIndex::class.java)
                            if (programIndex != null) programIndices.add(programIndex)

                        }
                        programIndexInterface.getProgramIndexes(programIndices)

                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        programIndexInterface.onError(databaseError)
                    }
                })


    }

    fun writeTags(newTag: String) {
        getmTagDatabase().runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                if (mutableData.value == null) {
                    val tagModel = TagModel()
                    tagModel.tagArrayList.add(newTag)
                    mutableData.value = tagModel
                } else {
                    val tagModel = mutableData.getValue(TagModel::class.java)
                    val tags = tagModel.tagArrayList
                    if (!checkArrayList(tags, newTag)) {
                        tagModel.tagArrayList.add(newTag)
                        mutableData.value = tagModel
                    }

                }
                return Transaction.success(mutableData)
            }

            override fun onComplete(databaseError: DatabaseError, b: Boolean, dataSnapshot: DataSnapshot) {

            }
        })
    }

    fun writeCode(code: Code) {

    }

    fun updatePurchasePayload(purchase: Purchase) {
        var userId = creekPreferences.userId
        if (userId.equals("", ignoreCase = true)) {
            userId = FirebaseAuth.getInstance().currentUser!!.uid
        }
        Log.d(TAG, "Premium upgrade : UserId : $userId :Purchase Details: $purchase")
        FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/premium_users/" + userId).setValue(purchase)
    }

    fun updateAnonAccountStats(creekUser: CreekUser) {

        userStatsDatabase.child(creekUser.userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val creekUserStats = dataSnapshot.getValue(CreekUserStats::class.java)
                if (creekUserStats != null) {
                    creekPreferences.saveCreekUserStats(creekUserStats)
                    Log.d(TAG, "getCreekUserStatsInBackground : success : retrieved stats are : " + creekUserStats.toString())
                    if (creekUser.userId != null && !creekUser.userId.trim { it <= ' ' }.equals("", ignoreCase = true)) {
                        userStatsDatabase.child(creekUser.userId).removeValue()
                        userStatsDatabase.child(creekUser.emailId.replace("[-+.^:,]".toRegex(), "")).setValue(creekUserStats)
                    }

                } else {
                    creekPreferences.saveCreekUserStats(CreekUserStats())
                    Log.d(TAG, "getCreekUserStatsInBackground : Failed : creating new stats : " + CreekUserStats().toString())
                }


            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    fun updatePurchasePayload(details: TransactionDetails) {
        var userId = creekPreferences.userId
        if (userId.equals("", ignoreCase = true)) {
            userId = FirebaseAuth.getInstance().currentUser!!.uid
        }
        Log.d(TAG, "Premium upgrade : UserId : $userId :Purchase Details: $details")
        //TODO Change this later
        creekPreferences.isPremiumUser = true
        FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/premium_users/" + userId).setValue(details)
    }

    interface AnjVerifyPurchaseListener {
        fun onSuccess(purchase: com.sortedqueue.programmercreek.billing.anjlab.TransactionDetails)
        fun onError(e: Exception?)
    }

    fun verifyPurchase(verifyPurchaseListener: AnjVerifyPurchaseListener) {
        val userId = creekPreferences.userId
        verifyPurchase(userId, verifyPurchaseListener)
    }

    private fun verifyPurchase(userId: String, verifyPurchaseListener: AnjVerifyPurchaseListener) {
        FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/premium_users/" + userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot != null && dataSnapshot.value != null) {
                    val purchase = dataSnapshot.getValue(com.sortedqueue.programmercreek.billing.anjlab.TransactionDetails::class.java)
                    if (purchase != null) {
                        verifyPurchaseListener.onSuccess(purchase)
                        creekPreferences.isPremiumUser = true
                    } else {
                        verifyPurchaseListener.onError(null)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                verifyPurchaseListener.onError(databaseError.toException())
            }
        })
    }

    interface VerifyPurchaseListener {
        fun onSuccess(purchase: Purchase)
        fun onError(e: Exception?)
    }

    fun verifyPurchase(verifyPurchaseListener: VerifyPurchaseListener) {
        val userId = creekPreferences.userId
        verifyPurchase(userId, verifyPurchaseListener)
    }

    private fun verifyPurchase(userId: String, verifyPurchaseListener: VerifyPurchaseListener) {
        FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/premium_users/" + userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot != null) {
                    val purchase = dataSnapshot.getValue(Purchase::class.java)
                    if (purchase != null) {
                        verifyPurchaseListener.onSuccess(purchase)
                        creekPreferences.isPremiumUser = true
                    } else {
                        verifyPurchaseListener.onError(null)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                verifyPurchaseListener.onError(databaseError.toException())
            }
        })
    }

    private inner class UserToken(var token: String?, var userEmail: String?) {
        var pushKey: String? = null
    }

    fun updateMessageToken(refreshedToken: String) {
        getmUserMessageTokenDatabase()
        val userToken = UserToken(creekPreferences.getSignInAccount(), refreshedToken)
        val pushKey = mUserMessageTokenDatabase!!.push().key
        userToken.pushKey = pushKey
        mUserMessageTokenDatabase!!.child(pushKey).setValue(userToken)

    }

    interface GetAllUserProgramsListener {
        fun onSuccess(userProgramDetailsArrayList: ArrayList<UserProgramDetails>)
        fun onError(databaseError: DatabaseError?)
    }

    fun getAllUserPrograms(accessSpecifier: String, language: String, getAllUserProgramsListener: GetAllUserProgramsListener) {

        userProgramDatabase
        val query: Query?
        if (accessSpecifier == "My Programs") {
            query = mUserProgramDatabase!!.ref.orderByChild("emailId").equalTo(creekPreferences.getSignInAccount())
        } else if (accessSpecifier == "Favorites") {
            getAllFavoritePrograms(getAllUserProgramsListener)
            query = null
        } else {
            if (language.equals("All", ignoreCase = true)) {
                query = mUserProgramDatabase
            } else {
                query = mUserProgramDatabase!!.ref.orderByChild("programLanguage").equalTo(language)
            }
        }

        query?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userProgramDetailsArrayList = ArrayList<UserProgramDetails>()

                for (userProgramSnap in dataSnapshot.children) {
                    val userProgramDetails = userProgramSnap.getValue(UserProgramDetails::class.java)
                    if (userProgramDetails != null) {
                        if (userProgramDetails.programTables != null
                                && userProgramDetails.programTables.size > 0
                                && userProgramDetails.programTables[0].userProgramId == null) {
                            for (programTable in userProgramDetails.programTables) {
                                programTable.userProgramId = userProgramDetails.programId
                            }
                        }
                        userProgramDetailsArrayList.add(userProgramDetails)
                    }
                }
                if (userProgramDetailsArrayList.size > 0) {
                    if (!creekPreferences.isFavoritesStored) {
                        val userEmail = creekPreferences.getSignInAccount()
                        for (userProgramDetails in userProgramDetailsArrayList) {
                            if (userProgramDetails.isLiked(userEmail)) {
                                userProgramDetails.save()
                                userProgramDetails.programIndex.save()
                                RushCore.getInstance().save(userProgramDetails.programTables)
                            }
                        }
                        creekPreferences.isFavoritesStored = true
                    }
                    getAllUserProgramsListener.onSuccess(userProgramDetailsArrayList)
                } else {
                    getAllUserProgramsListener.onError(null)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                getAllUserProgramsListener.onError(databaseError)
            }
        })

    }

    private fun getAllFavoritePrograms(getAllUserProgramsListener: GetAllUserProgramsListener) {
        object : AsyncTask<Void, Void, ArrayList<UserProgramDetails>>() {

            override fun doInBackground(vararg voids: Void): ArrayList<UserProgramDetails> {

                val userProgramDetailsArrayList = ArrayList(RushSearch().find(UserProgramDetails::class.java))
                for (userProgramDetails in userProgramDetailsArrayList) {
                    userProgramDetails.programIndex = RushSearch().whereEqual("userProgramId", userProgramDetails.programId).findSingle(ProgramIndex::class.java)
                    userProgramDetails.programTables = ArrayList(RushSearch().orderAsc("line_No").whereEqual("userProgramId", userProgramDetails.programId).find(ProgramTable::class.java))
                }

                return userProgramDetailsArrayList
            }

            override fun onPostExecute(userProgramDetailsArrayList: ArrayList<UserProgramDetails>?) {
                super.onPostExecute(userProgramDetailsArrayList)
                if (userProgramDetailsArrayList != null && userProgramDetailsArrayList.size > 0) {
                    getAllUserProgramsListener.onSuccess(userProgramDetailsArrayList)
                } else {
                    getAllUserProgramsListener.onError(null)
                }

            }
        }.execute()
    }

    interface ConfirmUserProgram {
        fun onSuccess(programIndex: ProgramIndex, programTables: ArrayList<ProgramTable>)
        fun onError(errorMessage: String)
    }

    fun compileSharedProgram(programText: String, confirmUserProgram: ConfirmUserProgram) {
        object : AsyncTask<Void, Void, String>() {

            private var programIndex: ProgramIndex? = null
            private var programTables: ArrayList<ProgramTable>? = null

            override fun doInBackground(vararg params: Void): String? {

                programIndex = ProgramIndex()
                programIndex!!.program_Description = ""
                programIndex!!.program_Language = ""

                val programLines = AuxilaryUtils.splitProgramIntolines(programText)
                val programExplanations = AuxilaryUtils.mapCodeToComments(mContext, programText)

                val intProgramIndex = programIndex!!.program_index
                programTables = ArrayList<ProgramTable>()
                var index = 1
                var i = 0
                while (i < programLines.size) {

                    var programLine = programLines[i]
                    var programExplanation = programExplanations[i]
                    if (programLine.contains("//")) {
                        if (programLine.trim { it <= ' ' }.startsWith("//")) {
                            //Do nothing
                        } else {
                            programLine = programLine.trim { it <= ' ' }.split("//".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                            programExplanation = programExplanation.trim { it <= ' ' }.split("//".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                            if (programLine.trim { it <= ' ' }.length > 0) {
                                programTables!!.add(
                                        ProgramTable(
                                                intProgramIndex,
                                                index++,
                                                programLanguage,
                                                programLine,
                                                programExplanation))
                            }
                        }
                    } else if (programLine.trim { it <= ' ' }.startsWith("/*") && programLine.trim { it <= ' ' }.endsWith("*/")) {
                        //Do nothing
                    } else if (programLine.trim { it <= ' ' }.startsWith("/*")) {

                        while (i < programLines.size && (!programLine.trim { it <= ' ' }.endsWith("*/") || !programLine.trim { it <= ' ' }.contains("*/"))) {
                            i++
                            programLine = programLines[i]
                        }

                    } else {
                        if (programLine.trim { it <= ' ' }.length > 0) {
                            programTables!!.add(
                                    ProgramTable(
                                            intProgramIndex,
                                            index++,
                                            programLanguage,
                                            programLine,
                                            programExplanation))
                        }
                    }
                    i++

                }

                return null
            }

            override fun onPreExecute() {
                super.onPreExecute()
                CommonUtils.displayProgressDialog(mContext, mContext.getString(R.string.loading_program))
            }

            override fun onPostExecute(aVoid: String?) {
                super.onPostExecute(aVoid)
                CommonUtils.dismissProgressDialog()
                if (aVoid == null) {
                    confirmUserProgram.onSuccess(programIndex!!, programTables!!)
                } else {
                    confirmUserProgram.onError(aVoid)
                }

            }
        }.execute()
    }

    fun readProgramFromFile(filepath: String, confirmUserProgram: ConfirmUserProgram) {

        object : AsyncTask<Void, Void, String>() {

            private var programIndex: ProgramIndex? = null
            private var programTables: ArrayList<ProgramTable>? = null

            override fun doInBackground(vararg params: Void): String? {
                var fis: InputStream? = null
                try {
                    fis = FileInputStream(filepath)
                    val isr = InputStreamReader(fis, Charset.forName("UTF-8"))
                    val br = BufferedReader(isr)
                    var line: String
                    var programTitle = ""
                    var programLanguage = ""
                    var programExplanation = ""
                    var program = ""
                    line = br.readLine()
                    while ((line) != null) {
                        if (line.trim { it <= ' ' }.length == 0) {
                            //blank line
                            continue
                        }

                        if (line.startsWith(START_PROGRAM_TITLE) && programTitle == "") {
                            line = br.readLine()

                            while (true) {
                                if (!line.startsWith(END_PROGRAM_TITLE))
                                    programTitle += line
                                else
                                    break
                                line = br.readLine()

                            }

                        }
                        if (line.startsWith(START_PROGRAM_LANGUAGE) && programLanguage == "") {
                            line = br.readLine()

                            while (true) {
                                if (!line.startsWith(END_PROGRAM_LANGUAGE))
                                    programLanguage += line
                                else
                                    break
                                line = br.readLine()

                            }
                        }
                        if (line.startsWith(START_PROGRAM_EXPLANATION) && programExplanation == "") {
                            line = br.readLine()

                            while (true) {
                                if (!line.startsWith(END_PROGRAM_EXPLANATION))
                                    programExplanation += line + "\n"
                                else
                                    break
                                line = br.readLine()

                            }

                        }
                        if (line.startsWith(START_PROGRAM) && program == "") {
                            line = br.readLine()

                            while (true) {
                                if (!line.startsWith(END_PROGRAM))
                                    program += line + "\n"
                                else
                                    break
                                line = br.readLine()

                            }

                        }
                        line = br.readLine()
                    }
                    if (programTitle.trim { it <= ' ' }.length == 0) {
                        return "Missing program title"
                    }
                    if (programLanguage.trim { it <= ' ' }.length == 0) {
                        return "Missing program language"
                    }
                    if (program.trim { it <= ' ' }.length == 0) {
                        return "Missing program code"
                    }
                    if (programExplanation.trim { it <= ' ' }.length == 0) {
                        return "Missing program explanation"
                    }
                    programIndex = ProgramIndex()
                    programIndex!!.program_Description = programTitle
                    programIndex!!.program_index = programTitle.hashCode()
                    programIndex!!.program_Language = programLanguage

                    val programLines = AuxilaryUtils.splitProgramIntolines(program)
                    val programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation)

                    if (programLines != null && programExplanations != null) {
                        if (programLines.size > programExplanations.size) {
                            return "Explanation needed for each line of code"
                        }
                        if (programLines.size < programExplanations.size) {
                            return "Code needed for each line of explanation"
                        }
                    }

                    val intProgramIndex = programIndex!!.program_index
                    programTables = ArrayList<ProgramTable>()
                    for (i in programLines.indices) {
                        programTables!!.add(
                                ProgramTable(
                                        intProgramIndex,
                                        i + 1,
                                        programLanguage,
                                        programLines[i],
                                        programExplanations[i]))
                    }
                } catch (e: java.io.IOException) {
                    e.printStackTrace()
                    return e.message
                }

                return null
            }

            override fun onPreExecute() {
                super.onPreExecute()
                CommonUtils.displayProgressDialog(mContext, mContext.getString(R.string.loading_program))
            }

            override fun onPostExecute(aVoid: String?) {
                super.onPostExecute(aVoid)
                CommonUtils.dismissProgressDialog()
                if (aVoid == null) {
                    confirmUserProgram.onSuccess(programIndex!!, programTables!!)
                } else {
                    confirmUserProgram.onError(aVoid)
                }

            }
        }.execute()


    }

    interface GetAllTagsListener {
        fun onError(databaseError: DatabaseError)
        fun onSuccess(tagModel: TagModel)
    }

    fun getAllTags(getAllTagsListener: GetAllTagsListener) {
        getmTagDatabase().addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var tagModel: TagModel? = dataSnapshot.getValue(TagModel::class.java)
                if (tagModel == null) {
                    tagModel = TagModel()
                    val tags = ArrayList<String>()
                    tags.add("C")
                    tags.add("C++")
                    tags.add("Java")
                    tags.add("SQL")
                    tagModel.tagArrayList = tags
                }
                getAllTagsListener.onSuccess(tagModel)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                databaseError.toException().printStackTrace()
                getAllTagsListener.onError(databaseError)
            }
        })
    }

    fun updateCodeCount() {
        FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/program_count")
                .runTransaction(object : Transaction.Handler {
                    override fun doTransaction(currentData: MutableData): Transaction.Result {
                        if (currentData.value == null) {
                            currentData.value = 1
                        } else {
                            currentData.value = currentData.value as Long + 1
                        }
                        return Transaction.success(currentData)
                    }

                    override fun onComplete(databaseError: DatabaseError, b: Boolean, dataSnapshot: DataSnapshot) {

                    }
                })

    }

    fun updateAdSettings(isAdEnabled: Int) {
        FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/ad_settings").setValue(isAdEnabled)
    }

    fun getAdSettings() {
        FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/ad_settings").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot != null) {
                    val isAdEnabled = dataSnapshot.getValue(Int::class.java)
                    Log.d(TAG, "isAdEnabled : " + isAdEnabled)
                    CreekApplication.creekPreferences!!.adsEnabled = isAdEnabled == 1
                    CreekApplication.instance.initAdsSdk()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }


    fun updateInviteCount(inviteCount: Int) {
        FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/invite_count")
                .runTransaction(object : Transaction.Handler {
                    override fun doTransaction(currentData: MutableData): Transaction.Result {
                        if (currentData.value == null) {
                            currentData.value = 1
                        } else {
                            currentData.value = currentData.value as Long + 1
                        }
                        return Transaction.success(currentData)
                    }

                    override fun onComplete(databaseError: DatabaseError, b: Boolean, dataSnapshot: DataSnapshot) {

                    }
                })
        FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/total_invites")
                .runTransaction(object : Transaction.Handler {
                    override fun doTransaction(currentData: MutableData): Transaction.Result {
                        if (currentData.value == null) {
                            currentData.value = inviteCount
                        } else {
                            currentData.value = currentData.value as Long + inviteCount
                        }
                        return Transaction.success(currentData)
                    }

                    override fun onComplete(databaseError: DatabaseError, b: Boolean, dataSnapshot: DataSnapshot) {

                    }
                })
    }

    private var mAlgorithmIndexReference: DatabaseReference? = null
    fun writeAlgorithmIndex(algorithmsIndex: AlgorithmsIndex) {
        /* mAlgorithmIndexReference = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + ALGORITHM_INDEX);
        mAlgorithmIndexReference.push().setValue(algorithmsIndex);*/

    }

    private var mAlgorithmReference: DatabaseReference? = null
    fun writeAlgorithm(algorithm: Algorithm) {

        mAlgorithmReference = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + ALGORITHM)
        mAlgorithmReference!!.child(ALGORITHM + "_" + algorithm.algorithmsIndex.programIndex).setValue(algorithm)
    }

    var presentationPushId: String? = null

    fun writeSlide(slideModel: SlideModel): String {
        getmPresentationSlidesDatabase()
        if (presentationPushId == null) {
            presentationPushId = mPresentationSlidesDatabase!!.child(creekPreferences.getSignInAccount().replace("[-+.^:,]".toRegex(), "")).push().key
        }
        mPresentationSlidesDatabase!!.child(creekPreferences.getSignInAccount().replace("[-+.^:,]".toRegex(), "") + "/" + presentationPushId).push().setValue(slideModel)
        return presentationPushId!!
    }

    fun writeNewPresentation(presentationModel: PresentationModel) {
        getmPresentationDatabase()
        mPresentationDatabase!!.child(TO_BE_APPROVED).push().setValue(presentationModel)
    }

    interface GetAllPresentationsListener {
        fun onSuccess(presentationModelArrayList: ArrayList<PresentationModel>)
        fun onError(databaseError: DatabaseError?)
    }

    fun getAllPresentations(getAllPresentationsListener: GetAllPresentationsListener) {
        getmPresentationDatabase()
        mPresentationDatabase!!.child(TO_BE_APPROVED).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val presentationModels = ArrayList<PresentationModel>()
                for (childSnapShot in dataSnapshot.children) {
                    val presentationModel = childSnapShot.getValue(PresentationModel::class.java)
                    if (presentationModel != null) {
                        presentationModels.add(presentationModel)
                    }
                }
                if (presentationModels.size == 0) {
                    getAllPresentationsListener.onError(null)
                } else {
                    getAllPresentationsListener.onSuccess(presentationModels)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                getAllPresentationsListener.onError(databaseError)
            }
        })
    }

    interface GetAllSlidesListener {
        fun onSuccess(slideModelArrayList: ArrayList<SlideModel>)
        fun onFailure(databaseError: DatabaseError?)
    }

    fun getAllSlidesListener(presenterId: String, presentationPushId: String, getAllSlidesListener: GetAllSlidesListener) {
        getmPresentationSlidesDatabase()
        mPresentationSlidesDatabase!!.child(presenterId.replace("[-+.^:,]".toRegex(), "")).child(presentationPushId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val slideModelArrayList = ArrayList<SlideModel>()

                for (child in dataSnapshot.children) {
                    val slideModel = child.getValue(SlideModel::class.java)
                    if (slideModel != null) {
                        slideModelArrayList.add(slideModel)
                    }
                }
                if (slideModelArrayList.size > 0)
                    getAllSlidesListener.onSuccess(slideModelArrayList)
                else
                    getAllSlidesListener.onFailure(null)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                getAllSlidesListener.onFailure(databaseError)
            }
        })
    }

    interface GetAllAlgorithmsListener {
        fun onSuccess(algorithmsIndexArrayList: ArrayList<AlgorithmsIndex>)
        fun onError(databaseError: DatabaseError?)
    }

    fun getAllAlgorithmIndex(getAllAlgorithmsListener: GetAllAlgorithmsListener) {
        //CommonUtils.displayProgressDialog(mContext, mContext.getString(R.string.loading));
        mAlgorithmIndexReference = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + ALGORITHM_INDEX)
        mAlgorithmIndexReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val algorithmsIndices = ArrayList<AlgorithmsIndex>()
                for (snapshot in dataSnapshot.children) {
                    val algorithmsIndex = snapshot.getValue(AlgorithmsIndex::class.java)
                    algorithmsIndices.add(algorithmsIndex)
                }
                if (algorithmsIndices.size == 0) {
                    getAllAlgorithmsListener.onError(null)
                } else {
                    getAllAlgorithmsListener.onSuccess(algorithmsIndices)
                }
                CommonUtils.dismissProgressDialog()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                getAllAlgorithmsListener.onError(databaseError)
                CommonUtils.dismissProgressDialog()
            }
        })
    }

    interface GetAlgorithmListener {
        fun onSuccess(algorithm: Algorithm)
        fun onError(databaseError: DatabaseError?)
    }

    fun getAlgorithmForIndex(algorithmIndex: Int, getAlgorithmListener: GetAlgorithmListener) {
        CommonUtils.displayProgressDialog(mContext, mContext.getString(R.string.loading))
        mAlgorithmReference = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + ALGORITHM)
        mAlgorithmReference!!.child(ALGORITHM + "_" + algorithmIndex).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val algorithm = dataSnapshot.getValue(Algorithm::class.java)
                if (algorithm != null) {
                    getAlgorithmListener.onSuccess(algorithm)
                } else {
                    getAlgorithmListener.onError(null)
                }
                CommonUtils.dismissProgressDialog()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                getAlgorithmListener.onError(databaseError)
                CommonUtils.dismissProgressDialog()
            }
        })
    }

    interface GetProgramLanguageListener {
        fun onSuccess(programLanguages: ArrayList<ProgramLanguage>)
        fun onError(databaseError: DatabaseError)
    }

    @SuppressLint("StaticFieldLeak")
    fun getAllProgramLanguages(getProgramLanguageListener: GetProgramLanguageListener) {
        val creekUserDB = creekPreferences.creekUserDB
        val totalLocalLanguages = creekPreferences.totalLanguages
        if (totalLocalLanguages == 0 || creekUserDB != null && totalLocalLanguages < creekUserDB.totalLanguages) {
            getProgramLanguageDB()
            mProgramLanguageDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    programLanguages = ArrayList<ProgramLanguage>()

                    object : AsyncTask<Void, Void, Void?>() {

                        override fun doInBackground(vararg voids: Void): Void? {
                            RushCore.getInstance().deleteAll(ProgramLanguage::class.java)
                            return null
                        }

                        override fun onPostExecute(aVoid: Void?) {
                            super.onPostExecute(aVoid)
                            for (child in dataSnapshot.children) {
                                val programLanguage = child.getValue(ProgramLanguage::class.java)
                                programLanguages!!.add(programLanguage)
                                programLanguage.save { }
                            }

                            creekPreferences.totalLanguages = programLanguages!!.size
                            getProgramLanguageListener.onSuccess(programLanguages!!)
                        }
                    }.execute()


                }

                override fun onCancelled(databaseError: DatabaseError) {
                    getProgramLanguageListener.onError(databaseError)

                }
            })
        } else {
            object : AsyncTask<Void, Void, ArrayList<ProgramLanguage>>() {

                override fun doInBackground(vararg params: Void): ArrayList<ProgramLanguage> {

                    return ArrayList(RushSearch().find(ProgramLanguage::class.java))
                }

                override fun onPostExecute(programLanguages: ArrayList<ProgramLanguage>) {
                    super.onPostExecute(programLanguages)
                    getProgramLanguageListener.onSuccess(programLanguages)
                }
            }.execute()

        }

    }


    init {
        creekPreferences = CreekApplication.creekPreferences!!
        programLanguage = creekPreferences.programLanguage
        if (programLanguage == "c++") {
            programLanguage = "cpp"
        }
    }

    private fun getChaptersDatabase() {
        mChapterDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("$CREEK_BASE_FIREBASE_URL/$CREEK_CHAPTERS/$programLanguage")
        mChapterDatabase!!.keepSynced(true)
    }

    private fun getSyntaxModuleDatabase() {
        if (mSyntaxModuleDatabase == null) {
            mSyntaxModuleDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("$CREEK_BASE_FIREBASE_URL/$SYNTAX_MODULE/$programLanguage")
            mSyntaxModuleDatabase!!.keepSynced(true)
        }
    }

    private fun getProgramWikiDatabase() {
        if (mProgramWikiDatabase == null) {
            mProgramWikiDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("$CREEK_BASE_FIREBASE_URL/$WIKI_MODULE/$programLanguage")
            mProgramWikiDatabase!!.keepSynced(true)
        }
    }


    private fun getLanguageModuleDatabase() {
        if (mLanguageModuleDatabase == null) {
            mLanguageModuleDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("$CREEK_BASE_FIREBASE_URL/$LANGUAGE_MODULE/$programLanguage")
            mLanguageModuleDatabase!!.keepSynced(true)
        }
    }

    fun writeProgramWiki(wikiModel: WikiModel) {
        getProgramWikiDatabase()
        mProgramWikiDatabase!!.child(wikiModel.wikiId).setValue(wikiModel)
        wikiModel.save { }
    }

    fun writeChapter(chapter: Chapter) {
        getChaptersDatabase()
        mChapterDatabase!!.push().child(chapter.chapterId).setValue(chapter)
        chapter.save { Log.d(TAG, "Rush ORM : saved successfully " + chapter.toString()) }
    }

    fun writeSyntaxModule(syntaxModule: SyntaxModule) {
        getSyntaxModuleDatabase()
        mSyntaxModuleDatabase!!.child(programLanguage + "_" + syntaxModule.moduleId + "_" + syntaxModule.syntaxModuleId).setValue(syntaxModule)
        syntaxModule.save { Log.d(TAG, "Rush ORM : saved successfully " + syntaxModule.toString()) }
    }

    fun writeLanguageModule(languageModule: LanguageModule) {
        getLanguageModuleDatabase()
        mLanguageModuleDatabase!!.child(languageModule.moduleLanguage + "_" + languageModule.moduleId).setValue(languageModule)
        languageModule.save { Log.d(TAG, "Rush ORM : saved successfully " + languageModule.toString()) }
    }

    fun writeProgramIndex(program_index: ProgramIndex) {
        programDatabase
        mProgramDatabase!!.child(PROGRAM_INDEX_CHILD + "/" + program_index.program_index).setValue(program_index)
    }

    fun writeProgramTable(program_table: ProgramTable) {
        programDatabase
        mProgramDatabase!!.child(PROGRAM_TABLE_CHILD + "/" + program_table.program_index + "/" + program_table.line_No).setValue(program_table)
    }

    fun writeCreekUser(creekUser: CreekUser) {
        userDatabase
        mUserDatabase!!.child(creekUser.emailId.replace("[-+.^:,]".toRegex(), "")).setValue(creekUser)
    }

    fun clearAllTables() {
        RushCore.getInstance().clearDatabase()
    }

    interface GetCreekUserListner {
        fun onSuccess(creekUser: CreekUser)
        fun onFailure(databaseError: DatabaseError?)
    }

    fun getCreekUser(emailId: String, getCreekUserListner: GetCreekUserListner) {
        userDatabase
        mUserDatabase!!.child(emailId.replace("[-+.^:,]".toRegex(), "")).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val creekUser = dataSnapshot.getValue(CreekUser::class.java)
                if (creekUser != null) {
                    creekPreferences.setSignInAccount(creekUser.emailId)
                    creekPreferences.setAccountName(creekUser.userFullName)
                    creekPreferences.setAccountPhoto(creekUser.userPhotoUrl)
                    if (creekUser.userId.equals("", ignoreCase = true)) {
                        creekUser.userId = creekPreferences.userId
                        creekUser.wasAnonUser = if (creekPreferences.isAnonAccount) "Yes" else "No"
                        creekUser.save(mContext)
                    }
                    if (creekUser.programLanguage != null)
                        creekPreferences.programLanguage = creekUser.programLanguage.toLowerCase()
                    getCreekUserListner.onSuccess(creekUser)
                } else {
                    getCreekUserListner.onFailure(null)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                getCreekUserListner.onFailure(databaseError)
            }
        })

    }

    fun writeCreekUserDB(creekUserDB: CreekUserDB) {
        getCreekUserDBDatabase()
        mCreekUserDBDatabase!!.setValue(creekUserDB)
    }

    fun getLatestCModules() {

    }

    interface GetIntroChaptersListener {
        fun onSuccess(introChapters: ArrayList<IntroChapter>)
        fun onError(error: DatabaseError)
    }

    fun getIntroChapters(getIntroChaptersListener: GetIntroChaptersListener) {
        getIntroDB()
        CommonUtils.displayProgressDialog(mContext, "Fetching chapters")
        if (!creekPreferences.introChapters) {
            mIntroChapterDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val introChapters = ArrayList<IntroChapter>()
                    for (keySnapShot in dataSnapshot.children) {
                        introChapters.add(keySnapShot.getValue(IntroChapter::class.java))
                    }
                    getIntroChaptersListener.onSuccess(introChapters)
                    RushCore.getInstance().save(introChapters) { Log.d(TAG, "getIntroChapters : Saved to local : " + programLanguage + " : " + introChapters.toString()) }
                    creekPreferences.introChapters = true
                    CommonUtils.dismissProgressDialog()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    getIntroChaptersListener.onError(databaseError)
                    CommonUtils.dismissProgressDialog()
                }
            })
        } else {
            object : AsyncTask<Void, Void, ArrayList<IntroChapter>>() {

                override fun doInBackground(vararg voids: Void): ArrayList<IntroChapter> {
                    if (programLanguage == "c++" || programLanguage == "cpp") {
                        return ArrayList(RushSearch()
                                .startGroup()
                                .whereEqual("chapterLanguage", "cpp")
                                .or()
                                .whereEqual("chapterLanguage", "c++")
                                .endGroup()
                                .orderAsc("chapterIndex")
                                .find(IntroChapter::class.java))
                    } else {
                        return ArrayList(RushSearch()
                                .whereEqual("chapterLanguage", programLanguage)
                                .orderAsc("chapterIndex")
                                .find(IntroChapter::class.java))
                    }

                }

                override fun onPostExecute(introChapters: ArrayList<IntroChapter>) {
                    super.onPostExecute(introChapters)
                    getIntroChaptersListener.onSuccess(introChapters)
                    CommonUtils.dismissProgressDialog()
                }
            }.execute()
        }

    }

    fun writeIntroChapter(chapter: IntroChapter) {
        getIntroDB()
        mIntroChapterDatabase!!.push().setValue(chapter)
    }

    interface GetChapterListener {
        fun onSuccess(chaptersList: ArrayList<Chapter>)
        fun onErrror(error: DatabaseError?)
    }

    fun getChaptersInBackground(getChapterListener: GetChapterListener) {
        getChaptersDatabase()
        if (AuxilaryUtils.isNetworkAvailable) {
            mChapterDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val chapterArrayList = ArrayList<Chapter>()
                    for (keyValue in dataSnapshot.children) {
                        for (chapterIdValue in keyValue.children) {
                            val chapter = chapterIdValue.getValue(Chapter::class.java)
                            if (chapter != null) {
                                chapterArrayList.add(chapter)
                            }
                        }
                    }
                    updateChaptersList(chapterArrayList)
                    getChapterListener.onSuccess(chapterArrayList)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    getChapterListener.onErrror(databaseError)
                }
            })
        } else {
            object : AsyncTask<Void, Void, ArrayList<Chapter>>() {

                override fun doInBackground(vararg voids: Void): ArrayList<Chapter> {
                    return offlineChapters
                }

                override fun onPostExecute(chapters: ArrayList<Chapter>) {
                    super.onPostExecute(chapters)
                    if (chapters.size > 0)
                        getChapterListener.onSuccess(chapters)
                    else
                        getChapterListener.onErrror(null)
                }
            }.execute()
        }

    }

    private val offlineChapters: ArrayList<Chapter>
        get() = ArrayList(
                RushSearch()
                        .whereEqual("program_Language", programLanguage)
                        .orderAsc("chapterId")
                        .find(Chapter::class.java))

    private fun updateChaptersList(chapterArrayList: ArrayList<Chapter>) {
        RushCore.getInstance().deleteAll(Chapter::class.java) { RushCore.getInstance().save(chapterArrayList) { } }
    }

    interface GetProgramTablesListener {
        fun onSuccess(programTables: ArrayList<ProgramTable>)
        fun onError(databaseError: DatabaseError?)
    }

    fun getProgramTablesInBackground(mProgramIndex: Int, getProgramTablesListener: GetProgramTablesListener) {
        if (creekPreferences.getProgramTables() != -1) {
            object : AsyncTask<Void, Void, ArrayList<ProgramTable>>() {

                override fun doInBackground(vararg params: Void): ArrayList<ProgramTable> {
                    return getProgramTables(mProgramIndex)
                }

                override fun onPostExecute(programTables: ArrayList<ProgramTable>) {
                    super.onPostExecute(programTables)
                    getProgramTablesListener.onSuccess(programTables)

                }
            }.execute()
        } else {
            programDatabase
            mProgramDatabase!!.child("program_tables/" + mProgramIndex.toString())
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val programTables = ArrayList<ProgramTable>()
                            for (indexSnapShot in dataSnapshot.children) {
                                val programTable = indexSnapShot.getValue(ProgramTable::class.java)
                                if (programTable != null) {
                                    programTables.add(programTable)
                                }
                            }
                            if (programTables.size > 0)
                                getProgramTablesListener.onSuccess(programTables)
                            else
                                getProgramTablesListener.onError(null)
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            getProgramTablesListener.onError(databaseError)
                        }
                    })
        }


    }

    fun getAllProgramTables() {
        programDatabase
        mProgramDatabase!!.child("program_tables")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val programTables = ArrayList<ProgramTable>()
                        for (indexSnapShot in dataSnapshot.children) {
                            Log.d(TAG, "Program_Index : " + indexSnapShot.key)
                            var programCode = ""
                            for (childSnapShot in indexSnapShot.children) {
                                val programTable = childSnapShot.getValue(ProgramTable::class.java)
                                if (programTable != null) {
                                    programTables.add(programTable)
                                    programCode += programTable.program_Line + "\n"
                                }
                            }
                            Log.d(TAG, programCode)
                        }


                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })
    }

    fun getProgramTables(mProgramIndex: Int): ArrayList<ProgramTable> {
        if (programLanguage == "c++" || programLanguage == "cpp") {
            return ArrayList(RushSearch()
                    .whereEqual("program_index", mProgramIndex)
                    .and()
                    .startGroup()
                    .whereEqual("program_Language", "c++")
                    .or()
                    .whereEqual("program_Language", "cpp")
                    .endGroup()
                    .orderAsc("line_No")
                    .find(ProgramTable::class.java))
        } else {
            return ArrayList(RushSearch()
                    .whereEqual("program_Language", programLanguage)
                    .and()
                    .whereEqual("program_index", mProgramIndex)
                    .orderAsc("line_No")
                    .find(ProgramTable::class.java))
        }

    }

    interface GetProgramIndexListener {
        fun onSuccess(programIndex: ProgramIndex)
        fun onError(databaseError: DatabaseError)
    }

    fun getProgramIndexInBackGround(mProgramIndex: Int, getProgramIndexListener: GetProgramIndexListener) {

        if (mProgramIndex <= creekPreferences.getProgramIndex()) {
            object : AsyncTask<Void, Void, ProgramIndex>() {

                override fun doInBackground(vararg voids: Void): ProgramIndex {
                    if (programLanguage == "c++" || programLanguage == "cpp") {
                        return RushSearch()
                                .startGroup()
                                .whereEqual("program_Language", "c++")
                                .or()
                                .whereEqual("program_Language", "cpp")
                                .endGroup()
                                .and()
                                .whereEqual("program_index", mProgramIndex)
                                .findSingle(ProgramIndex::class.java)
                    } else {
                        return RushSearch()
                                .whereEqual("program_Language", programLanguage)
                                .and()
                                .whereEqual("program_index", mProgramIndex)
                                .findSingle(ProgramIndex::class.java)
                    }
                }

                override fun onPostExecute(programIndex: ProgramIndex) {
                    super.onPostExecute(programIndex)
                    getProgramIndexListener.onSuccess(programIndex)
                }
            }.execute()
        } else {
            programDatabase
            mProgramDatabase!!.child(PROGRAM_INDEX_CHILD).child(mProgramIndex.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val programIndex = dataSnapshot.getValue(ProgramIndex::class.java)
                    getProgramIndexListener.onSuccess(programIndex)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(TAG, "getProgramIndexInBackGround : " + databaseError.toException().message)
                    databaseError.toException().printStackTrace()
                    getProgramIndexListener.onError(databaseError)
                }
            })
        }


    }

    interface SyntaxModuleInterface {
        fun onSuccess(syntaxModule: SyntaxModule)
        fun onError(error: DatabaseError?)
    }

    fun getSyntaxModule(syntaxId: String, wizardUrl: String, syntaxModuleInterface: SyntaxModuleInterface) {
        Log.d(TAG, "getSyntaxModule : Syntax module comparison : " + (syntaxId + "_" + wizardUrl + " : " + creekPreferences.syntaxInserted))
        Log.d(TAG, "getSyntaxModule : Syntax module comparison : " + AlphaNumComparator().compare(syntaxId + "_" + wizardUrl, creekPreferences.syntaxInserted))
        if (AlphaNumComparator().compare(syntaxId + "_" + wizardUrl, creekPreferences.syntaxInserted) <= 0) {
            Log.d(TAG, "getSyntaxModule : Running Async task")
            object : AsyncTask<Void, Void, SyntaxModule>() {

                override fun doInBackground(vararg voids: Void): SyntaxModule {
                    if (programLanguage == "c++" || programLanguage == "cpp") {
                        return RushSearch()
                                .whereEqual("syntaxLanguage", "c++")
                                .or()
                                .whereEqual("syntaxLanguage", "cpp")
                                .and()
                                .whereEqual("syntaxModuleId", wizardUrl)
                                .and()
                                .whereEqual("moduleId", syntaxId)
                                .findSingle(SyntaxModule::class.java)
                    } else {
                        return RushSearch()
                                .whereEqual("syntaxLanguage", programLanguage)
                                .and()
                                .whereEqual("syntaxModuleId", wizardUrl)
                                .and()
                                .whereEqual("moduleId", syntaxId)
                                .findSingle(SyntaxModule::class.java)
                    }
                }

                override fun onPostExecute(syntaxModule: SyntaxModule) {
                    super.onPostExecute(syntaxModule)
                    syntaxModuleInterface.onSuccess(syntaxModule)
                }
            }.execute()
        } else {
            Log.d(TAG, "getSyntaxModule : Running Firebase task")
            getSyntaxModuleDatabase()
            mSyntaxModuleDatabase!!.child(programLanguage + "_" + syntaxId + "_" + wizardUrl).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val syntaxModule = dataSnapshot.getValue(SyntaxModule::class.java)
                    if (syntaxModule == null) {
                        syntaxModuleInterface.onError(null)
                    } else
                        syntaxModuleInterface.onSuccess(syntaxModule)

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(TAG, "getSyntaxModule : " + databaseError.toException().message)
                    databaseError.toException().printStackTrace()
                    syntaxModuleInterface.onError(databaseError)
                }
            })
        }


    }

    interface GetWikiModelListener {
        fun onSuccess(wikiModel: WikiModel)
        fun onError(databaseError: DatabaseError)
    }

    fun getWikiModel(wizardUrl: String, getWikiModelListener: GetWikiModelListener) {
        Log.d(TAG, "getWikiModel : Wiki module comparison : " + (wizardUrl + " : " + creekPreferences.programWikiInserted))
        Log.d(TAG, "getWikiModel : Wiki module comparison : " + AlphaNumComparator().compare(wizardUrl, creekPreferences.programWikiInserted))
        if (AlphaNumComparator().compare(wizardUrl, creekPreferences.programWikiInserted) <= 0) {
            object : AsyncTask<Void, Void, WikiModel>() {

                override fun doInBackground(vararg voids: Void): WikiModel {
                    if (programLanguage == "c++" || programLanguage == "cpp") {
                        return RushSearch()
                                .whereEqual("wikiId", wizardUrl)
                                .and()
                                .startGroup()
                                .whereEqual("syntaxLanguage", "c++")
                                .or()
                                .whereEqual("syntaxLanguage", "cpp")
                                .endGroup()
                                .findSingle(WikiModel::class.java)
                    } else {
                        return RushSearch()
                                .whereEqual("syntaxLanguage", programLanguage)
                                .and()
                                .whereEqual("wikiId", wizardUrl)
                                .findSingle(WikiModel::class.java)
                    }
                }

                override fun onPostExecute(wikiModel: WikiModel) {
                    super.onPostExecute(wikiModel)
                    if (wikiModel.programWikis == null || wikiModel.programWikis.size == 0) {
                        getWikiFromFirebase(wizardUrl, getWikiModelListener)
                    } else
                        getWikiModelListener.onSuccess(wikiModel)
                }
            }.execute()

        } else {
            getWikiFromFirebase(wizardUrl, getWikiModelListener)
        }

    }

    private fun getWikiFromFirebase(wizardUrl: String, getWikiModelListener: GetWikiModelListener) {
        getProgramWikiDatabase()
        mProgramWikiDatabase!!.child(wizardUrl).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val wikiModel = dataSnapshot.getValue(WikiModel::class.java)
                getWikiModelListener.onSuccess(wikiModel)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(TAG, "getWikiModel : " + databaseError.toException().message)
                databaseError.toException().printStackTrace()
                getWikiModelListener.onError(databaseError)
            }
        })
    }

    interface GetCreekUserDBListener {
        fun onSuccess(creekUserDB: CreekUserDB)
        fun onError(databaseError: DatabaseError)
    }

    fun readCreekUserDB(getCreekUserDBListener: GetCreekUserDBListener) {
        if (creekPreferences.creekUserDB == null) {
            getCreekUserDBDatabase()
            mCreekUserDBDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot != null) {
                        val creekUserDB = dataSnapshot.getValue(CreekUserDB::class.java)
                        creekPreferences.creekUserDB = creekUserDB
                        if (creekUserDB != null) {
                            getCreekUserDBListener.onSuccess(creekUserDB)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    getCreekUserDBListener.onError(databaseError)
                }
            })
        } else {
            getCreekUserDBListener.onSuccess(creekPreferences.creekUserDB!!)
        }

    }


    fun writeUserProgramDetails(userProgramDetails: UserProgramDetails) {
        userProgramDatabase
        val programId = mUserProgramDatabase!!.push().key
        userProgramDetails.programId = programId
        for (programTable in userProgramDetails.programTables) {
            programTable.userProgramId = programId
        }
        userProgramDetails.programIndex.userProgramId = programId
        mUserProgramDatabase!!.child(programId).setValue(userProgramDetails)
    }

    fun updateViewCount(userProgramDetails: UserProgramDetails) {
        userProgramDatabase
        mUserProgramDatabase!!.child(userProgramDetails.programId)
                .runTransaction(object : Transaction.Handler {
                    override fun doTransaction(currentData: MutableData): Transaction.Result {

                        val programDetails = currentData.getValue(UserProgramDetails::class.java)
                        if (programDetails != null) {
                            programDetails.views = programDetails.views + 1
                            currentData.value = programDetails
                        }
                        return Transaction.success(currentData)
                    }

                    override fun onComplete(databaseError: DatabaseError?, b: Boolean, dataSnapshot: DataSnapshot) {

                    }
                })

    }

    fun updateLikes(isLiked: Boolean, userProgramDetails: UserProgramDetails) {
        userProgramDatabase
        mUserProgramDatabase!!.child(userProgramDetails.programId)
                .runTransaction(object : Transaction.Handler {
                    override fun doTransaction(currentData: MutableData): Transaction.Result {

                        val programDetails = currentData.getValue(UserProgramDetails::class.java)
                        if (programDetails != null) {
                            if (isLiked) {
                                val userAccount = creekPreferences.getSignInAccount()
                                if (!programDetails.likesList.contains(userAccount)) {
                                    programDetails.likesList.add(creekPreferences.getSignInAccount())
                                    programDetails.likes = programDetails.likes + 1
                                }
                            } else {
                                programDetails.likesList.remove(creekPreferences.getSignInAccount())
                                programDetails.likes = programDetails.likes - 1
                            }
                            currentData.value = programDetails
                        }
                        return Transaction.success(currentData)
                    }

                    override fun onComplete(databaseError: DatabaseError, b: Boolean, dataSnapshot: DataSnapshot) {

                    }
                })

    }

    interface ProgramWikiInterface {
        fun getProgramWiki(programWikis: ArrayList<WikiModel>)
        fun onError(error: DatabaseError)
    }

    fun initializeProgramWiki(programWikiInterface: ProgramWikiInterface) {
        if (creekPreferences.checkWikiUpdate() < 0) {
            getProgramWikiDatabase()
            mProgramWikiDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val programWikis = ArrayList<WikiModel>()
                    Log.d(TAG, "Wiki model Resoponse : " + dataSnapshot.toString())
                    for (childDataSnapShot in dataSnapshot.children) {
                        val wiki = childDataSnapShot.getValue(WikiModel::class.java)
                        wiki.save { }
                        programWikis.add(wiki)
                    }
                    programWikiInterface.getProgramWiki(programWikis)
                    creekPreferences.programWikiInserted = programWikis[programWikis.size - 1].wikiId
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(TAG, "initializeProgramWiki : " + databaseError.toException().message)
                    databaseError.toException().printStackTrace()
                    programWikiInterface.onError(databaseError)
                }
            })
        } else {
            object : AsyncTask<Void, Void, ArrayList<WikiModel>>() {

                override fun doInBackground(vararg voids: Void): ArrayList<WikiModel> {
                    val programWikis: ArrayList<WikiModel>
                    if (creekPreferences.programLanguage == "c++") {
                        programWikis = ArrayList(RushSearch()
                                .startGroup()
                                .whereEqual("syntaxLanguage", creekPreferences.programLanguage)
                                .or()
                                .whereEqual("syntaxLanguage", "cpp")
                                .endGroup()
                                .orderAsc("wikiId")
                                .find(WikiModel::class.java))
                    } else {
                        programWikis = ArrayList(RushSearch()
                                .whereEqual("syntaxLanguage", creekPreferences.programLanguage)
                                .orderAsc("wikiId")
                                .find(WikiModel::class.java))
                    }
                    return programWikis
                }

                override fun onPostExecute(programWikis: ArrayList<WikiModel>) {
                    super.onPostExecute(programWikis)
                    programWikiInterface.getProgramWiki(programWikis)
                }
            }.execute()

        }
    }

    interface ModuleInterface {
        fun getModules(languageModules: ArrayList<LanguageModule>)
        fun onError(error: DatabaseError)
    }

    interface ProgramIndexInterface {
        fun getProgramIndexes(program_indices: ArrayList<ProgramIndex>)
        fun onError(error: DatabaseError)
    }

    interface SyntaxInterface {
        fun getSyntaxModules(syntaxModules: ArrayList<SyntaxModule>)
        fun onError(error: DatabaseError)
    }

    fun initializeSyntax(languageModule: LanguageModule, syntaxInterface: SyntaxInterface) {
        if (creekPreferences.checkSyntaxUpdate() < 0) {
            Log.d(TAG, "initializeSyntax : Firebase task")
            getSyntaxModuleDatabase()
            mSyntaxModuleDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val syntaxModules = ArrayList<SyntaxModule>()
                    var largestSyntaxId = ""
                    val alphaNumComparator = AlphaNumComparator()
                    for (childDataSnapShot in dataSnapshot.children) {
                        val syntaxModule = childDataSnapShot.getValue(SyntaxModule::class.java)
                        if (largestSyntaxId == "") {
                            largestSyntaxId = syntaxModule.moduleId + "_" + syntaxModule.syntaxModuleId
                        } else {
                            if (alphaNumComparator.compare(largestSyntaxId, syntaxModule.moduleId + "_" + syntaxModule.syntaxModuleId) <= 0) {
                                largestSyntaxId = syntaxModule.moduleId + "_" + syntaxModule.syntaxModuleId
                            }
                        }
                        syntaxModule.save { }
                        if (syntaxModule.moduleId == languageModule.moduleId) {
                            syntaxModules.add(syntaxModule)
                        }
                    }
                    syntaxInterface.getSyntaxModules(syntaxModules)
                    Log.d(TAG, "initialize Syntax : Largest Syntax : " + largestSyntaxId)
                    creekPreferences.syntaxInserted = largestSyntaxId
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(TAG, "initializeSyntax : " + databaseError.toException().message)
                    databaseError.toException().printStackTrace()
                    syntaxInterface.onError(databaseError)
                }
            })
        } else {
            Log.d(TAG, "initializeSyntax : Async task")
            object : AsyncTask<Void, Void, ArrayList<SyntaxModule>>() {

                override fun doInBackground(vararg voids: Void): ArrayList<SyntaxModule> {
                    val syntaxModules: ArrayList<SyntaxModule>
                    if (creekPreferences.programLanguage == "c++") {
                        syntaxModules = ArrayList(RushSearch()
                                .whereEqual("moduleId", languageModule.moduleId)
                                .and()
                                .startGroup()
                                .whereEqual("syntaxLanguage", creekPreferences.programLanguage)
                                .or()
                                .whereEqual("syntaxLanguage", "cpp")
                                .endGroup()
                                .find(SyntaxModule::class.java))
                    } else {
                        syntaxModules = ArrayList(RushSearch()
                                .whereEqual("syntaxLanguage", creekPreferences.programLanguage)
                                .and()
                                .whereEqual("moduleId", languageModule.moduleId)
                                .find(SyntaxModule::class.java))
                    }
                    return syntaxModules
                }

                override fun onPostExecute(syntaxModules: ArrayList<SyntaxModule>) {
                    super.onPostExecute(syntaxModules)
                    syntaxInterface.getSyntaxModules(syntaxModules)
                }
            }.execute()

        }
    }


    fun initializeModules(moduleInterface: ModuleInterface) {

        if (creekPreferences.checkModulesUpdate() < 0) {
            Log.d(TAG, "initializeModules : FirebaseDBTask")
            getLanguageModuleDatabase()
            mLanguageModuleDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val languageModules = ArrayList<LanguageModule>()
                    for (childDataSnapShot in dataSnapshot.children) {
                        val languageModule = childDataSnapShot.getValue(LanguageModule::class.java)
                        languageModule.save { }
                        languageModules.add(languageModule)
                    }
                    moduleInterface.getModules(languageModules)
                    creekPreferences.modulesInserted = languageModules[languageModules.size - 1].moduleId
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(TAG, "initializeModules : " + databaseError.toException().message)
                    databaseError.toException().printStackTrace()
                    moduleInterface.onError(databaseError)
                }
            })
        } else {
            Log.d(TAG, "initializeModules : Asnctask")
            object : AsyncTask<Void, Void, ArrayList<LanguageModule>>() {

                override fun doInBackground(vararg voids: Void): ArrayList<LanguageModule> {
                    val programLanguage = creekPreferences.programLanguage
                    if (programLanguage.equals("c++", ignoreCase = true)) {
                        return ArrayList(RushSearch()
                                .whereEqual("moduleLanguage", creekPreferences.programLanguage)
                                .or()
                                .whereEqual("moduleLanguage", "cpp")
                                .orderAsc("moduleId")
                                .find(LanguageModule::class.java))
                    } else {
                        return ArrayList(RushSearch()
                                .whereEqual("moduleLanguage", creekPreferences.programLanguage)
                                .orderAsc("moduleId")
                                .find(LanguageModule::class.java))
                    }
                }

                override fun onPostExecute(languageModules: ArrayList<LanguageModule>) {
                    super.onPostExecute(languageModules)
                    moduleInterface.getModules(languageModules)
                }
            }.execute()

        }
    }

    fun initializeProgramIndexes(programIndexInterface: ProgramIndexInterface) {

        //Get last n number of programs : ? Store total programs in firebase, total_programs - existing max index

        if (!creekPreferences.checkProgramIndexUpdate()) {
            CommonUtils.displayProgressDialog(mContext as Activity, "Loading program index")
            if (!creekPreferences.isWelcomeDone) {
                AuxilaryUtils.generateBigNotification(mContext, "Welcome", "Hey there, Welcome to Practice Code, we have an array of " + programLanguage.toUpperCase() + " programs to be explored; Your learning starts here...")
                creekPreferences.isWelcomeDone = true
                getCreekUserStatsInBackground(object : CreekUserStatsListener {
                    override fun onSuccess(creekUserStats: CreekUserStats) {

                    }

                    override fun onFailure(databaseError: DatabaseError?) {

                    }
                })
            }
            programDatabase
            mProgramDatabase!!.child(PROGRAM_INDEX_CHILD)
                    .orderByKey()
                    .limitToLast(creekPreferences.programIndexDifference)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val program_indices = ArrayList<ProgramIndex>()
                            for (programIndexSnapshot in dataSnapshot.children) {
                                val program_index = programIndexSnapshot.getValue(ProgramIndex::class.java)
                                program_index.save { }
                                program_indices.add(program_index)
                            }
                            creekPreferences.setProgramIndex(program_indices[program_indices.size - 1].program_index)
                            Log.d(TAG, "Inserted program indexes : " + program_indices.size)
                            CommonUtils.dismissProgressDialog()
                            programIndexInterface.getProgramIndexes(program_indices)
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.d(TAG, "initializeProgramIndexes : " + databaseError.toException().message)
                            databaseError.toException().printStackTrace()
                            programIndexInterface.onError(databaseError)
                            CommonUtils.dismissProgressDialog()
                        }
                    })
        } else {
            CommonUtils.displayProgressDialog(mContext as Activity, "Loading program index")
            object : AsyncTask<Void, Void, ArrayList<ProgramIndex>>() {

                override fun doInBackground(vararg params: Void): ArrayList<ProgramIndex> {
                    return FirebaseDatabaseHandler(mContext).programIndexes

                }

                override fun onPostExecute(programIndices: ArrayList<ProgramIndex>) {
                    super.onPostExecute(programIndices)
                    CommonUtils.dismissProgressDialog()
                    programIndexInterface.getProgramIndexes(programIndices)
                }
            }.execute()
            Log.d(TAG, "Inserted program indexes found : " + creekPreferences.getProgramIndex())

        }

    }

    private val programIndexes: ArrayList<ProgramIndex>
        get() {
            if (programLanguage == "c++" || programLanguage == "cpp") {
                return ArrayList(RushSearch()
                        .startGroup()
                        .whereEqual("program_Language", "c++")
                        .or()
                        .whereEqual("program_Language", "cpp")
                        .endGroup()
                        .orderAsc("program_index")
                        .find(ProgramIndex::class.java))
            } else {
                return ArrayList(RushSearch()
                        .whereEqual("program_Language", programLanguage)
                        .orderAsc("program_index")
                        .find(ProgramIndex::class.java))
            }
        }

    interface ProgramTableInterface {
        fun getProgramTables(program_tables: ArrayList<ProgramTable>)
        fun onError(error: DatabaseError)
    }

    fun initializeProgramTables(programTableInterface: ProgramTableInterface) {
        if (!creekPreferences.checkProgramTableUpdate()) {
            program_tables = ArrayList<ProgramTable>()
            programDatabase
            mProgramDatabase!!
                    .child(PROGRAM_TABLE_CHILD)
                    .orderByKey()
                    .limitToLast(creekPreferences.programTableDifference)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            Log.d(TAG, "initializeProgramTables : indexSnapshot size : " + dataSnapshot.childrenCount)
                            for (indexSnapshot in dataSnapshot.children) {
                                Log.d(TAG, "initializeProgramTables : indexSnapshot size : " + indexSnapshot.childrenCount)
                                for (lineSnapShot in indexSnapshot.children) {
                                    val program_table = lineSnapShot.getValue(ProgramTable::class.java)
                                    program_table.save { }
                                    program_tables!!.add(program_table)
                                    Log.d(TAG, "Inserted program tables : " + program_tables!!.size)
                                }
                            }
                            Log.d(TAG, "Set Program Tables : " + programLanguage + " : " + program_tables!![program_tables!!.size - 1].program_index)
                            creekPreferences.setProgramTables(program_tables!![program_tables!!.size - 1].program_index)
                            programTableInterface.getProgramTables(program_tables!!)

                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.d(TAG, "initializeProgramTables : " + databaseError.toException().message)
                            databaseError.toException().printStackTrace()
                            programTableInterface.onError(databaseError)
                        }
                    })
        } else {
            Log.d(TAG, "Inserted program tables found : " + creekPreferences.getProgramTables())
            programTableInterface.getProgramTables(ArrayList<ProgramTable>())
        }
    }

    fun getAllCreekUserStatsInBackground() {
        userStatsDatabase
        userDatabase
        mUserStatsDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val creekUserStatsHashMap = HashMap<String, CreekUserStats>()
                for (child in dataSnapshot.children) {
                    val creekUserStats = child.getValue(CreekUserStats::class.java)
                    Log.d(TAG, "CreekUserStats : account : " + child.key)
                    creekUserStats.calculateReputation()
                    creekUserStatsHashMap.put(child.key, creekUserStats)
                    mUserStatsDatabase!!.child(child.key.replace("[-+.^:,]".toRegex(), "")).setValue(creekUserStats)
                    updateRankingForAllUsers(creekUserStatsHashMap)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    private fun updateRankingForAllUsers(creekUserStatsHashMap: HashMap<String, CreekUserStats>) {
        mUserDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
                    val creekUser = child.getValue(CreekUser::class.java)
                    if (creekUser != null && creekUser.emailId != null) {
                        val accountEmail = creekUser.emailId.replace("[-+.^:,]".toRegex(), "")
                        val creekUserStats = creekUserStatsHashMap[accountEmail]
                        if (creekUserStats != null) {
                            val userRanking = UserRanking()
                            userRanking.emailId = creekUser.emailId
                            userRanking.userPhotoUrl = creekUser.userPhotoUrl
                            userRanking.userFullName = creekUser.userFullName
                            userRanking.reputation = creekUserStats.creekUserReputation
                            mUserDatabase!!.child("ranking/" + creekUser.emailId.replace("[-+.^:,]".toRegex(), ""))
                                    .setValue(userRanking)
                        }
                    }

                }


            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

    interface CreekUserStatsListener {
        fun onSuccess(creekUserStats: CreekUserStats)
        fun onFailure(databaseError: DatabaseError?)
    }

    fun getCreekUserStatsInBackground(creekUserStatsListener: CreekUserStatsListener) {
        userStatsDatabase
        mUserStatsDatabase!!.child(creekPreferences.getSignInAccount().replace("[-+.^:,]".toRegex(), "")).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val creekUserStats = dataSnapshot.getValue(CreekUserStats::class.java)
                if (creekUserStats != null) {
                    creekPreferences.saveCreekUserStats(creekUserStats)
                    Log.d(TAG, "getCreekUserStatsInBackground : success : retrieved stats are : " + creekUserStats.toString())
                    creekUserStatsListener.onSuccess(creekUserStats)

                } else {
                    creekUserStatsListener.onFailure(null)
                    creekPreferences.saveCreekUserStats(CreekUserStats())
                    Log.d(TAG, "getCreekUserStatsInBackground : Failed : creating new stats : " + CreekUserStats().toString())
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                creekUserStatsListener.onFailure(databaseError)
                creekPreferences.saveCreekUserStats(CreekUserStats())
            }
        })

    }

    fun writeCreekUserStats(creekUserStats: CreekUserStats) {
        userStatsDatabase
        mUserStatsDatabase!!.child(creekPreferences.getSignInAccount().replace("[-+.^:,]".toRegex(), "")).setValue(creekUserStats)
        creekPreferences.saveCreekUserStats(creekUserStats)
        updateUserRanking(creekUserStats)
    }

    private fun updateUserRanking(creekUserStats: CreekUserStats) {
        if (creekUserStats.creekUserReputation > 0) {
            userDatabase
            val userRanking = UserRanking()
            userRanking.emailId = creekPreferences.getSignInAccount()
            userRanking.userPhotoUrl = creekPreferences.getAccountPhoto()
            userRanking.userFullName = creekPreferences.getAccountName()
            userRanking.reputation = creekUserStats.creekUserReputation
            mUserDatabase!!.child("ranking/" + creekPreferences.getSignInAccount().replace("[-+.^:,]".toRegex(), ""))
                    .setValue(userRanking)
        }

    }

    interface GetTopLearnersInterface {
        fun onSuccess(userRankings: ArrayList<UserRanking>)
        fun onFailure(databaseError: DatabaseError)
    }

    fun getTopLearners(getTopLearnersInterface: GetTopLearnersInterface) {
        userDatabase
        val query = mUserDatabase!!.child("ranking")
        query.orderByChild("reputation")
                .limitToLast(21)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        Log.d(TAG, "UserRanking : " + dataSnapshot.toString())
                        val userRankings = ArrayList<UserRanking>()
                        for (child in dataSnapshot.children) {
                            val userRanking = child.getValue(UserRanking::class.java)
                            if (userRanking.emailId != "programmer.creek@gmail.com")
                                userRankings.add(child.getValue(UserRanking::class.java))
                        }
                        getTopLearnersInterface.onSuccess(userRankings)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        getTopLearnersInterface.onFailure(databaseError)
                    }
                })
    }

    companion object {

        fun checkArrayList(tags: ArrayList<String>, newTag: String): Boolean {
            for (string in tags) {
                if (newTag.equals(string, ignoreCase = true)) {
                    return true
                }
            }
            return false
        }

        private val START_PROGRAM_TITLE = "<program_title>"
        private val END_PROGRAM_TITLE = "</program_title>"
        private val START_PROGRAM_LANGUAGE = "<program_language>"
        private val END_PROGRAM_LANGUAGE = "</program_language>"
        private val START_PROGRAM_EXPLANATION = "<program_explanation>"
        private val END_PROGRAM_EXPLANATION = "</program_explanation>"
        private val START_PROGRAM = "<program>"
        private val END_PROGRAM = "</program>"
    }


}

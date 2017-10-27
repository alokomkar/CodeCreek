package com.sortedqueue.programmercreek.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.google.gson.Gson
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.database.UserProgramDetails
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekAnalytics
import com.sortedqueue.programmercreek.util.CreekPreferences
import com.sortedqueue.programmercreek.util.FileUtils
import com.sortedqueue.programmercreek.view.UserProgramDialog

import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

import butterknife.ButterKnife
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by Alok on 25/05/17.
 */

class CodeShareHandlerActivity : AppCompatActivity(), FirebaseDatabaseHandler.ConfirmUserProgram {


    private var creekPreferences: CreekPreferences? = null
    private val filepath: String? = null
    private var sharedText: String? = null
    private val TAG = CodeShareHandlerActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_share)
        ButterKnife.bind(this)
        creekPreferences = CreekPreferences(this@CodeShareHandlerActivity)
        // Get intent, action and MIME type
        val intent = intent
        val action = intent.action
        val type = intent.type
        CreekAnalytics.logEvent(TAG, "Adding Code")
        if (Intent.ACTION_SEND == action && type != null) {
            if ("text/plain" == type) {
                handleSendText(intent) // Handle text being sent
            }
        }
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)

    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }

    internal fun handleSendText(intent: Intent) {
        sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (sharedText != null) {
            FirebaseDatabaseHandler(this@CodeShareHandlerActivity).compileSharedProgram(sharedText, this)
        }
    }


    override fun onSuccess(programIndex: ProgramIndex?, programTables: ArrayList<ProgramTable>) {

        if (programIndex != null && programTables.size > 0) {

            UserProgramDialog(
                    this@CodeShareHandlerActivity,
                    programIndex, programTables,
                    object : UserProgramDialog.WebUserProgramDialogListener {
                        override fun onSave(accessSpecifier: String, programIndex: ProgramIndex, programTables: ArrayList<ProgramTable>) {

                            val firebaseDatabaseHandler = FirebaseDatabaseHandler(this@CodeShareHandlerActivity)
                            firebaseDatabaseHandler.updateCodeCount()

                            val userProgramDetails = UserProgramDetails()
                            userProgramDetails.accessSpecifier = accessSpecifier
                            userProgramDetails.md5 = FileUtils.md5(sharedText)
                            userProgramDetails.emailId = creekPreferences!!.signInAccount
                            userProgramDetails.likes = 0
                            userProgramDetails.likesList = ArrayList<String>()
                            userProgramDetails.programIndex = programIndex
                            userProgramDetails.programTables = programTables
                            userProgramDetails.views = 0
                            userProgramDetails.programLanguage = programIndex.program_Language
                            userProgramDetails.programTitle = programIndex.program_Description.toLowerCase()

                            if (creekPreferences!!.addUserFile(userProgramDetails.md5)) {
                                try {
                                    CreekAnalytics.logEvent(TAG, JSONObject(Gson().toJson(programIndex)))
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }

                                firebaseDatabaseHandler.writeUserProgramDetails(userProgramDetails)
                                onProgressStatsUpdate(CreekUserStats.CHAPTER_SCORE)
                            } else {
                                CommonUtils.displayToast(this@CodeShareHandlerActivity, "File already added")
                            }


                        }

                        override fun onCancel() {
                            finish()
                        }

                        override fun onPreview(programIndex: ProgramIndex, programTables: ArrayList<ProgramTable>) {
                            val newIntentBundle = Bundle()
                            var newIntent: Intent? = null
                            programIndex.userProgramId = "trial"
                            newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true)
                            newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, programIndex)
                            newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, 1)
                            newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, programIndex.program_Description)
                            newIntentBundle.putParcelableArrayList(ProgrammingBuddyConstants.KEY_USER_PROGRAM, programTables)
                            newIntent = Intent(this@CodeShareHandlerActivity, ProgramActivity::class.java)
                            newIntent.putExtras(newIntentBundle)
                            startActivity(newIntent)
                        }
                    }, 1).showDialog()

        }

    }

    private fun onProgressStatsUpdate(chapterScore: Int) {
        CommonUtils.displayToast(this@CodeShareHandlerActivity, "You have gained " + chapterScore + "xp")
        finish()
    }

    override fun onError(errorMessage: String) {
        CommonUtils.displayToast(this@CodeShareHandlerActivity, errorMessage)
    }
}

package com.sortedqueue.programmercreek.activity


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView

import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CustomProgramLineListAdapter
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener
import com.sortedqueue.programmercreek.util.AuxilaryUtils

import java.util.ArrayList

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants.KEY_USER_PROGRAM

class ProgramActivity : AppCompatActivity(), UIUpdateListener {

    internal var mProgramList = ArrayList<String>()
    internal var mProgramExplanationList = ArrayList<String>()

    //ArrayAdapter<String> mAdapterProgramList;
    internal var mAdapterProgramList: CustomProgramLineListAdapter ?= null
    internal var mAdapterProgramExplanationList: CustomProgramLineListAdapter ?= null

    internal var mProgramTableList: ArrayList<ProgramTable>? = null

    internal var mProgramListView: ListView ?= null
    internal var mProgramExplanationListView: ListView ?= null

    internal var mProgramIndex: Int = 0
    internal var mProgDescriptionBtn: Button ?= null
    internal var mNextProgramBtn: ImageButton ?= null
    internal var mPrevProgramBtn: ImageButton ?= null
    internal var program_index: ProgramIndex ?= null

    internal var mListPostion = 0
    internal var mWizard = false
    internal var mProgram_Title: String? = null
    private var mTotalPrograms: Int = 0
    private var newProgramActivityBundle: Bundle? = null
    //gesture detector
    /**
     * http://code.tutsplus.com/tutorials/android-sdk-detecting-gestures--mobile-21161
     */

    override fun onResume() {
        super.onResume()
        CreekApplication.instance.isAppRunning = (true)
    }

    override fun onPause() {
        super.onPause()
        CreekApplication.instance.isAppRunning = (false)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newProgramActivityBundle = intent.extras
        program_index = newProgramActivityBundle!!.get(ProgrammingBuddyConstants.KEY_PROG_ID) as ProgramIndex
        mProgramIndex = program_index!!.program_index
        mTotalPrograms = newProgramActivityBundle!!.getInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, 0)
        this.mWizard = newProgramActivityBundle!!.getBoolean(ProgramListActivity.KEY_WIZARD)
        mProgramTableList = newProgramActivityBundle!!.getParcelableArrayList<ProgramTable>(KEY_USER_PROGRAM)
        //boolean modules = newProgramActivityBundle.getBoolean(DashboardActivity.KEY_MODULE_LIST);

        mProgram_Title = program_index!!.program_Description
        if (mProgram_Title == null) {
            AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", this)
        } else {
            title = "Revise : " + mProgram_Title!!
            Log.d("ProgramActivity", " :: ProgramIndex : " + mProgramIndex + "")
            if (mProgramTableList != null && mProgramTableList!!.size > 0) {
                initUI(mProgramTableList!!)
            } else {
                getProgramTableFromDB(mProgramIndex)
            }

        }
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)
    }

    private fun getProgramTableFromDB(program_Index: Int) {

        FirebaseDatabaseHandler(this@ProgramActivity)
                .getProgramTablesInBackground(program_Index, object : FirebaseDatabaseHandler.GetProgramTablesListener {
                    override fun onSuccess(programTables: ArrayList<ProgramTable>) {
                        mProgramTableList = programTables
                        run { initUI(mProgramTableList!!) }
                    }

                    override fun onError(databaseError: DatabaseError) {

                    }
                })

    }

    private fun initUI(program_TableList: List<ProgramTable>) {

        mProgramList = ArrayList<String>()
        mProgramExplanationList = ArrayList<String>()

        val iteraor = program_TableList.iterator()
        while (iteraor.hasNext()) {

            val newProgramTable = iteraor.next()
            mProgramList.add(newProgramTable.line_No.toString() + ". " + newProgramTable.program_Line)
            mProgramExplanationList.add(newProgramTable.line_No.toString() + ". " + newProgramTable.program_Line_Description)

        }

        setContentView(R.layout.activity_program)

        //FrameLayout container = (LinearLayout) findViewById(R.id.container);
        mProgramListView = findViewById(R.id.list_program) as ListView
        mProgramExplanationListView = findViewById(R.id.list_explanation) as ListView


        // Prepare the ListView
        /*mAdapterProgramList = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mProgramList);*/
        mAdapterProgramList = CustomProgramLineListAdapter(this, android.R.layout.simple_list_item_1,
                R.id.progamLineTxtView, mProgramList, false)

        // Prepare the ListView
        mAdapterProgramExplanationList = CustomProgramLineListAdapter(this, android.R.layout.simple_list_item_1,
                R.id.progamLineTxtView, mProgramExplanationList, true)

        mProgramListView!!.adapter = mAdapterProgramList
        mProgramExplanationListView!!.adapter = mAdapterProgramExplanationList
        mProgramExplanationListView!!.rotationY = -90f
        mListPostion = mProgramListView!!.firstVisiblePosition

        initButtons()

        /*AuxilaryUtils.displayInformation(
				ProgramActivity.this,
				R.string.add_notes,
				R.string.add_notes_and_earn_reputation,
				new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialogInterface) {

			}

		});*/

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_notes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_notes) {
            addNotes()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addNotes() {
        val intent = Intent(this@ProgramActivity, NotesActivity::class.java)
        intent.putExtra(ProgrammingBuddyConstants.KEY_PROG_ID, mProgramTableList)
        startActivityForResult(intent, ProgrammingBuddyConstants.RESULT_NOTES)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == ProgrammingBuddyConstants.RESULT_NOTES) {
            if (requestCode == AppCompatActivity.RESULT_OK) {

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    private fun initButtons() {

        mProgDescriptionBtn = findViewById(R.id.descriptionBtn) as Button
        mProgDescriptionBtn!!.setOnClickListener(mButtonClickListener)

        mNextProgramBtn = findViewById(R.id.nextProgramBtn) as ImageButton
        mNextProgramBtn!!.setOnClickListener(mButtonClickListener)

        mPrevProgramBtn = findViewById(R.id.prevProgramBtn) as ImageButton
        mPrevProgramBtn!!.setOnClickListener(mButtonClickListener)
        enableDisablePrevButton()
        if (mWizard == true) {
            mPrevProgramBtn!!.visibility = View.GONE
        }


    }

    internal var mButtonClickListener: OnClickListener = OnClickListener { v ->
        when (v.id) {
            R.id.descriptionBtn -> programDescriptionAction()

            R.id.prevProgramBtn -> prevProgramBtnAction()

            R.id.nextProgramBtn -> if (mWizard == true) {
                showSelectBox()
            } else {
                nextProgramBtnAction()
            }
        }
    }


    private fun programDescriptionAction() {

        if (mProgDescriptionBtn!!.text === "Flip") {
            mProgDescriptionBtn!!.text = "Flip"
        } else {
            mProgDescriptionBtn!!.text = "Flip"
        }
        flipit()

    }

    protected fun nextProgramBtnAction() {
        mProgramIndex = mProgramIndex + 1
        enableDisablePrevButton()
        NextProgram(mProgramIndex)
    }

    protected fun prevProgramBtnAction() {
        mProgramIndex = mProgramIndex - 1
        enableDisablePrevButton()
        NextProgram(mProgramIndex)
    }

    fun getProgramTitle(program_Index: Int): String {
        return AuxilaryUtils.getProgramTitle(program_Index, this@ProgramActivity)!!
    }

    fun enableDisablePrevButton() {
        if (mProgramIndex == 1 || mProgramIndex < 1) {
            mPrevProgramBtn!!.isEnabled = false

        } else {
            mPrevProgramBtn!!.isEnabled = true
        }

    }

    protected fun NextProgram(program_Index: Int) {
        /**
         * Reset the list and reset adapter to change view
         */
        if (program_Index > 0 && program_Index <= mTotalPrograms) {

            FirebaseDatabaseHandler(this@ProgramActivity)
                    .getProgramTablesInBackground(program_Index, object : FirebaseDatabaseHandler.GetProgramTablesListener {
                        override fun onSuccess(programTables: ArrayList<ProgramTable>) {
                            val program_TableList = programTables
                            mAdapterProgramExplanationList!!.clear()
                            mAdapterProgramList!!.clear()
                            mListPostion = 1
                            mProgramList = ArrayList<String>()
                            mProgramExplanationList = ArrayList<String>()
                            mProgram_Title = getProgramTitle(mProgramIndex)

                            if (mProgram_Title == null) {
                                title = "Revise"
                                AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", this@ProgramActivity)
                            } else {
                                title = "Revise : " + mProgram_Title!!
                                val iteraor = program_TableList.iterator()

                                while (iteraor.hasNext()) {
                                    val newProgramTable = iteraor.next()
                                    mProgramList.add(newProgramTable.line_No.toString() + ". " + newProgramTable.program_Line)
                                    mProgramExplanationList.add(newProgramTable.line_No.toString() + ". " + newProgramTable.program_Line_Description)

                                }


                            }

                            // Prepare the ListView
                            mAdapterProgramList!!.addAll(mProgramList)
                            // Prepare the ListView
                            mAdapterProgramExplanationList!!.addAll(mProgramExplanationList)

                            mAdapterProgramList!!.setNotifyOnChange(true)
                            mAdapterProgramExplanationList!!.setNotifyOnChange(true)


                        }

                        override fun onError(databaseError: DatabaseError) {

                        }
                    })

        }
        if (program_Index > mTotalPrograms) {
            AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", this@ProgramActivity)
            mProgramIndex--
        }

    }

    private val accelerator = AccelerateInterpolator()
    private val decelerator = DecelerateInterpolator()

    private fun flipit() {

        val visibleList: ListView
        val invisibleList: ListView
        if (mProgramListView!!.visibility == View.GONE) {
            mListPostion = mProgramExplanationListView!!.firstVisiblePosition
            visibleList = mProgramExplanationListView!!
            invisibleList = mProgramListView!!
        } else {
            mListPostion = mProgramListView!!.firstVisiblePosition
            invisibleList = mProgramExplanationListView!!
            visibleList = mProgramListView!!
        }
        val visToInvis = ObjectAnimator.ofFloat(visibleList, "rotationY", 0f, 90f)
        visToInvis.duration = 500
        visToInvis.interpolator = accelerator
        val invisToVis = ObjectAnimator.ofFloat(invisibleList, "rotationY",
                -90f, 0f)
        invisToVis.duration = 500
        invisToVis.interpolator = decelerator
        visToInvis.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(anim: Animator) {
                visibleList.visibility = View.GONE
                invisToVis.start()
                invisibleList.visibility = View.VISIBLE
            }
        })
        visToInvis.start()
        //TODO TO retain postion
        visibleList.setSelection(mListPostion)
        invisibleList.setSelection(mListPostion)
    }

    protected fun showSelectBox() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Quiz Mode - Question Type")
        val boxTypes = arrayOf("Description", "Program Code")
        builder.setItems(boxTypes, quizTypeListener)
        builder.setNegativeButton("Cancel", null)
        val alertDialog = builder.create()
        alertDialog.show()

    }

    /**
     * Quiz Type Listener
     */
    internal var quizTypeListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { dialog, which ->
        val newIntentBundle = Bundle()
        val newIntent = Intent(this@ProgramActivity, WizardActivity::class.java)
        newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_QUIZ)
        newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true)

        when (which) {

            KEY_QUIZ_DESCRIPTION_QUESTION -> {
                newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, program_index)
                newIntentBundle.putInt(KEY_QUIZ_TYPE, KEY_QUIZ_DESCRIPTION_QUESTION)
            }

            KEY_QUIZ_PROGRAM_CODE_QUESTION -> {
                newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, program_index)
                newIntentBundle.putInt(KEY_QUIZ_TYPE, KEY_QUIZ_PROGRAM_CODE_QUESTION)
            }
        }
        newIntentBundle.putParcelableArrayList(KEY_USER_PROGRAM, newProgramActivityBundle!!.getParcelableArrayList<Parcelable>(KEY_USER_PROGRAM))
        newIntent.putExtras(newIntentBundle)
        startActivity(newIntent)
        this@ProgramActivity.finish()
    }


    override fun updateUI() {

        FirebaseDatabaseHandler(this@ProgramActivity)
                .getProgramTablesInBackground(mProgramIndex, object : FirebaseDatabaseHandler.GetProgramTablesListener {
                    override fun onSuccess(programTables: ArrayList<ProgramTable>) {
                        mProgramTableList = programTables
                        if (mProgramTableList == null || mProgramTableList!!.size == 0) {
                            AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", this@ProgramActivity)
                            mProgramIndex--
                        } else {
                            initUI(mProgramTableList!!)
                        }
                    }

                    override fun onError(databaseError: DatabaseError) {

                    }
                })


    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }

    override fun onBackPressed() {
        finish()
    }

    companion object {

        var KEY_QUIZ_TYPE = "Quiz_Type"

        val KEY_QUIZ_DESCRIPTION_QUESTION = 1
        val KEY_QUIZ_PROGRAM_CODE_QUESTION = 0
        val KEY_WIZARD = "wizard_mode"
    }
}

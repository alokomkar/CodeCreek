package com.sortedqueue.programmercreek.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
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


class MemorizeProgramActivity : AppCompatActivity(), UIUpdateListener {


    internal var mAdapterProgramList: CustomProgramLineListAdapter ?= null
    internal var mAdapterProgramExplanationList: CustomProgramLineListAdapter ?= null

    internal var mProgramListView: ListView ?= null
    internal var mProgramExplanationListView: ListView ?= null
    internal var mProgramList: ArrayList<String> ?= null
    internal var mProgramExplanationList: ArrayList<String> ?= null
    internal var mProgramIndex: ProgramIndex ?= null
    internal var programIndex: Int = 0
    internal var mProgDescriptionBtn: Button ?= null
    internal var mLinebylineprogramList: ArrayList<String>? = ArrayList()
    internal var mLinebylineprogramExplanationList: ArrayList<String>? = ArrayList()
    internal var mIndex = 0
    internal var mProgramLength = 0

    internal var mPrevProgramBtn: ImageButton ?= null
    internal var mNextProgramBtn: ImageButton ?= null
    internal var mShowOrHideProgramBtn: ImageButton ?= null
    internal var mWizard = false

    var KEY_PROG_TABLE_INSERT = "insertProgramTable"
    internal var mProgramTableList: ArrayList<ProgramTable>? = null
    internal var mProgram_Title: String? = null
    private var mShowAllDrawable: Drawable? = null
    private var mHideDrawable: Drawable? = null
    private var mTotalPrograms: Int = 0
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
        setContentView(R.layout.activity_revise_program)

        val newProgramActivityBundle = intent.extras
        mProgramIndex = newProgramActivityBundle!!.get(ProgrammingBuddyConstants.KEY_PROG_ID) as ProgramIndex
        programIndex = mProgramIndex!!.program_index
        mTotalPrograms = newProgramActivityBundle.getInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS)
        mWizard = newProgramActivityBundle.getBoolean(ProgramListActivity.KEY_WIZARD)
        mShowAllDrawable = ContextCompat.getDrawable(this, R.drawable.ic_show_all)
        mHideDrawable = ContextCompat.getDrawable(this, R.drawable.ic_remove)
        Log.d("Program Activity", " :: ProgramIndex :  " + mProgramIndex + "")
        getProgramTableFromDB(programIndex)
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)

    }

    private fun getProgramTableFromDB(program_Index: Int) {

        FirebaseDatabaseHandler(this@MemorizeProgramActivity)
                .getProgramTablesInBackground(program_Index, object : FirebaseDatabaseHandler.GetProgramTablesListener {
                    override fun onSuccess(programTables: ArrayList<ProgramTable>) {
                        mProgramTableList = programTables
                        run { initUI(mProgramTableList) }
                    }

                    override fun onError(databaseError: DatabaseError) {

                    }
                })


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
        val intent = Intent(this@MemorizeProgramActivity, NotesActivity::class.java)
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

    private fun initUI(program_TableList: List<ProgramTable>?) {

        if (program_TableList != null && program_TableList.size > 0) {

            mProgram_Title = mProgramIndex!!.program_Description
            title = "Memorize : " + mProgram_Title!!
            mProgramList = ArrayList<String>()
            mProgramExplanationList = ArrayList<String>()

            val iteraor = program_TableList.iterator()
            while (iteraor.hasNext()) {
                val newProgramTable = iteraor.next()
                mProgramList!!.add(newProgramTable.line_No.toString() + ". " + newProgramTable.program_Line)
                mProgramExplanationList!!.add(newProgramTable.line_No.toString() + ". " + newProgramTable.program_Line_Description)

            }

            mProgramLength = mProgramList!!.size
            Log.d("Program Length", mProgramLength.toString() + "")

            mProgramListView = findViewById(R.id.list_program) as ListView
            mProgramExplanationListView = findViewById(R.id.list_explanation) as ListView


            mLinebylineprogramExplanationList!!.add(mProgramExplanationList!![mIndex])
            mLinebylineprogramList!!.add(mProgramList!![mIndex])
            mIndex++


            // Prepare the ListView
            mAdapterProgramList = CustomProgramLineListAdapter(this,
                    android.R.layout.simple_list_item_1, R.id.progamLineTxtView, mLinebylineprogramList!!, false)
            // Prepare the ListView
            mAdapterProgramExplanationList = CustomProgramLineListAdapter(this,
                    android.R.layout.simple_list_item_1, R.id.progamLineTxtView, mLinebylineprogramExplanationList!!, true)

            mProgramListView!!.adapter = mAdapterProgramList
            mProgramExplanationListView!!.adapter = mAdapterProgramExplanationList
            mProgramExplanationListView!!.rotationY = -90f

            initButtons()

            /*AuxilaryUtils.displayInformation(
					MemorizeProgramActivity.this,
					R.string.add_notes,
					R.string.add_notes_and_earn_reputation,
					new DialogInterface.OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialogInterface) {

						}

					});*/


        }


    }

    private fun initButtons() {

        mProgDescriptionBtn = findViewById(R.id.descriptionBtn) as Button
        mProgDescriptionBtn!!.setOnClickListener {
            if (mProgDescriptionBtn!!.text === "Flip") {
                mProgDescriptionBtn!!.text = "Flip"
            } else {
                mProgDescriptionBtn!!.text = "Flip"
            }
            flipit()
        }

        mShowOrHideProgramBtn = findViewById(R.id.showAllBtn_revise) as ImageButton
        mShowOrHideProgramBtn!!.setImageDrawable(mShowAllDrawable)
        mShowOrHideProgramBtn!!.setOnClickListener {
            mAdapterProgramExplanationList!!.clear()
            mAdapterProgramList!!.clear()
            Log.d("Index < programlength", mIndex.toString() + "<" + mProgramLength)
            if (mLinebylineprogramExplanationList != null) {
                mLinebylineprogramExplanationList!!.clear()
                mLinebylineprogramExplanationList = ArrayList<String>()
            }
            if (mLinebylineprogramList != null) {
                mLinebylineprogramList!!.clear()
                mLinebylineprogramList = ArrayList<String>()
            }
            if (mShowOrHideProgramBtn!!.drawable == mShowAllDrawable == true) {

                for (i in 0..mProgramLength - 1) {
                    mLinebylineprogramExplanationList!!.add(mProgramExplanationList!![i])
                    mLinebylineprogramList!!.add(mProgramList!![i])
                }
                mIndex = mProgramLength
                // Prepare the ListView
                mAdapterProgramList!!.addAll(mLinebylineprogramList!!)
                // Prepare the ListView
                mAdapterProgramExplanationList!!.addAll(mLinebylineprogramExplanationList!!)
                mAdapterProgramList!!.notifyDataSetChanged()
                mAdapterProgramExplanationList!!.notifyDataSetChanged()

                //To scroll down automatically upon introduction of new line
                mProgramExplanationListView!!.setSelection(mAdapterProgramExplanationList!!.count - 1)
                mProgramListView!!.setSelection(mAdapterProgramList!!.count - 1)
                mShowOrHideProgramBtn!!.setImageDrawable(mHideDrawable)
            } else {
                mIndex = 1
                mShowOrHideProgramBtn!!.setImageDrawable(mShowAllDrawable)
            }
        }


        mNextProgramBtn = findViewById(R.id.nextProgramBtn_revise) as ImageButton
        mPrevProgramBtn = findViewById(R.id.prevProgramBtn_revise) as ImageButton

        enableDisablePrevButton()

        mNextProgramBtn!!.setOnClickListener {
            programIndex = programIndex + 1
            enableDisablePrevButton()
            mLinebylineprogramExplanationList = ArrayList<String>()
            mLinebylineprogramList = ArrayList<String>()
            NextProgram(programIndex)
        }

        mPrevProgramBtn!!.setOnClickListener {
            programIndex = programIndex - 1
            enableDisablePrevButton()
            mLinebylineprogramExplanationList = ArrayList<String>()
            mLinebylineprogramList = ArrayList<String>()
            NextProgram(programIndex)
        }


        val hintProgramButton = findViewById(R.id.hintProgramBtn) as ImageButton
        hintProgramButton.setOnClickListener {
            mAdapterProgramExplanationList!!.clear()
            mAdapterProgramList!!.clear()
            Log.d("Index < programlength", mIndex.toString() + "<" + mProgramLength)
            if (mLinebylineprogramExplanationList != null) {
                mLinebylineprogramExplanationList!!.clear()
                mLinebylineprogramExplanationList = ArrayList<String>()
            }
            if (mLinebylineprogramList != null) {
                mLinebylineprogramList!!.clear()
                mLinebylineprogramList = ArrayList<String>()
            }
            mIndex++
            if (mIndex <= mProgramLength) {

                for (i in 0..mIndex - 1) {
                    mLinebylineprogramExplanationList!!.add(mProgramExplanationList!![i])
                    mLinebylineprogramList!!.add(mProgramList!![i])
                }
                // Prepare the ListView
                mAdapterProgramList!!.addAll(mLinebylineprogramList!!)
                // Prepare the ListView
                mAdapterProgramExplanationList!!.addAll(mLinebylineprogramExplanationList!!)
                mAdapterProgramList!!.notifyDataSetChanged()
                mAdapterProgramExplanationList!!.notifyDataSetChanged()

                //To scroll down automatically upon introduction of new line
                mProgramExplanationListView!!.setSelection(mAdapterProgramExplanationList!!.count - 1)
                mProgramListView!!.setSelection(mAdapterProgramList!!.count - 1)

            } else {
                NextProgram(programIndex)
            }
            //To navigate to next program - disable this.
            /**else {
             * NextProgram(mProgramIndex++);
             * enableDisablePrevButton();
             * } */
            /**else {
             * NextProgram(mProgramIndex++);
             * enableDisablePrevButton();
             * } */
        }

        checkWizardMode()


    }

    private fun checkWizardMode() {

        if (mWizard == true) {

            mPrevProgramBtn!!.visibility = View.GONE
            mNextProgramBtn!!.visibility = View.GONE
            mShowOrHideProgramBtn!!.setImageDrawable(mShowAllDrawable)
            mShowOrHideProgramBtn!!.setOnClickListener { showSelectBox() }

        }

    }

    fun getProgramTitle(program_Index: Int): String {
        return AuxilaryUtils.getProgramTitle(program_Index, this@MemorizeProgramActivity)!!
    }

    fun enableDisablePrevButton() {
        if (programIndex == 1 || programIndex < 1) {
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
            mIndex = 0
            FirebaseDatabaseHandler(this@MemorizeProgramActivity)
                    .getProgramTablesInBackground(program_Index, object : FirebaseDatabaseHandler.GetProgramTablesListener {
                        override fun onSuccess(programTables: ArrayList<ProgramTable>) {
                            val program_TableList = programTables
                            initAdapters(program_TableList, program_Index)
                        }

                        override fun onError(databaseError: DatabaseError) {

                        }
                    })


        }
        if (program_Index > mTotalPrograms) {
            AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", this@MemorizeProgramActivity)
            programIndex--
        }

    }

    private fun initAdapters(program_TableList: ArrayList<ProgramTable>?, program_Index: Int) {
        if (program_TableList != null && program_TableList.size > 0) {
            /*if( mProgDescriptionBtn.getText().equals("Flip")) {
					flipit();
					mProgDescriptionBtn.setText("Flip");
				}*/

            mAdapterProgramExplanationList!!.clear()
            mAdapterProgramList!!.clear()
            mProgram_Title = getProgramTitle(program_Index)

            if (mProgram_Title == null) {
                title = "Memorize"
                AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", this@MemorizeProgramActivity)
            } else {
                title = "Memorize : " + getProgramTitle(program_Index)

                mProgramList = ArrayList<String>()
                mProgramExplanationList = ArrayList<String>()

                val iteraor = program_TableList.iterator()

                while (iteraor.hasNext()) {
                    val newProgramTable = iteraor.next()
                    mProgramList!!.add(newProgramTable.line_No.toString() + ". " + newProgramTable.program_Line)
                    mProgramExplanationList!!.add(newProgramTable.line_No.toString() + ". " + newProgramTable.program_Line_Description)
                }
                mProgramLength = mProgramList!!.size
                Log.d("Program Length", mProgramLength.toString() + "")
            }

            mLinebylineprogramExplanationList!!.add(mProgramExplanationList!![mIndex])
            mLinebylineprogramList!!.add(mProgramList!![mIndex++])

            // Prepare the ListView
            mAdapterProgramList!!.addAll(mLinebylineprogramList!!)
            // Prepare the ListView
            mAdapterProgramExplanationList!!.addAll(mLinebylineprogramExplanationList!!)

            mAdapterProgramList!!.setNotifyOnChange(true)
            mAdapterProgramExplanationList!!.setNotifyOnChange(true)
        }
    }


    private val accelerator = AccelerateInterpolator()
    private val decelerator = DecelerateInterpolator()

    internal var mListPostion = 0

    private fun flipit() {
        val visibleList: ListView
        val invisibleList: ListView
        if (mProgramListView!!.visibility == View.GONE) {
            visibleList = mProgramExplanationListView!!
            invisibleList = mProgramListView!!
        } else {
            invisibleList = mProgramExplanationListView!!
            visibleList = mProgramListView!!
        }
        mListPostion = visibleList.firstVisiblePosition
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
        val newIntent = Intent(this@MemorizeProgramActivity, WizardActivity::class.java)
        newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_QUIZ)
        newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true)

        when (which) {

            ProgrammingBuddyConstants.KEY_QUIZ_DESCRIPTION_QUESTION -> {
                newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, mProgramIndex)
                newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_QUIZ_TYPE, ProgrammingBuddyConstants.KEY_QUIZ_DESCRIPTION_QUESTION)
            }

            ProgrammingBuddyConstants.KEY_QUIZ_PROGRAM_CODE_QUESTION -> {
                newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, mProgramIndex)
                newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_QUIZ_TYPE, ProgrammingBuddyConstants.KEY_QUIZ_PROGRAM_CODE_QUESTION)
            }
        }

        newIntent.putExtras(newIntentBundle)
        startActivity(newIntent)
        this@MemorizeProgramActivity.finish()
    }

    override fun updateUI() {
        FirebaseDatabaseHandler(this@MemorizeProgramActivity)
                .getProgramTablesInBackground(programIndex, object : FirebaseDatabaseHandler.GetProgramTablesListener {
                    override fun onSuccess(programTables: ArrayList<ProgramTable>) {
                        mProgramTableList = programTables
                        if (mProgramTableList == null || mProgramTableList!!.size == 0) {
                            AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", this@MemorizeProgramActivity)
                            programIndex--
                        } else {
                            initUI(mProgramTableList)
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


}

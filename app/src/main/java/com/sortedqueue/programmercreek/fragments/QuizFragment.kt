package com.sortedqueue.programmercreek.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView

import com.google.firebase.database.DatabaseError
import com.google.gson.Gson
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.activity.ProgramListActivity
import com.sortedqueue.programmercreek.adapter.QuizRecyclerAdapter
import com.sortedqueue.programmercreek.asynctask.ProgramFetcherTask
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.database.QuizModel
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener
import com.sortedqueue.programmercreek.interfaces.UIProgramFetcherListener
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener
import com.sortedqueue.programmercreek.interfaces.WizardNavigationListener
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekAnalytics
import com.sortedqueue.programmercreek.util.ShuffleList
import kotlinx.android.synthetic.main.activity_quiz.*

import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList
import java.util.concurrent.TimeUnit




/**
 * Created by Alok on 03/01/17.
 */

class QuizFragment : Fragment(), UIUpdateListener, UIProgramFetcherListener, TestCompletionListener {

    internal var mProgramList: ArrayList<String> ?= null
    internal var mShuffleProgramList: ArrayList<String> ?= null
    internal var mProgramExplanationList: ArrayList<String> ?= null
    internal var mCheckSolutionBtn: Button? = null
    internal var mTimerBtn: Button? = null
    internal var remainingTime: Long = 0
    internal var time: Long = 0
    internal var interval: Long = 0
    internal var mCountDownTimer: CountDownTimer? = null
    internal var mWizard = false
    internal var mQuizMode = -1

    private var quizModels: ArrayList<QuizModel>? = null
    private var quizRecyclerAdapter: QuizRecyclerAdapter? = null
    private var bundle: Bundle? = null
    private var mInvokeMode: Int = 0
    private var mProgramTableList: ArrayList<ProgramTable>? = null
    private var moduleDetailsScrollPageListener: ModuleDetailsScrollPageListener? = null
    private val TAG = QuizFragment::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.activity_quiz, container, false)




        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCheckSolutionBtn = view!!.findViewById(R.id.checkQuizButton) as Button
        mTimerBtn = view.findViewById(R.id.timerButton) as Button
        mTimerBtn!!.text = "00:00"
        mTimerBtn!!.isEnabled = false


        mWizard = bundle!!.getBoolean(ProgramListActivity.KEY_WIZARD)

        if (bundle != null) {
            mQuizMode = bundle!!.getInt(ProgramListActivity.KEY_QUIZ_TYPE, ProgramListActivity.KEY_QUIZ_PROGRAM_CODE_QUESTION)
            initQuiz(mQuizMode)
        }
    }

    fun getmProgramList(): ArrayList<String> {
        val programList = ArrayList<String>()
        for (programTable in mProgramTableList!!) {
            programList.add(programTable.program_Line)
        }
        return programList
    }

    fun setBundle(bundle: Bundle) {
        this.bundle = bundle
    }

    internal var mProgramIndex = 0
    private var program_index: ProgramIndex? = null

    @SuppressLint("SimpleDateFormat")
    private fun initQuiz(quizMode: Int) {
        mInvokeMode = bundle!!.getInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST, -1)
        progressLayout!!.visibility = View.GONE
        mProgramTableList = bundle!!.getParcelableArrayList<ProgramTable>(ProgrammingBuddyConstants.KEY_USER_PROGRAM)
        if (mProgramTableList != null && mProgramTableList!!.size > 0) {
            program_index = bundle!!.get(ProgrammingBuddyConstants.KEY_PROG_ID) as ProgramIndex
            mProgramIndex = program_index!!.program_index
            initUI(mProgramTableList)
        } else {
            if (mInvokeMode == ProgrammingBuddyConstants.KEY_LESSON) {
                mWizard = false
                FirebaseDatabaseHandler(context).getProgramIndexInBackGround(bundle!!.getInt(ProgrammingBuddyConstants.KEY_PROG_ID),
                        object : FirebaseDatabaseHandler.GetProgramIndexListener {
                            override fun onSuccess(programIndex: ProgramIndex) {
                                program_index = programIndex
                                mProgramIndex = programIndex.program_index
                                getProgramTables()
                            }

                            override fun onError(databaseError: DatabaseError) {
                                CommonUtils.displayToast(context, R.string.unable_to_fetch_data)
                            }
                        })
            } else {
                program_index = bundle!!.get(ProgrammingBuddyConstants.KEY_PROG_ID) as ProgramIndex
                mProgramIndex = program_index!!.program_index
                getProgramTables()
            }
        }

    }

    private fun getProgramTables() {
        FirebaseDatabaseHandler(context)
                .getProgramTablesInBackground(mProgramIndex, object : FirebaseDatabaseHandler.GetProgramTablesListener {
                    override fun onSuccess(programTables: ArrayList<ProgramTable>) {
                        run { initUI(programTables) }
                    }

                    override fun onError(databaseError: DatabaseError?) {

                    }
                })
    }

    private fun initUI(program_TableList: ArrayList<ProgramTable>?) {
        if (program_index != null) {
            try {
                CreekAnalytics.logEvent(TAG, JSONObject(Gson().toJson(program_index)))
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
        activity.title = "Quiz : " + program_index!!.program_Description
        mProgramTableList = program_TableList
        if (program_TableList != null && program_TableList.size > 0) {
            mProgramList = ArrayList<String>()
            mProgramExplanationList = ArrayList<String>()

            val iterator = program_TableList.iterator()
            var programLine: String? = null
            if (mQuizMode == ProgramListActivity.KEY_QUIZ_DESCRIPTION_QUESTION) {
                while (iterator.hasNext()) {
                    val newProgram_Table = iterator.next()
                    programLine = newProgram_Table.program_Line.trim { it <= ' ' }
                    if (programLine.trim { it <= ' ' } == "{" == false && programLine.trim { it <= ' ' } == "}" == false) {
                        mProgramList!!.add(programLine)
                        mProgramExplanationList!!.add(newProgram_Table.program_Line_Description)
                    }

                }
            } else {
                while (iterator.hasNext()) {
                    val newProgram_Table = iterator.next()
                    programLine = newProgram_Table.program_Line.trim { it <= ' ' }
                    if (programLine.trim { it <= ' ' } == "{" == false && programLine.trim { it <= ' ' } == "}" == false) {
                        mProgramList!!.add(newProgram_Table.program_Line_Description)
                        mProgramExplanationList!!.add(programLine)
                    }
                }
            }

        }

        val programSize = mProgramList!!.size
        var optionList: ArrayList<String>? = null
        quizModels = ArrayList<QuizModel>()
        for (questionIndex in 0..programSize - 1) {
            val quizModel = QuizModel()
            quizModel.questionIndex = questionIndex + 1
            quizModel.question = mProgramList!![questionIndex].trim { it <= ' ' }
            quizModel.correctOption = mProgramTableList!![questionIndex].program_Line
            //Get Options List
            optionList = getOptionsList(questionIndex, programSize)
            //Shuffle Options
            mShuffleProgramList = ShuffleList.shuffleList(optionList)
            quizModel.optionsList = mShuffleProgramList
            quizModels!!.add(quizModel)
        }

        quizRecyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        quizRecyclerAdapter = QuizRecyclerAdapter(context, quizModels!!, mProgramExplanationList!!, object : QuizRecyclerAdapter.CustomQuizAdapterListner {
            override fun onOptionSelected(position: Int, option: String) {

                quizRecyclerAdapter!!.notifyItemChanged(position)
            }
        })
        quizRecyclerView!!.adapter = quizRecyclerAdapter
        time = (programSize / 2 * 60 * 1000).toLong()
        interval = 1000
        circular_progress_bar!!.max = (time / 1000).toInt()

        mCountDownTimer = object : CountDownTimer(time, interval) {

            override fun onTick(millisUntilFinished: Long) {
                mTimerBtn!!.text = "" + String.format("%d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
                progressTextView!!.text = mTimerBtn!!.text
                remainingTime = time - millisUntilFinished
                circular_progress_bar!!.progress = (remainingTime / 1000).toInt()
            }

            override fun onFinish() {

                mTimerBtn!!.text = "Time up"
                mTimerBtn!!.visibility = View.VISIBLE
                progressLayout!!.visibility = View.GONE
                if (mWizard == true) {
                    mTimerBtn!!.text = "Next"
                    mTimerBtn!!.isEnabled = true
                    mTimerBtn!!.setOnClickListener(mNextBtnClickListener)
                }
                checkScore(programSize)
            }
        }

        mCheckSolutionBtn!!.setOnClickListener { showConfirmSubmitDialog(programSize, mCountDownTimer!!) }

        if (mInvokeMode != ProgrammingBuddyConstants.KEY_LESSON) {
            progressLayout!!.visibility = View.VISIBLE
            mCountDownTimer!!.start()
        }


    }

    internal var mNextBtnClickListener: View.OnClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.timerButton -> navigateToMatchMaker()
        }
    }


    protected fun checkScore(programSize: Int) {
        var score = 0

        quizRecyclerAdapter!!.setAnswerChecked(true)
        var i = 0
        for (quizModel in quizModels!!) {
            if (quizModel.selectedOption == mProgramExplanationList!![i++]) {
                score++
            }
        }

        var message: String? = null
        when (score) {

            0 -> message = "Your Score is $score/$programSize in " + String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                    TimeUnit.MILLISECONDS.toSeconds(remainingTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))) + ", Good Luck next time"

            else -> message = "Your Score is $score/$programSize in " + String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                    TimeUnit.MILLISECONDS.toSeconds(remainingTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))) + ", Keep Working.."
        }

        if (score == programSize) {
            message = "Your Score is $score/$programSize in " + String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                    TimeUnit.MILLISECONDS.toSeconds(remainingTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))) + ", Fantastic Work..!!"
        }

        AuxilaryUtils.displayResultAlert(activity, "Quiz Complete", message, score, programSize)
        quizComplete = true

        if (moduleDetailsScrollPageListener != null) moduleDetailsScrollPageListener!!.toggleFABDrawable()

        if (mWizard == true) {
            mTimerBtn!!.text = "Next"
            mTimerBtn!!.isEnabled = true
            mTimerBtn!!.visibility = View.VISIBLE
            progressLayout!!.visibility = View.GONE
            mTimerBtn!!.setOnClickListener(mNextBtnClickListener)
        }
        mCheckSolutionBtn!!.isEnabled = false
    }

    protected fun navigateToMatchMaker() {
        val newIntentBundle = Bundle()
        newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, program_index)
        newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true)
        newIntentBundle.putParcelableArrayList(ProgrammingBuddyConstants.KEY_USER_PROGRAM, bundle!!.getParcelableArrayList<Parcelable>(ProgrammingBuddyConstants.KEY_USER_PROGRAM))
        wizardNavigationListener!!.loadMatchMakerFragment(bundle)
    }

    private fun getOptionsList(questionIndex: Int, programSize: Int): ArrayList<String> {
        val optionList = ArrayList<String>()
        for (optionIndex in 0..3) {
            optionList.add(mProgramExplanationList!![(questionIndex + optionIndex) % programSize])
        }
        return optionList
    }

    override fun onDetach() {
        super.onDetach()
        if (mCountDownTimer != null)
            mCountDownTimer!!.cancel()
        wizardNavigationListener = null
    }

    internal var quizComplete = false

    fun onBackPressed() {
        if (quizComplete == false) {
            AuxilaryUtils.showConfirmationDialog(activity)

        } else {
            activity.finish()
        }

    }

    private var wizardNavigationListener: WizardNavigationListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is WizardNavigationListener) {
            wizardNavigationListener = context
        }
    }

    override fun updateUI(program_TableList: ArrayList<ProgramTable>) {

        if (mCountDownTimer != null) {
            mCountDownTimer!!.cancel()
            mTimerBtn!!.text = "00:00"
            mCheckSolutionBtn!!.isEnabled = true
        }
        initUI(program_TableList)
    }


    private fun showConfirmSubmitDialog(programSize: Int, countDownTimer: CountDownTimer) {
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Yes") { dialog, which ->
            checkScore(programSize)
            countDownTimer.cancel()
        }

        builder.setNegativeButton("No") { dialog, which ->
            //dialog.dismiss();
        }

        builder.setMessage("Are you sure you want to submit the Quiz?")
        builder.setTitle(activity.title)
        builder.setIcon(android.R.drawable.ic_dialog_info)
        builder.show()

    }

    override fun updateUI() {
        ProgramFetcherTask(context, mProgramIndex).execute()
    }

    override fun isTestComplete(): Int {
        return if (quizComplete) ProgrammingBuddyConstants.KEY_QUIZ else -1
    }

    fun setModuleDetailsScrollPageListener(moduleDetailsScrollPageListener: ModuleDetailsScrollPageListener) {
        this.moduleDetailsScrollPageListener = moduleDetailsScrollPageListener
    }
}

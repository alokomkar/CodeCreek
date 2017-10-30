package com.sortedqueue.programmercreek.fragments

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import com.google.firebase.database.DatabaseError
import com.google.gson.Gson
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.activity.ProgramListActivity
import com.sortedqueue.programmercreek.adapter.MatchOptionsDragAdapter
import com.sortedqueue.programmercreek.adapter.MatchQuestionsDropAdapter
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener
import com.sortedqueue.programmercreek.interfaces.WizardNavigationListener
import com.sortedqueue.programmercreek.util.AnimationUtils
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekAnalytics

import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList
import java.util.Collections

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok Omkar on 2017-08-14.
 */

class NewMatchFragment : Fragment(), View.OnClickListener, TestCompletionListener {

    @BindView(R.id.questionRecyclerView)
    internal var questionRecyclerView: RecyclerView? = null
    @BindView(R.id.optionRecyclerView)
    internal var optionRecyclerView: RecyclerView? = null
    internal var mProgramTableList: List<ProgramTable> ?= null
    @BindView(R.id.checkButton)
    internal var checkButton: Button? = null
    @BindView(R.id.optionsTextView)
    internal var optionsTextView: TextView? = null
    private var newProgramActivityBundle: Bundle? = null
    private var mInvokeMode: Int = 0
    private var program_TableList: ArrayList<ProgramTable>? = null
    private var mProgramIndex: ProgramIndex? = null
    private var mWizard: Boolean = false
    private var mProgramQuestionList: ArrayList<ProgramTable>? = null
    private var mOptionsList: ArrayList<String>? = null
    private var matchQuestionsAdapter: MatchQuestionsDropAdapter? = null
    private var matchOptionsAdapter: MatchOptionsDragAdapter? = null
    private var quizComplete: Boolean = false
    private var solutionList: ArrayList<String>? = null
    private var moduleDetailsScrollPageListener: ModuleDetailsScrollPageListener? = null
    private val TAG = NewMatchFragment::class.java.simpleName


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_new_match, container, false)
        ButterKnife.bind(this, fragmentView)
        return fragmentView
    }

    fun setBundle(bundle: Bundle) {
        this.newProgramActivityBundle = bundle
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkButton!!.setOnClickListener(this)
        mInvokeMode = newProgramActivityBundle!!.getInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST, -1)
        program_TableList = newProgramActivityBundle!!.getParcelableArrayList<ProgramTable>(ProgrammingBuddyConstants.KEY_USER_PROGRAM)
        showHelperDialog()
        if (program_TableList != null && program_TableList!!.size > 0) {
            mProgramIndex = newProgramActivityBundle!!.get(ProgrammingBuddyConstants.KEY_PROG_ID) as ProgramIndex
            mWizard = newProgramActivityBundle!!.getBoolean(ProgramListActivity.KEY_WIZARD, false)
            initUI(program_TableList)
        } else {
            if (mInvokeMode == ProgrammingBuddyConstants.KEY_LESSON) {
                mWizard = false
                FirebaseDatabaseHandler(context).getProgramIndexInBackGround(newProgramActivityBundle!!.getInt(ProgrammingBuddyConstants.KEY_PROG_ID),
                        object : FirebaseDatabaseHandler.GetProgramIndexListener {
                            override fun onSuccess(programIndex: ProgramIndex) {
                                mProgramIndex = programIndex
                                getProgramTables()
                            }

                            override fun onError(databaseError: DatabaseError) {
                                CommonUtils.displayToast(context, R.string.unable_to_fetch_data)
                            }
                        })
            } else {
                mProgramIndex = newProgramActivityBundle!!.get(ProgrammingBuddyConstants.KEY_PROG_ID) as ProgramIndex
                mWizard = newProgramActivityBundle!!.getBoolean(ProgramListActivity.KEY_WIZARD, false)

                getProgramTables()
            }
        }


    }

    private fun showHelperDialog() {
        AuxilaryUtils.displayInformation(context, R.string.match_maker, R.string.match_maker_new_description, DialogInterface.OnDismissListener { })
    }

    private fun getProgramTables() {
        FirebaseDatabaseHandler(context).getProgramTablesInBackground(mProgramIndex!!.program_index, object : FirebaseDatabaseHandler.GetProgramTablesListener {
            override fun onSuccess(programTables: ArrayList<ProgramTable>) {
                program_TableList = programTables
                initUI(program_TableList)
            }

            override fun onError(databaseError: DatabaseError?) {
                CommonUtils.displaySnackBar(activity, R.string.unable_to_fetch_data)

            }
        })
    }

    private fun initUI(program_TableList: ArrayList<ProgramTable>?) {
        if (mProgramIndex != null) {
            try {
                CreekAnalytics.logEvent(TAG, JSONObject(Gson().toJson(mProgramIndex)))
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
        questionRecyclerView!!.visibility = View.INVISIBLE
        optionRecyclerView!!.visibility = View.INVISIBLE
        optionsTextView!!.visibility = View.INVISIBLE
        solutionList = ArrayList<String>()
        for (programTable in program_TableList!!) {
            solutionList!!.add(programTable.program_Line)
        }

        mProgramTableList = program_TableList

        if (program_TableList != null && program_TableList.size > 0) {
            //TODO
            activity.title = "Match : " + mProgramIndex!!.program_Description
            mProgramQuestionList = ProgramTable.getMatchList(program_TableList) { optionsList -> mOptionsList = optionsList }
            for (programTable in mProgramQuestionList!!) {
                Log.d("Match", "programTable : " + programTable)
            }
            for (solution in mOptionsList!!) {
                Log.d("Match", "solution : " + solution)
            }

        }

        Collections.shuffle(mOptionsList!!)

        questionRecyclerView!!.layoutManager = LinearLayoutManager(context)
        if (mOptionsList!!.size <= 4) {
            optionRecyclerView!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
        } else {
            //optionRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false));
            optionRecyclerView!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
        }

        matchQuestionsAdapter = MatchQuestionsDropAdapter(mProgramQuestionList!!)
        questionRecyclerView!!.adapter = matchQuestionsAdapter
        matchOptionsAdapter = MatchOptionsDragAdapter(mOptionsList!!)
        optionRecyclerView!!.adapter = matchOptionsAdapter

        Handler().postDelayed({
            AnimationUtils.slideInToRight(questionRecyclerView)
            AnimationUtils.slideInToLeft(optionRecyclerView)
            AnimationUtils.slideInToLeft(optionsTextView)
        }, 400)

    }

    private var wizardNavigationListener: WizardNavigationListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is WizardNavigationListener) {
            wizardNavigationListener = context
        }
    }

    override fun onClick(view: View) {
        if (checkButton!!.text.toString().equals("Check", ignoreCase = true)) {
            val programTables = matchQuestionsAdapter!!.programList
            for (i in solutionList!!.indices) {
                programTables!![i].isCorrect = solutionList!![i].trim { it <= ' ' } == programTables[i].program_Line.trim { it <= ' ' }
                program_TableList!![i].program_Line = solutionList!![i]
            }
            quizComplete = true
            matchQuestionsAdapter!!.setChecked(true, programTables!!)
            if (moduleDetailsScrollPageListener != null) {
                moduleDetailsScrollPageListener!!.toggleFABDrawable()
            }
            if (mWizard) {
                checkButton!!.text = "Next"
            } else
                checkButton!!.text = "Finish"
        } else {
            if (!mWizard) {
                activity.finish()
            } else {
                val newIntentBundle = Bundle()
                newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, mProgramIndex)
                newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true)
                newIntentBundle.putParcelableArrayList(ProgrammingBuddyConstants.KEY_USER_PROGRAM, newProgramActivityBundle!!.getParcelableArrayList<Parcelable>(ProgrammingBuddyConstants.KEY_USER_PROGRAM))
                Log.d("MatchFragment", "Preference Language : " + CreekApplication.creekPreferences!!.programLanguage)
                if (program_TableList!!.size <= 15) {
                    wizardNavigationListener!!.loadTestFragment(newIntentBundle)
                } else {
                    wizardNavigationListener!!.loadFillBlanksFragment(newIntentBundle)
                }
            }

        }

    }

    fun getmProgramList(): ArrayList<String> {
        return solutionList!!
    }

    fun onBackPressed() {
        if (!quizComplete) {
            AuxilaryUtils.showConfirmationDialog(activity)
        } else {
            activity.finish()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun isTestComplete(): Int {
        return if (quizComplete) ProgrammingBuddyConstants.KEY_MATCH else -1
    }

    fun setModuleDetailsScrollPageListener(moduleDetailsScrollPageListener: ModuleDetailsScrollPageListener) {
        this.moduleDetailsScrollPageListener = moduleDetailsScrollPageListener
    }
}

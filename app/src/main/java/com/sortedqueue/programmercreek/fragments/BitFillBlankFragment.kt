package com.sortedqueue.programmercreek.fragments

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.MatchOptionsDragAdapter
import com.sortedqueue.programmercreek.adapter.MatchQuestionsDropAdapter
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.database.lessons.BitModule
import com.sortedqueue.programmercreek.interfaces.LessonNavigationListener
import com.sortedqueue.programmercreek.interfaces.OnBackPressListener
import com.sortedqueue.programmercreek.util.AnimationUtils
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekAnalytics
import com.startapp.android.publish.adsCommon.Ad
import com.startapp.android.publish.adsCommon.StartAppAd
import com.startapp.android.publish.adsCommon.VideoListener
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener

import java.util.ArrayList
import java.util.Collections

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok on 31/08/17.
 */

class BitFillBlankFragment : Fragment(), View.OnClickListener, FirebaseDatabaseHandler.ConfirmUserProgram {

    @BindView(R.id.questionRecyclerView)
    internal var questionRecyclerView: RecyclerView? = null
    @BindView(R.id.optionsTextView)
    internal var optionsTextView: TextView? = null
    @BindView(R.id.optionRecyclerView)
    internal var optionRecyclerView: RecyclerView? = null
    @BindView(R.id.checkButton)
    internal var checkButton: Button? = null
    @BindView(R.id.buttonLayout)
    internal var buttonLayout: LinearLayout? = null
    @BindView(R.id.hintButton)
    internal var hintButton: Button? = null
    @BindView(R.id.resultTextView)
    internal var resultTextView: TextView? = null
    @BindView(R.id.proceedTextView)
    internal var proceedTextView: TextView? = null
    @BindView(R.id.resultLayout)
    internal var resultLayout: RelativeLayout? = null
    private var mProgramTableList: ArrayList<ProgramTable>? = null
    private var mProgramQuestionList: ArrayList<ProgramTable>? = null
    private var mOptionsList: ArrayList<String>? = null
    private var matchQuestionsAdapter: MatchQuestionsDropAdapter? = null
    private var matchOptionsAdapter: MatchOptionsDragAdapter? = null
    private var quizComplete: Boolean = false
    private var isAnswered: Boolean = false
    private var creekUserStats: CreekUserStats? = null

    private var programLangauge: String? = null
    private var moduleId: String? = null
    private var bitModule: BitModule? = null
    private var onBackPressListener: OnBackPressListener? = null
    private var lessonNavigationListener: LessonNavigationListener? = null
    private val TAG = BitFillBlankFragment::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_bit_fill_blank, container, false)
        ButterKnife.bind(this, fragmentView)
        return fragmentView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CreekAnalytics.logEvent(TAG, "Fill Blanks - Bits and Bytes")
        bitModule = arguments.getParcelable<BitModule>("BitModule")
        programLangauge = bitModule!!.programLanguage
        moduleId = bitModule!!.moduleId
        checkButton!!.setOnClickListener(this)
        hintButton!!.setOnClickListener(this)
        proceedTextView!!.setOnClickListener(this)
        showHelperDialog()
        //lessonNavigationListener.onProgessStatsUpdate(CreekUserStats.PROGRAM_SCORE);
        FirebaseDatabaseHandler(context).compileSharedProgram(bitModule!!.code, this)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is LessonNavigationListener) {
            lessonNavigationListener = context
        }
    }

    private fun showHelperDialog() {
        AuxilaryUtils.displayInformation(context, R.string.fill_blanks, R.string.match_maker_new_description, DialogInterface.OnDismissListener { })
    }

    private var solutionList: ArrayList<String>? = null

    private fun initUI(program_TableList: ArrayList<ProgramTable>?) {
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
        optionRecyclerView!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)

        matchQuestionsAdapter = MatchQuestionsDropAdapter(mProgramQuestionList!!, true)
        questionRecyclerView!!.adapter = matchQuestionsAdapter
        matchOptionsAdapter = MatchOptionsDragAdapter(mOptionsList!!)
        optionRecyclerView!!.adapter = matchOptionsAdapter

        Handler().postDelayed({
            AnimationUtils.slideInToRight(questionRecyclerView)
            AnimationUtils.slideInToLeft(optionRecyclerView)
            AnimationUtils.slideInToLeft(optionsTextView)
        }, 400)

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.checkButton -> if (checkButton!!.text.toString().equals("Check", ignoreCase = true)) {
                val programTables = matchQuestionsAdapter!!.programList
                var rightAnswers = 0
                for (i in solutionList!!.indices) {
                    programTables!![i].isCorrect = solutionList!![i].trim { it <= ' ' } == programTables[i].program_Line.trim { it <= ' ' }
                    if (programTables[i].isChoice && programTables[i].isCorrect)
                        rightAnswers++
                    //program_TableList.get(i).setProgram_Line(solutionList.get(i));
                }
                quizComplete = true
                matchQuestionsAdapter!!.setChecked(true, programTables!!)
                isAnswered = true
                var message = ""
                if (rightAnswers == mOptionsList!!.size) {
                    message = "Congratulations, you've got it"
                } else if (rightAnswers < mOptionsList!!.size / 2) {
                    message = "You need improvement, try again"
                } else {
                    message = "Almost perfect, you are few steps away, retry"
                }
                isAnswered = rightAnswers == mOptionsList!!.size
                resultTextView!!.text = message + ". You scored : " + rightAnswers + "/" + mOptionsList!!.size
                AnimationUtils.slideInToLeft(resultLayout)
                updateCreekStats()
                lessonNavigationListener!!.onProgessStatsUpdate(CreekUserStats.PROGRAM_SCORE)
                proceedTextView!!.text = "Okay"
                if (isAnswered) {
                    proceedTextView!!.text = "Proceed"
                    checkButton!!.text = "Finish"
                }
            } else {
                onBackPressListener!!.onBackPressed()
            }
            R.id.hintButton -> if (!CreekApplication.creekPreferences!!.isPremiumUser) {
                AuxilaryUtils.displayInformation(context, R.string.hint_video, R.string.reward_video_description,
                        DialogInterface.OnClickListener { dialogInterface, i -> showRewardedClick() },

                        DialogInterface.OnDismissListener { showRewardedClick() })
            } else {
                matchQuestionsAdapter!!.enableHints()
            }

            R.id.proceedTextView -> {
                if (proceedTextView!!.text.toString().equals("Proceed", ignoreCase = true)) {
                    onBackPressListener!!.onBackPressed()
                }
                AnimationUtils.slideOutToLeft(resultLayout)
            }
        }

    }

    private fun showRewardedClick() {
        val rewardedVideo = StartAppAd(context)

        /**
         * This is very important: set the video listener to be triggered after video
         * has finished playing completely
         */
        rewardedVideo.setVideoListener { matchQuestionsAdapter!!.enableHints() }

        /**
         * Load rewarded by specifying AdMode.REWARDED
         * We are using AdEventListener to trigger ad show
         */
        rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, object : AdEventListener {

            override fun onReceiveAd(arg0: Ad) {
                rewardedVideo.showAd()
            }

            override fun onFailedToReceiveAd(arg0: Ad) {
                /**
                 * Failed to load rewarded video:
                 * 1. Check that FullScreenActivity is declared in AndroidManifest.xml:
                 * See https://github.com/StartApp-SDK/Documentation/wiki/Android-InApp-Documentation#activities
                 * 2. Is android API level above 16?
                 */
                Log.e("MainActivity", "Failed to load rewarded video with reason: " + arg0.errorMessage)
            }
        })
    }

    private fun updateCreekStats() {
        creekUserStats = CreekApplication.instance.creekUserStats
        when (programLangauge!!.toLowerCase()) {
        /*case "c":
                creekUserStats.addToUnlockedCProgramIndexList(mProgramIndex.getProgram_index() + 1);
                break;
            case "cpp":
            case "c++":
                creekUserStats.addToUnlockedCppProgramIndexList(mProgramIndex.getProgram_index() + 1);
                break;*/
            "java" -> creekUserStats!!.addToUnlockedJavaBitsAndBytesIndexList(moduleId)
        }/*case "usp":
                creekUserStats.addToUnlockedUspProgramIndexList(mProgramIndex.getProgram_index() + 1);
                break;*/
        FirebaseDatabaseHandler(context).writeCreekUserStats(creekUserStats!!)
    }

    override fun onSuccess(programIndex: ProgramIndex, programTables: ArrayList<ProgramTable>) {
        initUI(programTables)
        CommonUtils.dismissProgressDialog()
    }

    override fun onError(errorMessage: String) {
        CommonUtils.dismissProgressDialog()
    }

    fun setOnBackPressListener(onBackPressListener: OnBackPressListener) {
        this.onBackPressListener = onBackPressListener
    }
}

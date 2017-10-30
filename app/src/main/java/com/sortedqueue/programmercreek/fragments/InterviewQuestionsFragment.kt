package com.sortedqueue.programmercreek.fragments

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CodeEditorRecyclerAdapter
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.adapter.InterviewQuestionsAdapter
import com.sortedqueue.programmercreek.asynctask.SlideContentReaderTask
import com.sortedqueue.programmercreek.database.InterviewQuestionModel
import com.sortedqueue.programmercreek.database.OptionModel
import com.sortedqueue.programmercreek.interfaces.InterviewNavigationListener
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.SimpleItemTouchHelperCallback

import java.util.ArrayList



import com.sortedqueue.programmercreek.constants.TYPE_MULTIPLE_RIGHT
import com.sortedqueue.programmercreek.constants.TYPE_REARRANGE
import com.sortedqueue.programmercreek.constants.TYPE_SINGLE_RIGHT
import com.sortedqueue.programmercreek.constants.TYPE_TRUE_FALSE


/**
 * Created by Alok Omkar on 2017-03-08.
 */

class InterviewQuestionsFragment : Fragment(), SlideContentReaderTask.OnDataReadListener, CustomProgramRecyclerViewAdapter.AdapterClickListner {

    @BindView(R.id.questionTextView)
    internal var questionTextView: TextView? = null
    @BindView(R.id.optionsRecyclerView)
    internal var optionsRecyclerView: RecyclerView? = null
    @BindView(R.id.questionLayout)
    internal var questionLayout: LinearLayout? = null
    @BindView(R.id.lifeLine1ImageView)
    internal var lifeLine1ImageView: ImageView? = null
    @BindView(R.id.lifeLine2ImageView)
    internal var lifeLine2ImageView: ImageView? = null
    @BindView(R.id.lifeLine3ImageView)
    internal var lifeLine3ImageView: ImageView? = null
    @BindView(R.id.lifeLineLayout)
    internal var lifeLineLayout: RelativeLayout? = null
    @BindView(R.id.progressTextView)
    internal var progressTextView: TextView? = null
    @BindView(R.id.codeRecyclerView)
    internal var codeRecyclerView: RecyclerView? = null
    @BindView(R.id.timerProgressBar)
    internal var timerProgressBar: ProgressBar? = null
    @BindView(R.id.checkAnswerImageView)
    internal var checkAnswerImageView: TextView? = null

    private var interviewQuestionModels: ArrayList<InterviewQuestionModel>? = null
    private var interviewQuestionModel: InterviewQuestionModel? = null
    private var interviewQuestionsAdapter: InterviewQuestionsAdapter? = null
    private var mCountDownTimer: CountDownTimer? = null
    private var mInterviewNavigationListener: InterviewNavigationListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_interview_questions, container, false)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkAnswerImageView!!.setOnClickListener { checkAnswer() }
        interviewQuestionModels = ArrayList<InterviewQuestionModel>()
        getAllInterviewModels(programLanguage!!)

    }

    private var index = 0

    private fun getAllInterviewModels(programLanguage: String) {
        /*setupMultiRightModel();
        setupRearrangeModel();
        setupSingleRightModel();
        setupTrueFalseModel();*/
        val fileId = "c_questions"
        SlideContentReaderTask(activity, fileId, this).execute()


    }

    private fun startTimer() {
        timerProgressBar!!.isIndeterminate = false
        timerProgressBar!!.max = 59
        timerProgressBar!!.progress = 0
        mCountDownTimer = object : CountDownTimer((60 * 1000).toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                if (progressTextView != null)
                    progressTextView!!.text = "" + (millisUntilFinished / 1000).toInt()
                timerProgressBar!!.progress = (millisUntilFinished / 1000).toInt()
                if (timerProgressBar!!.progress == 1) {
                    timerProgressBar!!.progress = 0
                }
            }

            override fun onFinish() {
                if (progressTextView != null) {
                    progressTextView!!.text = "--"
                }


            }
        }
        mCountDownTimer!!.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelTimer()
    }

    private fun setupMultiRightModel() {
        interviewQuestionModel = InterviewQuestionModel()
        interviewQuestionModel!!.typeOfQuestion = TYPE_MULTIPLE_RIGHT
        val optionModels = ArrayList<OptionModel>()
        var index = 1
        optionModels.add(OptionModel(index++, "int a = 1;"))
        optionModels.add(OptionModel(index++, "char b = \"ava\""))
        optionModels.add(OptionModel(index++, "char[] str = \"abcd\""))
        interviewQuestionModel!!.optionModels = optionModels
        interviewQuestionModel!!.question = "Which are valid?"
        val correctOptions = ArrayList<Int>()
        correctOptions.add(1)
        correctOptions.add(3)
        interviewQuestionModel!!.correctOptions = correctOptions
        interviewQuestionModel!!.modelId = "Model_1"
        interviewQuestionModel!!.programLanguage = "c"
        interviewQuestionModels!!.add(interviewQuestionModel!!)
    }

    private fun setupRearrangeModel() {
        interviewQuestionModel = InterviewQuestionModel()
        interviewQuestionModel!!.typeOfQuestion = TYPE_REARRANGE
        val optionModels = ArrayList<OptionModel>()
        var index = 1
        optionModels.add(OptionModel(index++, "void main() {"))
        optionModels.add(OptionModel(index++, " puts(s);"))
        optionModels.add(OptionModel(index++, " int s = 0;"))
        optionModels.add(OptionModel(index++, "}"))
        val correctSequence = ArrayList<Int>()
        correctSequence.add(1)
        correctSequence.add(3)
        correctSequence.add(2)
        correctSequence.add(4)
        interviewQuestionModel!!.correctSequence = correctSequence
        interviewQuestionModel!!.optionModels = optionModels
        interviewQuestionModel!!.question = "Arrange in right sequence"
        interviewQuestionModel!!.correctOption = 1
        interviewQuestionModel!!.modelId = "Model_1"
        interviewQuestionModel!!.programLanguage = "c"
        interviewQuestionModels!!.add(interviewQuestionModel!!)
    }

    private fun setupSingleRightModel() {
        interviewQuestionModel = InterviewQuestionModel()
        interviewQuestionModel!!.typeOfQuestion = TYPE_SINGLE_RIGHT
        val optionModels = ArrayList<OptionModel>()
        var index = 1
        optionModels.add(OptionModel(index++, "True"))
        optionModels.add(OptionModel(index++, "False"))
        optionModels.add(OptionModel(index++, "No Idea"))
        interviewQuestionModel!!.optionModels = optionModels
        interviewQuestionModel!!.question = "Is it True that !true is false?"
        interviewQuestionModel!!.correctOption = 1
        interviewQuestionModel!!.modelId = "Model_1"
        interviewQuestionModel!!.programLanguage = "c"
        interviewQuestionModels!!.add(interviewQuestionModel!!)
    }

    private fun setupTrueFalseModel() {
        interviewQuestionModel = InterviewQuestionModel()
        interviewQuestionModel!!.typeOfQuestion = TYPE_TRUE_FALSE
        val optionModels = ArrayList<OptionModel>()
        var index = 1
        optionModels.add(OptionModel(index++, "True"))
        optionModels.add(OptionModel(index++, "False"))
        interviewQuestionModel!!.optionModels = optionModels
        interviewQuestionModel!!.question = "Is it True that !true is false?"
        interviewQuestionModel!!.correctOption = 1
        interviewQuestionModel!!.modelId = "Model_1"
        interviewQuestionModel!!.programLanguage = "c"
        interviewQuestionModels!!.add(interviewQuestionModel!!)
    }

    private fun hideShowLifeLine() {
        lifeLineLayout!!.visibility = View.GONE
        /*int visibility = interviewQuestionModel.getOptionModels().size() == 2
                ? View.GONE
                : View.VISIBLE;
        lifeLineLayout.setVisibility(visibility);*/
    }

    private fun setupViews() {
        questionTextView!!.text = interviewQuestionModel!!.question
        if (interviewQuestionModel!!.code != null) {
            codeRecyclerView!!.visibility = View.VISIBLE
            codeRecyclerView!!.layoutManager = LinearLayoutManager(context)
            codeRecyclerView!!.adapter = CodeEditorRecyclerAdapter(context, AuxilaryUtils.splitProgramIntolines(interviewQuestionModel!!.code), programLanguage!!)
        } else {
            codeRecyclerView!!.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        optionsRecyclerView!!.layoutManager = LinearLayoutManager(context)
        interviewQuestionsAdapter = InterviewQuestionsAdapter(interviewQuestionModel!!, this)
        optionsRecyclerView!!.adapter = interviewQuestionsAdapter
        if (interviewQuestionModel!!.typeOfQuestion == TYPE_REARRANGE) {
            val callback = SimpleItemTouchHelperCallback(interviewQuestionsAdapter!!)
            val touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(optionsRecyclerView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    fun checkAnswer() {

        interviewQuestionsAdapter!!.isAnswerChecked(true)
        cancelTimer()
        //mInterviewNavigationListener.showExplanation("Some explanation");
        if (null != interviewQuestionModel!!.explanation) {
            // mInterviewNavigationListener.showExplanation(interviewQuestionModel.getExplanation());
            navigateToNext()
        } else {
            if (interviewQuestionModel!!.typeOfQuestion != TYPE_MULTIPLE_RIGHT
                    && interviewQuestionModel!!.typeOfQuestion != TYPE_REARRANGE ) {
                navigateToNext()
            } else {
                Handler().postDelayed({ navigateToNext() }, 2500)
            }

        }

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToNext();
            }
        }, 2500);*/
    }

    fun navigateToNext() {
        if (index < interviewQuestionModels!!.size) {
            interviewQuestionModel = interviewQuestionModels!![index++]
            setupViews()
            setupRecyclerView()
            hideShowLifeLine()
            startTimer()
        } else {
            activity.finish()
        }

    }

    private fun cancelTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer!!.cancel()
        }
    }

    private var programLanguage: String? = null

    fun setProgramLanguage(programLanguage: String) {
        this.programLanguage = programLanguage
    }

    override fun onDataReadComplete(contentArrayList: ArrayList<InterviewQuestionModel>) {
        interviewQuestionModels = contentArrayList
        setupMultiRightModel()
        setupRearrangeModel()
        setupSingleRightModel()
        setupTrueFalseModel()
        navigateToNext()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is InterviewNavigationListener) {
            mInterviewNavigationListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mInterviewNavigationListener = null
    }

    override fun onItemClick(position: Int) {
        if (interviewQuestionModel!!.typeOfQuestion != TYPE_MULTIPLE_RIGHT
                && interviewQuestionModel!!.typeOfQuestion != TYPE_REARRANGE) {
            cancelTimer()
            /*if (null != interviewQuestionModel.getExplanation()) {
                mInterviewNavigationListener.showExplanation(interviewQuestionModel.getExplanation());
            }*/
        }
    }
}
package com.sortedqueue.programmercreek.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CodeEditorRecyclerAdapter
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.adapter.SubTopicsQuestionAdapter
import com.sortedqueue.programmercreek.database.InterviewQuestionModel
import com.sortedqueue.programmercreek.database.OptionModel
import com.sortedqueue.programmercreek.database.SubTopics
import com.sortedqueue.programmercreek.interfaces.OnBackPressListener
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CreekPreferences
import com.sortedqueue.programmercreek.util.SimpleItemTouchHelperCallback
import kotlinx.android.synthetic.main.fragment_subtopic_question.*

import java.util.ArrayList
import java.util.regex.Pattern





/**
 * Created by Alok on 06/10/17.
 */

class SubTopicQuestionFragment : Fragment(), CustomProgramRecyclerViewAdapter.AdapterClickListner {

    private var programLanguage: String? = null
    private var subTopic: SubTopics? = null
    private var subTopicsQuestionAdapter: SubTopicsQuestionAdapter? = null
    private var onBackPressListener: OnBackPressListener? = null
    var correctAnswers = 0
        private set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_subtopic_question, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkAnswerImageView!!.setOnClickListener { checkAnswer() }
        questionTextView!!.text = subTopic!!.question

        if (subTopic!!.questionCode != null) {
            codeRecyclerView!!.layoutManager = LinearLayoutManager(context)
            codeRecyclerView!!.adapter = CodeEditorRecyclerAdapter(context!!, AuxilaryUtils.splitProgramIntolines(subTopic!!.questionCode), programLanguage!!)
        }

        optionsRecyclerView!!.layoutManager = LinearLayoutManager(context)
        subTopicsQuestionAdapter = SubTopicsQuestionAdapter(getOptions(subTopic!!.options), subTopic!!.testMode, this)
        subTopicsQuestionAdapter!!.setCorrectAnswers(subTopic!!.answer)
        optionsRecyclerView!!.adapter = subTopicsQuestionAdapter

        if (subTopic!!.testMode == "rearrange") {
            val callback = SimpleItemTouchHelperCallback(subTopicsQuestionAdapter!!)
            val touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(optionsRecyclerView)
        }

    }

    private fun getOptions(options: String): ArrayList<OptionModel> {
        val optionModels = ArrayList<OptionModel>()
        var optionId = 1
        for (option in options.split(Pattern.quote("|||").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            optionModels.add(OptionModel(optionId++, option))
        }
        return optionModels
    }


    private fun checkAnswer() {

        if (checkAnswerImageView!!.text == "Check") {
            subTopicsQuestionAdapter!!.isAnswerChecked(true)
            checkAnswerImageView!!.text = "Finish"
            correctAnswers = subTopicsQuestionAdapter!!.countCorrectAnswers()
        } else {
            val creekPreferences = CreekApplication.creekPreferences
            creekPreferences!!.setUnlockedSubTopic(subTopic!!.subTopicId)
            onBackPressListener!!.onBackPressed()
        }

    }

    fun setProgramLanguage(programLanguage: String) {
        this.programLanguage = programLanguage
    }

    fun setSubTopic(subTopic: SubTopics) {
        this.subTopic = subTopic
    }

    override fun onItemClick(position: Int) {

    }

    fun setOnBackPressListener(onBackPressListener: OnBackPressListener) {
        this.onBackPressListener = onBackPressListener
    }

    fun callBackClick() {
        onBackPressListener!!.onBackPressed()
    }
}

package com.sortedqueue.programmercreek.v2.ui.module

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.SimpleItemTouchHelperCallback
import com.sortedqueue.programmercreek.v2.base.BaseFragment
import com.sortedqueue.programmercreek.v2.base.hide
import com.sortedqueue.programmercreek.v2.data.helper.SimpleContent
import kotlinx.android.synthetic.main.fragment_module_questions.*
import kotlin.collections.ArrayList

class ModuleQuestionsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle? ): View?
            = inflater.inflate(R.layout.fragment_module_questions, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val simpleContent = arguments!!.getParcelable<SimpleContent>(SimpleContent::class.java.simpleName)
        initViews( simpleContent )
    }

    private fun initViews(simpleContent: SimpleContent?) {

        tvQuestion.text = simpleContent!!.getQuestion()

        if( simpleContent.contentType == SimpleContent.codeMcq ) {
            codeQuestionEditor.setText( simpleContent.getCode() )
        }
        else codeQuestionEditor.hide()

        rvOptions.layoutManager = LinearLayoutManager( context )
        val adapter = OptionsRvAdapter( simpleContent.contentType, simpleContent.getQuestionOptions(), simpleContent.getCorrectOptions() )
        rvOptions.adapter = adapter
        tvCheck.setOnClickListener { adapter.isAnswerChecked(true) }
        if( simpleContent.contentType == SimpleContent.rearrange ) {
            val correctOrder = AuxilaryUtils.splitProgramIntolines(simpleContent.contentString.split("?")[1].trim())
            val shuffledList = ArrayList<String>()
            shuffledList.addAll(correctOrder)
            shuffledList.shuffle()
            val optionsAdapter = OptionsRvAdapter( simpleContent.contentType, shuffledList, correctOrder )
            rvOptions.adapter = optionsAdapter
            val callback = SimpleItemTouchHelperCallback(optionsAdapter)
            val touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(rvOptions)
            tvCheck.setOnClickListener { optionsAdapter.isAnswerChecked(true) }
        }
    }
}
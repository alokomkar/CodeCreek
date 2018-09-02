package com.sortedqueue.programmercreek.v2.ui.module

import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.SimpleItemTouchHelperCallback
import com.sortedqueue.programmercreek.v2.base.BaseAdapterClickListener
import com.sortedqueue.programmercreek.v2.base.BaseFragment
import com.sortedqueue.programmercreek.v2.base.hide
import com.sortedqueue.programmercreek.v2.base.show
import com.sortedqueue.programmercreek.v2.data.helper.SimpleContent
import kotlinx.android.synthetic.main.fragment_module_questions.*
import java.util.*

class ModuleQuestionsFragment : BaseFragment(), BaseAdapterClickListener<String> {


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle? ): View?
            = inflater.inflate(R.layout.fragment_module_questions, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val simpleContent = arguments!!.getParcelable<SimpleContent>(SimpleContent::class.java.simpleName)
        rvOptions.layoutManager = LinearLayoutManager( context )
        initViews( simpleContent )
    }

    private fun initViews(simpleContent: SimpleContent?) {

        if( simpleContent != null ) {

            fillLayout.hide()
            tvQuestion.hide()
            codeView.hide()
            codeQuestionEditor.hide()
            scroll_view.hide()

            if( simpleContent.contentType == SimpleContent.fillBlanks ) {
                tvFillQuestion.text = simpleContent.getFillBlanksQuestion()
                fillLayout.show()
                ivBack.setOnClickListener { tvFillQuestion.text = simpleContent.getFillBlanksQuestion() }
            }
            else {
                tvQuestion.text = simpleContent.getQuestion()
                tvQuestion.show()
            }

            if( simpleContent.contentType == SimpleContent.codeMcq ) {
                codeQuestionEditor.setText( simpleContent.getCode() )
                codeQuestionEditor.show()
                scroll_view.show()
            }
            else if( simpleContent.contentType == SimpleContent.syntaxLearn ) {
                codeView.show()
                codeView.setCode(simpleContent.getCode(), "java")
            }

            val questionsList = simpleContent.getQuestionOptions()
            questionsList.shuffle()
            val adapter = OptionsRvAdapter( simpleContent.contentType, questionsList, simpleContent.getCorrectOptions(), this )
            rvOptions.adapter = adapter

            tvCheck.setOnClickListener {
                ivBack.isEnabled = false
                adapter.isAnswerChecked(true)
                if( simpleContent.contentType == SimpleContent.fillBlanks ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tvFillQuestion.text = Html.fromHtml(simpleContent.checkAnswer( tvFillQuestion.text.toString() ), Html.FROM_HTML_MODE_LEGACY)
                    }
                    else tvFillQuestion.text = Html.fromHtml(simpleContent.checkAnswer( tvFillQuestion.text.toString() ))
                }
            }

            if( simpleContent.contentType == SimpleContent.rearrange ) {
                val correctOrder = AuxilaryUtils.splitProgramIntolines(simpleContent.contentString.split("?")[1].trim())
                val shuffledList = ArrayList<String>()
                shuffledList.addAll(correctOrder)
                shuffledList.shuffle()
                val optionsAdapter = OptionsRvAdapter( simpleContent.contentType, shuffledList, correctOrder, this )
                rvOptions.adapter = optionsAdapter
                val callback = SimpleItemTouchHelperCallback(optionsAdapter)
                val touchHelper = ItemTouchHelper(callback)
                touchHelper.attachToRecyclerView(rvOptions)
                tvCheck.setOnClickListener {
                    ivBack.isEnabled = false
                    optionsAdapter.isAnswerChecked(true)
                }
            }
        }

    }

    override fun onItemClick(position: Int, item: String) {
        var fillText = tvFillQuestion.text.toString()
        fillText = fillText.replaceFirst("<________>", "<$item>")
        tvFillQuestion.text = fillText
    }
}
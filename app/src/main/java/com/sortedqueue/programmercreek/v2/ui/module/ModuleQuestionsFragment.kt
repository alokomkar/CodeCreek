package com.sortedqueue.programmercreek.v2.ui.module

import android.annotation.SuppressLint
import android.graphics.Color
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
import com.sortedqueue.programmercreek.v2.base.*
import com.sortedqueue.programmercreek.v2.data.helper.SimpleContent
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme
import kotlinx.android.synthetic.main.fragment_module_questions.*
import java.util.*

@Suppress("DEPRECATION") //HTML legacy support deprecation
@SuppressLint("SetTextI18n")
class ModuleQuestionsFragment : BaseQuestionsFragment(), BaseAdapterClickListener<String> {

    private val solutionList = ArrayList<String>()

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
            tvOutput.hide()

            if( simpleContent.contentType == SimpleContent.fillBlanks ) {
                tvFillQuestion.text = simpleContent.getFillBlanksQuestion()
                fillLayout.show()
                ivBack.setOnClickListener { tvFillQuestion.text = simpleContent.getFillBlanksQuestion() }
            }
            else {
                tvQuestion.text = simpleContent.getQuestion()
                tvFillBlanks.text = simpleContent.getQuestion()

                if( simpleContent.contentType == SimpleContent.syntaxLearn ) {
                    fillLayout.show()
                    tvQuestion.hide()
                    tvFillQuestion.hide()
                }
                else tvQuestion.show()
            }

            if( simpleContent.contentType == SimpleContent.codeMcq ) {
                codeQuestionEditor.setText( simpleContent.getCode() )
                codeQuestionEditor.show()
                scroll_view.show()
            }
            else if( simpleContent.contentType == SimpleContent.syntaxLearn ) {
                codeView.show()
                tvOutput.show()
                codeView.setCode("", "java")
                tvOutput.text = simpleContent.getSyntaxOutput()
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
                onProgressStatsUpdate(45)
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
                    onProgressStatsUpdate(55)
                }
            }
            if( simpleContent.contentType == SimpleContent.syntaxLearn ) {
                solutionList.clear()
                val correctOrder = simpleContent.getSyntaxOptions()
                val shuffledList = ArrayList<String>()
                shuffledList.addAll(correctOrder)
                shuffledList.shuffle()
                val optionsAdapter = OptionsRvAdapter( simpleContent.contentType, shuffledList, correctOrder, this )
                rvOptions.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                rvOptions.adapter = optionsAdapter
                tvCheck.setOnClickListener {
                    ivBack.isEnabled = false
                    optionsAdapter.isAnswerChecked(true)
                    checkSolution(simpleContent.getSyntax(), simpleContent.getSyntaxOutput())
                    onProgressStatsUpdate(65)
                }
                ivBack.setOnClickListener {
                    if( solutionList.isNotEmpty() ) {
                        solutionList.removeAt(solutionList.size - 1)
                        val solution = getSolution( solutionList )
                        codeView.setCode( solution )
                    }
                }
            }

        }

    }

    private fun getSolution(solutionList: ArrayList<String>): String {
        var solution = ""
        for (solutionString in solutionList) {
            solution += "$solutionString "
        }
        return solution
    }

    override fun onItemClick(position: Int, item: String) {
        if( codeView.isVisible() ) {
            solutionList.add(item)
            val solution = getSolution( solutionList )
            syntaxSolutionTextView.text = solution
            codeView
                    .setOptions(Options.get(context!!)
                            .withLanguage("java")
                            .withCode(syntaxSolutionTextView.text.toString())
                            .withTheme(ColorTheme.DEFAULT))
        }
        else {
            var fillText = tvFillQuestion.text.toString()
            fillText = fillText.replaceFirst("<________>", "<$item>")
            tvFillQuestion.text = fillText
        }

    }

    private fun checkSolution(syntax: String, syntaxOutput: String) {
        val solutionText = getSolution(solutionList)
        if (solutionText.trim { it <= ' ' }.replace("\\s+".toRegex(), "") == syntax.trim { it <= ' ' }.replace("\\s+".toRegex(), "")) {
            tvOutput.text = syntaxOutput
            tvOutput.setTextColor(Color.GREEN)
         } else {
            tvOutput.text = "Error..!!"
            tvOutput.setTextColor(Color.RED)
        }
    }
}
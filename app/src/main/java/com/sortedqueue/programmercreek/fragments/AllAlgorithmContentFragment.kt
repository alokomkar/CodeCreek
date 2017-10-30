package com.sortedqueue.programmercreek.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.AlgorithmContent



import com.sortedqueue.programmercreek.constants.CONTENT_AIM_DESCRIPTION
import com.sortedqueue.programmercreek.constants.CONTENT_ALGORITHM
import com.sortedqueue.programmercreek.constants.CONTENT_CODE
import com.sortedqueue.programmercreek.constants.CONTENT_OUTPUT
import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme
import kotlinx.android.synthetic.main.fragment_all_content_algorithm.*

/**
 * Created by Alok Omkar on 2017-03-18.
 */

class AllAlgorithmContentFragment : Fragment() {

    private var algorithmContent: AlgorithmContent? = null
    private var programLanguage: String? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_all_content_algorithm, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        titleTextView!!.visibility = View.GONE
        contentTextView!!.visibility = View.GONE
        algorithmsTextView!!.visibility = View.GONE
        algorithmCodeView!!.visibility = View.GONE
        outputTextView!!.visibility = View.GONE
        algorithmNestedScrollview!!.visibility = View.GONE

        when (algorithmContent!!.contentType) {
            CONTENT_AIM_DESCRIPTION -> {
                algorithmNestedScrollview!!.visibility = View.VISIBLE
                titleTextView!!.visibility = View.VISIBLE
                contentTextView!!.visibility = View.VISIBLE
                titleTextView!!.text = algorithmContent!!.aim
                contentTextView!!.text = algorithmContent!!.programDescription
            }
            CONTENT_ALGORITHM -> {
                algorithmNestedScrollview!!.visibility = View.VISIBLE
                algorithmsTextView!!.visibility = View.VISIBLE
                algorithmsTextView!!.text = algorithmContent!!.algorithmPseudoCode
            }
            CONTENT_CODE -> {
                algorithmCodeView!!.visibility = View.VISIBLE
                algorithmCodeView!!.setOptions(Options.get(context)
                        .withLanguage(programLanguage!!)
                        .withCode(algorithmContent!!.programCode)
                        .withTheme(ColorTheme.SOLARIZED_LIGHT))
            }
            CONTENT_OUTPUT -> {
                algorithmNestedScrollview!!.visibility = View.VISIBLE
                outputTextView!!.visibility = View.VISIBLE
                outputTextView!!.text = algorithmContent!!.output
            }
        }
    }

    fun setParameter(algorithmContent: AlgorithmContent, programLanguage: String) {
        this.algorithmContent = algorithmContent
        this.programLanguage = programLanguage
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}

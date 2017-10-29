package com.sortedqueue.programmercreek.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.interfaces.InterviewNavigationListener

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok Omkar on 2017-03-08.
 */

class InterviewChoiceFragment : Fragment(), View.OnClickListener {

    @BindView(R.id.cQuestionsTextView)
    internal var cQuestionsTextView: TextView? = null
    @BindView(R.id.cppQuestionsTextView)
    internal var cppQuestionsTextView: TextView? = null
    @BindView(R.id.javaQuestionsTextView)
    internal var javaQuestionsTextView: TextView? = null
    @BindView(R.id.sqlQuestionsTextView)
    internal var sqlQuestionsTextView: TextView? = null
    private var interviewNavigationListener: InterviewNavigationListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_interview_choice, container, false)
        ButterKnife.bind(this, view)
        setListeners()
        return view
    }

    private fun setListeners() {
        cppQuestionsTextView!!.setOnClickListener(this)
        cQuestionsTextView!!.setOnClickListener(this)
        javaQuestionsTextView!!.setOnClickListener(this)
        sqlQuestionsTextView!!.setOnClickListener(this)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is InterviewNavigationListener) {
            interviewNavigationListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        interviewNavigationListener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onClick(view: View) {
        if (view is TextView) {
            val language = view.text.toString().replace("questions", "").trim { it <= ' ' }
            if (interviewNavigationListener != null) {
                interviewNavigationListener!!.onNavigateToQuestions(language)
            }
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var indexFragment: InterviewChoiceFragment? = null
        val instance: InterviewChoiceFragment
            get() {
                if (indexFragment == null) {
                    indexFragment = InterviewChoiceFragment()
                }
                return indexFragment!!
            }
    }
}

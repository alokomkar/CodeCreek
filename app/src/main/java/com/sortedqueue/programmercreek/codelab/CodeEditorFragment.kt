package com.sortedqueue.programmercreek.codelab

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.database.CodeShortCuts
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.view.CodeEditor
import kotlinx.android.synthetic.main.fragment_editor.*

import java.util.ArrayList

/**
 * Created by Alok on 14/09/17.
 */

class CodeEditorFragment : Fragment(), CodeEditor.OnTextChangedListener, CodeLabView {

    private var codeLabPresenter = CodeLabPresenter(this)

    override fun onCreateView( inflater: LayoutInflater?, container: ViewGroup?, state: Bundle?): View? {
        return inflater!!.inflate( R.layout.fragment_editor, container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkFAB.setOnClickListener {
            outputDividerView.visibility = View.GONE
            outputTextView.visibility = View.GONE
            codeLabPresenter.executeCode("", editor.text.toString()) }
        codeLabPresenter.getCodeShortCutsForLanguage(CreekApplication.getPreferences().programLanguage)
    }

    override fun showProgress(messageId: Int) {
        CommonUtils.displayProgressDialog(context, messageId)
    }

    override fun hideProgress() {
        CommonUtils.dismissProgressDialog()
    }

    override fun onError(error: String) {
        CommonUtils.dismissProgressDialog()
        CommonUtils.displayToast(context, error)
    }

    override fun getCodeShortCuts(codeShortCuts: ArrayList<CodeShortCuts>, codeBody: String ) {
        editor!!.setOnTextChangedListener(this)
        editor!!.setText(codeBody)
        codeShortCutsRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)

        codeShortCutsRecyclerView!!.adapter = CodeShortCutsAdapter(codeShortCuts,
                object : CustomProgramRecyclerViewAdapter.AdapterClickListner {
                    override fun onItemClick(position: Int) {
                        val start = Math.max(editor!!.selectionStart, 0)
                        val end = Math.max(editor!!.selectionEnd, 0)
                        editor!!.text.insert(Math.min(start, end), codeShortCuts[position].value)
                    }
                })
    }

    override fun startCodeExecuteAnimation() {
        compilerProgressLayout.visibility = View.VISIBLE
        val animation = AlphaAnimation(1f, 0f)
        animation.duration = 800
        animation.interpolator = LinearInterpolator()
        animation.repeatCount = Animation.INFINITE
        animation.repeatMode = Animation.REVERSE
        progressImageView.startAnimation(animation)
    }

    override fun onTextChanged(text: String) {
        if (!CreekApplication.creekPreferences!!.doesRunOnChange()) {
            return
        }

        if (editor!!.hasErrorLine()) {
            editor!!.setErrorLine(0)
            editor!!.updateHighlighting()
        }
    }

    override fun onResume() {
        super.onResume()
        updateToPreferences()
    }

    private fun updateToPreferences() {
        val preferences = CreekApplication.creekPreferences

        editor!!.setUpdateDelay(preferences!!.updateDelay)

        editor!!.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                preferences.textSize.toFloat())

        editor!!.setTabWidth(preferences.tabWidth)
    }

    override fun stopCodeExecuteAnimation() {
        progressImageView.clearAnimation()
        compilerProgressLayout.visibility = View.GONE
    }

    override fun onOutputSuccess(output: String) {
        outputDividerView.visibility = View.VISIBLE
        outputTextView.visibility = View.VISIBLE
        outputTextView.text = output
        outputTextView.setTextColor(Color.GREEN)
    }

    override fun onOutputError(error: String) {
        outputDividerView.visibility = View.VISIBLE
        outputTextView.visibility = View.VISIBLE
        outputTextView.text = error
        outputTextView.setTextColor(Color.RED)
    }
}

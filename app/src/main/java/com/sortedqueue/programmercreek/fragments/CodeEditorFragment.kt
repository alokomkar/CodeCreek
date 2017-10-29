package com.sortedqueue.programmercreek.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CodeShortCutsAdapter
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.database.CodeShortCuts
import com.sortedqueue.programmercreek.util.CreekPreferences
import com.sortedqueue.programmercreek.view.CodeEditor

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * Created by Alok on 14/09/17.
 */

class CodeEditorFragment : Fragment(), CodeEditor.OnTextChangedListener {


    @BindView(R.id.editor)
    internal var editor: CodeEditor? = null
    @BindView(R.id.scroll_view)
    internal var scrollView: ScrollView? = null
    internal var unbinder: Unbinder ?= null
    @BindView(R.id.codeShortCutsRecyclerView)
    internal var codeShortCutsRecyclerView: RecyclerView? = null

    override fun onCreateView(
            inflater: LayoutInflater?,
            container: ViewGroup?,
            state: Bundle?): View? {
        val view = inflater!!.inflate(
                R.layout.fragment_editor,
                container,
                false)
        unbinder = ButterKnife.bind(this, view)
        setupViews()
        return view
    }

    private fun setupViews() {
        editor!!.setOnTextChangedListener(this)
        editor!!.setText("#include \"stdio.h\"\n" + "#include \"conio.h\"")
        codeShortCutsRecyclerView!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
        val codeShortCuts = ArrayList<CodeShortCuts>()
        codeShortCuts.add(CodeShortCuts("{}", "{\n\n}"))
        codeShortCuts.add(CodeShortCuts("TAB", "    "))
        codeShortCuts.add(CodeShortCuts(";", ";"))
        codeShortCuts.add(CodeShortCuts("++", "++"))
        codeShortCuts.add(CodeShortCuts("--", "--"))
        codeShortCuts.add(CodeShortCuts("<", "<"))
        codeShortCuts.add(CodeShortCuts(">", ">"))
        codeShortCuts.add(CodeShortCuts("()", "()"))
        codeShortCuts.add(CodeShortCuts("main", "void main()\n{\n\n\n}"))
        codeShortCuts.add(CodeShortCuts("int main", "int main()\n{\n\n\nreturn0;\n}"))
        codeShortCuts.add(CodeShortCuts("do_while", "do{ \n\n }while();"))
        codeShortCuts.add(CodeShortCuts("for_loop", "for( ; ; ){\n\n}"))
        codeShortCuts.add(CodeShortCuts("if", "if(  ){\n\n}"))
        codeShortCuts.add(CodeShortCuts("else", "else{\n\n}"))
        codeShortCuts.add(CodeShortCuts("else_if", "else{\n\n}"))
        codeShortCuts.add(CodeShortCuts("printf", "printf(\"\");"))
        codeShortCuts.add(CodeShortCuts("scanf", "scanf(\"\");"))
        codeShortCuts.add(CodeShortCuts("stdio", "#include \"stdio.h\""))
        codeShortCuts.add(CodeShortCuts("conio", "#include \"conio.h\""))


        codeShortCutsRecyclerView!!.adapter = CodeShortCutsAdapter(codeShortCuts,
                object : CustomProgramRecyclerViewAdapter.AdapterClickListner {
            override fun onItemClick(position: Int) {
                val start = Math.max(editor!!.selectionStart, 0)
                val end = Math.max(editor!!.selectionEnd, 0)
                editor!!.text.insert(Math.min(start, end), codeShortCuts[position].value)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
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
}

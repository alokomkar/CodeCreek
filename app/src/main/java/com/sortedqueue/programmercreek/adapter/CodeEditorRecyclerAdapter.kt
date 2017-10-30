package com.sortedqueue.programmercreek.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.util.PrettifyHighlighter

import java.util.ArrayList




/**
 * Created by Alok on 12/04/17.
 */
class CodeEditorRecyclerAdapter : RecyclerView.Adapter<CodeEditorRecyclerAdapter.ViewHolder> {

    private var context: Context? = null
    var programLines: ArrayList<String>? = null
        private set
    private var prettifyHighlighter: PrettifyHighlighter? = null
    private var programLanguage: String? = null
    private var editableIndex = -1
    private val TAG = CodeEditorRecyclerAdapter::class.java.simpleName
    private var codeMode = false

    constructor(context: Context, programLines: ArrayList<String>, programLanguage: String) {
        this.context = context
        this.programLines = programLines
        this.programLanguage = programLanguage
        this.prettifyHighlighter = PrettifyHighlighter.instance
    }

    constructor(context: Context, programLines: ArrayList<String>, programLanguage: String, codeMode: Boolean) {
        this.context = context
        this.codeMode = codeMode
        this.programLines = programLines
        this.programLanguage = programLanguage
        this.prettifyHighlighter = PrettifyHighlighter.instance
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                if (codeMode)
                    R.layout.item_code
                else
                    R.layout.item_edit_code, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val programLine = programLines!![position]
        //holder.codeEditText.setEnabled(position == editableIndex);
        if (programLine.contains("<") || programLine.contains(">")) {
            holder.codeEditText!!.setText(programLine)
            holder.codeEditText!!.setTextColor(Color.parseColor("#006699"))
        } else {
            if (Build.VERSION.SDK_INT >= 24) {
                holder.codeEditText!!.setText(Html.fromHtml(prettifyHighlighter!!.highlight(programLanguage!!, programLine), Html.FROM_HTML_MODE_LEGACY))
            } else {
                holder.codeEditText!!.setText(Html.fromHtml(prettifyHighlighter!!.highlight(programLanguage!!, programLine)))
            }
        }
    }

    override fun getItemCount(): Int {
        return programLines!!.size
    }

    val code: String
        get() {
            var code = ""
            for (programLine in programLines!!) {
                code += programLine + "\n"
            }
            return code
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, TextWatcher {

        internal var codeEditText: EditText = itemView.findViewById(R.id.codeEditText) as EditText

        init {

            codeEditText!!.isEnabled = false
            codeEditText!!.setOnClickListener(this)
            codeEditText!!.addTextChangedListener(this)
        }

        override fun onClick(v: View) {

        }


        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            editableIndex = adapterPosition
        }

        override fun afterTextChanged(s: Editable) {
            if (editableIndex == -1) {
                return
            }
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val programLine = s.toString().trim { it <= ' ' }
                Log.d(TAG, "Setting line : " + programLine)
                if (programLine.trim { it <= ' ' }.isNotEmpty()) {
                    programLines!![position] = programLine
                } else {
                    programLines!![position] = ""
                }
            }

        }
    }

}

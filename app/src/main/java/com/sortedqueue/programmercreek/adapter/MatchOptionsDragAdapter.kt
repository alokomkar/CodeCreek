package com.sortedqueue.programmercreek.adapter

import android.content.ClipData
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.util.PrettifyHighlighter

import java.util.ArrayList




/**
 * Created by Alok Omkar on 2017-08-14.
 */
class MatchOptionsDragAdapter(private val mProgramList: ArrayList<String>) : RecyclerView.Adapter<MatchOptionsDragAdapter.ViewHolder>() {
    private var mSelectedProgramLineView: View? = null
    private val mPrettifyHighlighter: PrettifyHighlighter

    init {
        mPrettifyHighlighter = PrettifyHighlighter.instance
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_option_question, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var programLine = mProgramList[position]
        if (programLine.contains("<") || programLine.contains(">")) {
            holder.questionTextView!!.text = programLine
            holder.questionTextView!!.setTextColor(Color.parseColor("#006699"))
        } else {
            programLine = mPrettifyHighlighter.highlight("c", programLine)
            if (Build.VERSION.SDK_INT >= 24) {
                holder.questionTextView!!.text = Html.fromHtml(programLine, Html.FROM_HTML_MODE_LEGACY)
            } else {
                holder.questionTextView!!.text = Html.fromHtml(programLine)
            }
        }
    }

    override fun getItemCount(): Int {
        return mProgramList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener, View.OnTouchListener {
        @BindView(R.id.questionTextView)
        internal var questionTextView: TextView? = null
        @BindView(R.id.matchQuestionLayout)
        internal var matchQuestionLayout: LinearLayout? = null

        init {
            ButterKnife.bind(this, itemView)
            questionTextView!!.setOnLongClickListener(this)
        }

        override fun onLongClick(view: View): Boolean {
            view.setOnTouchListener(this)
            view.findViewById(R.id.questionTextView).setBackgroundResource(R.drawable.selected)
            mSelectedProgramLineView = view
            //Toast.makeText(getContext(), "Selected", Toast.LENGTH_SHORT).show();

            //To start drag immediately after a view has been selected.
            val data = ClipData.newPlainText("", "")
            val shadowBuilder = View.DragShadowBuilder(view)
            view.startDrag(data, shadowBuilder, view, 0)

            return false
        }

        override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                /*
				 * Drag details: we only need default behavior
				 * - clip data could be set to pass data as part of drag
				 * - shadow can be tailored
				 */
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(view)
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0)
                return true
            } else {
                return false
            }
        }
    }
}

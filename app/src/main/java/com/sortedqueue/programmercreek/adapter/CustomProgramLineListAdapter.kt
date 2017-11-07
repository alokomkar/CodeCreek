package com.sortedqueue.programmercreek.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.util.PrettifyHighlighter

import java.util.ArrayList


class CustomProgramLineListAdapter(private val mContext: Context, resource: Int, textViewResourceId: Int,
                                   private val mProgramLineList: ArrayList<String>, private val isExplanation: Boolean) : ArrayAdapter<String>(mContext, resource, textViewResourceId, mProgramLineList) {
    internal var highlighter = PrettifyHighlighter.instance
    internal var highlighted: String? = null

    init {
        mLayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return mProgramLineList.size
    }

    override fun getItem(position: Int): String? {
        return mProgramLineList[position]
    }

    override fun getItemId(itemId: Int): Long {
        return itemId.toLong()
    }

    internal var mViewHolder: ViewHolder? = null
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View ?= convertView
        if (convertView == null) {
            view = mLayoutInflater!!.inflate(R.layout.program_list, null)
            mViewHolder = ViewHolder()
            mViewHolder!!.programLineTextView = view.findViewById(R.id.progamLineTxtView)
            view.tag = mViewHolder
        } else {
            mViewHolder = view!!.tag as ViewHolder
        }
        val programLine = mProgramLineList[position]
        if (isExplanation) {
            mViewHolder!!.programLineTextView!!.text = programLine
            return view!!
        } else {
            if (programLine.contains("<") || programLine.contains(">")) {
                mViewHolder!!.programLineTextView!!.text = programLine
                mViewHolder!!.programLineTextView!!.setTextColor(Color.parseColor("#006699"))
                return view!!
            }

            highlighted = highlighter.highlight("c", " " + mProgramLineList[position])
            if (Build.VERSION.SDK_INT >= 24) {
                mViewHolder!!.programLineTextView!!.text = Html.fromHtml(highlighted, Html.FROM_HTML_MODE_LEGACY)
            } else {
                mViewHolder!!.programLineTextView!!.text = Html.fromHtml(highlighted)
            }


            return view!!
        }

    }

    internal class ViewHolder {
        var programLineTextView: TextView? = null
    }

    companion object {
        private var mLayoutInflater: LayoutInflater? = null
    }

}

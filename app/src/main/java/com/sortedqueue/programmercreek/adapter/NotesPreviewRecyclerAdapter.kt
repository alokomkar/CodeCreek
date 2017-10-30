package com.sortedqueue.programmercreek.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.NotesModel
import com.sortedqueue.programmercreek.util.PrettifyHighlighter

import java.util.ArrayList




/**
 * Created by Alok Omkar on 2017-07-29.
 */
class NotesPreviewRecyclerAdapter(private val notesModelArrayList: ArrayList<NotesModel>) : RecyclerView.Adapter<NotesPreviewRecyclerAdapter.ViewHolder>() {
    private val prettifyHighlighter: PrettifyHighlighter

    init {
        this.prettifyHighlighter = PrettifyHighlighter.instance
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_notes_preview, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notesModel = notesModelArrayList[position]
        holder.headerTextView!!.visibility = View.GONE
        holder.contentTextView!!.visibility = View.GONE
        holder.codeTextView!!.visibility = View.GONE
        when (notesModel.noteType) {
            NotesModel.TYPE_CODE -> if (notesModel.noteLine.contains("<") || notesModel.noteLine.contains(">")) {
                holder.codeTextView!!.text = notesModel.noteLine
                holder.codeTextView!!.setTextColor(Color.parseColor("#006699"))
                holder.codeTextView!!.visibility = View.VISIBLE

            } else {
                holder.codeTextView!!.text = Html.fromHtml(prettifyHighlighter.highlight("c", notesModel.noteLine))
                holder.codeTextView!!.visibility = View.VISIBLE
            }
            NotesModel.TYPE_HEADER -> {
                holder.headerTextView!!.text = notesModel.noteLine
                holder.headerTextView!!.visibility = View.VISIBLE
            }
            NotesModel.TYPE_NOTES -> {
                holder.contentTextView!!.text = notesModel.noteLine
                holder.contentTextView!!.visibility = View.VISIBLE
            }
        }
    }


    override fun getItemCount(): Int {
        return notesModelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.headerTextView)
        internal var headerTextView: TextView? = null
        @BindView(R.id.contentTextView)
        internal var contentTextView: TextView? = null
        @BindView(R.id.codeTextView)
        internal var codeTextView: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}

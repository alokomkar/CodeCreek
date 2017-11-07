package com.sortedqueue.programmercreek.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.NotesModel

import java.util.ArrayList




import com.sortedqueue.programmercreek.database.NotesModel.TYPE_CODE
import com.sortedqueue.programmercreek.database.NotesModel.TYPE_HEADER
import com.sortedqueue.programmercreek.database.NotesModel.TYPE_NOTES

/**
 * Created by Alok on 24/08/17.
 */

class ArticleShareAdaper(val notesModelArrayList: ArrayList<NotesModel>) : RecyclerView.Adapter<ArticleShareAdaper.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleShareAdaper.ViewHolder {
        return ViewHolder(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_article, parent, false))
    }

    override fun onBindViewHolder(holder: ArticleShareAdaper.ViewHolder, position: Int) {
        val notesModel = getItemAtPosition(position)
        holder.notesTextView!!.text = notesModel.noteLine
        val noteType = notesModel.noteType
        when (noteType) {
            TYPE_CODE -> {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.md_black_1000))
                holder.notesTextView!!.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            }
            NotesModel.TYPE_HEADER -> {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.md_blue_grey_300))
                holder.notesTextView!!.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            }
            NotesModel.TYPE_NOTES -> {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                holder.notesTextView!!.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorPrimary))
            }
        }
    }

    private fun getItemAtPosition(position: Int): NotesModel {
        return notesModelArrayList[position]
    }

    override fun getItemCount(): Int {
        return notesModelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var notesTextView: TextView = itemView.findViewById(R.id.notesTextView)

        init {

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val notesModel = getItemAtPosition(position)
                val noteType = notesModel.noteType
                when (noteType) {
                    TYPE_CODE -> notesModel.noteType = TYPE_HEADER
                    TYPE_NOTES -> notesModel.noteType = TYPE_CODE
                    TYPE_HEADER -> notesModel.noteType = TYPE_NOTES
                }
                notifyItemChanged(position)
            }
        }
    }

}

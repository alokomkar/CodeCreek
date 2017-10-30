package com.sortedqueue.programmercreek.adapter

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




/**
 * Created by Alok Omkar on 2017-07-28.
 */
class NotesShareRecyclerAdapter(val notesModelArrayList: ArrayList<NotesModel>) : RecyclerView.Adapter<NotesShareRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_notes, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notesModel = getItemAtPosition(position)
        holder.notesTextView!!.text = notesModel.noteLine
        val noteType = notesModel.noteType
        holder.codeRadioButton!!.isChecked = noteType == NotesModel.TYPE_CODE
        holder.headerRadioButton!!.isChecked = noteType == NotesModel.TYPE_HEADER
        holder.notesRadioButton!!.isChecked = noteType == NotesModel.TYPE_NOTES
    }

    private fun getItemAtPosition(position: Int): NotesModel {
        return notesModelArrayList[position]
    }

    override fun getItemCount(): Int {
        return notesModelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), CompoundButton.OnCheckedChangeListener {
        @BindView(R.id.notesTextView)
        internal var notesTextView: TextView? = null
        @BindView(R.id.codeRadioButton)
        internal var codeRadioButton: RadioButton? = null
        @BindView(R.id.notesRadioButton)
        internal var notesRadioButton: RadioButton? = null
        @BindView(R.id.headerRadioButton)
        internal var headerRadioButton: RadioButton? = null

        init {
            ButterKnife.bind(this, itemView)
            codeRadioButton!!.setOnCheckedChangeListener(this)
            notesRadioButton!!.setOnCheckedChangeListener(this)
            headerRadioButton!!.setOnCheckedChangeListener(this)
        }

        override fun onCheckedChanged(compoundButton: CompoundButton, isSelected: Boolean) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION && isSelected) {
                val notesModel = getItemAtPosition(position)
                when (compoundButton.id) {
                    R.id.codeRadioButton -> notesModel.noteType = NotesModel.TYPE_CODE
                    R.id.notesRadioButton -> notesModel.noteType = NotesModel.TYPE_NOTES
                    R.id.headerRadioButton -> notesModel.noteType = NotesModel.TYPE_HEADER
                }

            }

        }
    }

}

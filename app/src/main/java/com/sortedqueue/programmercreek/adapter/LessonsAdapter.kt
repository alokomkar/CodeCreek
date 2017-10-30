package com.sortedqueue.programmercreek.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.lessons.Lesson
import org.w3c.dom.Text

import java.util.ArrayList




/**
 * Created by Alok on 29/08/17.
 */

class LessonsAdapter(private val context: Context, private val lessons: ArrayList<Lesson>, private val adapterClickListener: CustomProgramRecyclerViewAdapter.AdapterClickListner) : RecyclerView.Adapter<LessonsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_lesson, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lesson = getItem(position)
        holder.lessonTitleTextView!!.text = lesson.title
        holder.programLanguageTextView!!.text = lesson.programLanguage.toUpperCase()
    }

    override fun getItemCount(): Int {
        return lessons.size
    }

    fun getItem(position: Int): Lesson {
        return lessons[position]
    }


    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var programLanguageTextView: TextView = itemView.findViewById(R.id.programLanguageTextView) as TextView
        internal var lessonTitleTextView: TextView = itemView.findViewById(R.id.lessonTitleTextView) as TextView

        init {

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != -1)
                adapterClickListener.onItemClick(position)
        }
    }
}

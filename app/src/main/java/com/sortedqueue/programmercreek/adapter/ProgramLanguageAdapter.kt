package com.sortedqueue.programmercreek.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.ProgramLanguage

import java.util.ArrayList




/**
 * Created by Alok on 27/01/17.
 */

class ProgramLanguageAdapter(private val context: Context, private val programLanguages: ArrayList<ProgramLanguage>, private val adapterClickListner: CustomProgramRecyclerViewAdapter.AdapterClickListner) : RecyclerView.Adapter<ProgramLanguageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_language, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val programLanguage = programLanguages[position]
        holder.programmingTextView!!.text = programLanguage.programLanguage
        holder.programLanguageDescriptionTextView!!.text = programLanguage.description
        holder.languageIdTextView!!.text = programLanguage.languageId
        //startAnimation(holder.itemView, position * 250 );
    }

    private fun startAnimation(itemView: View, delay: Int) {
        itemView.alpha = 0.0f
        itemView.animate().setStartDelay(delay.toLong()).setDuration(250).alpha(1.0f)
    }


    override fun getItemCount(): Int {
        return programLanguages.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        internal var programmingTextView: TextView? = itemView.findViewById(R.id.programmingTextView) as TextView

        internal var progressBar: ProgressBar? = itemView.findViewById(R.id.progressBar) as ProgressBar

        internal var programLanguageDescriptionTextView: TextView? = itemView.findViewById(R.id.programLanguageDescriptionTextView) as TextView

        internal var languageIdTextView: TextView? = itemView.findViewById(R.id.languageIdTextView) as TextView


        init {

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                adapterClickListner.onItemClick(position)
            }
        }
    }
}

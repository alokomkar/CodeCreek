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

        @BindView(R.id.programmingTextView)
        internal var programmingTextView: TextView? = null
        @BindView(R.id.progressBar)
        internal var progressBar: ProgressBar? = null
        @BindView(R.id.programLanguageDescriptionTextView)
        internal var programLanguageDescriptionTextView: TextView? = null
        @BindView(R.id.languageIdTextView)
        internal var languageIdTextView: TextView? = null


        init {
            ButterKnife.bind(this, itemView)
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

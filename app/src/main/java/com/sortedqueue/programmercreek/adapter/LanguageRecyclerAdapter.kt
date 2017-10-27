package com.sortedqueue.programmercreek.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.sortedqueue.programmercreek.R

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok Omkar on 2017-04-16.
 */

class LanguageRecyclerAdapter(private val languages: ArrayList<String>,
                              private val adapterClickListner: CustomProgramRecyclerViewAdapter.AdapterClickListner) : RecyclerView.Adapter<LanguageRecyclerAdapter.ViewHolder>() {
    var selectedLanguage = ""
        set(selectedLanguage) {
            field = selectedLanguage
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tag, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val language = languages[position]
        holder.tagTextView!!.text = language
        holder.tagLayout!!.isSelected = this.selectedLanguage == language
    }

    override fun getItemCount(): Int {
        return languages.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        @BindView(R.id.tagTextView)
        internal var tagTextView: TextView? = null
        @BindView(R.id.tagLayout)
        internal var tagLayout: LinearLayout? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                adapterClickListner.onItemClick(position)
            }
        }
    }
}

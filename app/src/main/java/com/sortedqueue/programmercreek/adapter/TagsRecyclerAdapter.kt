package com.sortedqueue.programmercreek.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.sortedqueue.programmercreek.R

import java.util.ArrayList




/**
 * Created by Alok on 13/04/17.
 */
class TagsRecyclerAdapter : RecyclerView.Adapter<TagsRecyclerAdapter.ViewHolder> {


    private var tagArrayList: ArrayList<String>? = null
    var selectedTags: ArrayList<String>? = null
        private set

    private var selectedTag = ""

    private var mode = -1
    private var adapterClickListner: CustomProgramRecyclerViewAdapter.AdapterClickListner?= null

    constructor(tagArrayList: ArrayList<String>) {
        this.tagArrayList = tagArrayList
        this.selectedTags = ArrayList<String>()
    }

    constructor(tagArrayList: ArrayList<String>, mode: Int) {
        this.tagArrayList = tagArrayList
        this.selectedTags = ArrayList<String>()
        this.mode = mode
    }

    constructor(tagArrayList: ArrayList<String>, mode: Int, adapterClickListner: CustomProgramRecyclerViewAdapter.AdapterClickListner) {
        this.tagArrayList = tagArrayList
        this.selectedTags = ArrayList<String>()
        this.adapterClickListner = adapterClickListner
        this.mode = mode
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tag, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val tag = tagArrayList!![position]
        holder.tagTextView!!.text = tag

        if (mode == -1) {
            val isSelected = selectedTags!!.contains(tag)
            holder.tagLayout!!.isSelected = isSelected
        } else {
            val isSelected = tag == selectedTag
            holder.tagLayout!!.isSelected = isSelected
        }

    }

    fun addTag(tag: String) {
        if (tagArrayList!!.contains(tag)) {
            selectedTags!!.add(tag)
            notifyDataSetChanged()
        } else {
            tagArrayList!!.add(tag)
            selectedTags!!.add(tag)
            notifyDataSetChanged()
        }
    }

    fun getSelectedTag(): String {
        return selectedTag
    }

    override fun getItemCount(): Int {
        return tagArrayList!!.size
    }

    fun setSelectedTag(selectedTag: String) {
        this.selectedTag = selectedTag
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        internal var tagTextView: TextView? = itemView.findViewById(R.id.tagTextView)

        internal var tagLayout: LinearLayout? = itemView.findViewById(R.id.tagLayout)

        init {

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val tag = tagArrayList!![position]
                if (mode == -1) {
                    if (selectedTags!!.contains(tag)) {
                        selectedTags!!.remove(tag)
                    } else {
                        selectedTags!!.add(tag)
                    }
                } else {
                    selectedTag = tag
                }
                adapterClickListner?.onItemClick(position)
                notifyDataSetChanged()
            }
        }
    }


}

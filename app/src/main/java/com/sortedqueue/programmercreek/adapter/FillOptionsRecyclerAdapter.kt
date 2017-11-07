package com.sortedqueue.programmercreek.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.fragments.BitModuleFragment

import java.util.ArrayList




/**
 * Created by Alok Omkar on 2017-09-01.
 */

class FillOptionsRecyclerAdapter(private val fillBlankOptions: ArrayList<String>, private val adapterClickListner: CustomProgramRecyclerViewAdapter.AdapterClickListner) : RecyclerView.Adapter<FillOptionsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_code_text, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.codeTextView!!.text = fillBlankOptions[position]
        holder.codeTextView!!.setTextColor(Color.parseColor("#006699"))
    }

    override fun getItemCount(): Int {
        return fillBlankOptions.size
    }

    fun getItem(position: Int): String {
        return fillBlankOptions[position]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var codeTextView: TextView = itemView.findViewById(R.id.codeTextView)

        init {

            codeTextView!!.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                adapterClickListner.onItemClick(position)
            }
        }
    }
}

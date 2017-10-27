package com.sortedqueue.programmercreek.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.AlgorithmsIndex

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok Omkar on 2017-03-17.
 */
class AlgorithmsRecyclerAdapter(context: Context, private val adapterClickListner: CustomProgramRecyclerViewAdapter.AdapterClickListner, private val algorithmsIndexArrayList: ArrayList<AlgorithmsIndex>) : RecyclerView.Adapter<AlgorithmsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_algorithm_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val algorithmsIndex = getItemAtPosition(position)
        holder.descriptionTextView!!.text = algorithmsIndex.programDescription
        holder.titleTextView!!.text = algorithmsIndex.programTitle
    }

    override fun getItemCount(): Int {
        return algorithmsIndexArrayList.size
    }

    fun getItemAtPosition(position: Int): AlgorithmsIndex {
        return algorithmsIndexArrayList[position]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        @BindView(R.id.titleTextView)
        internal var titleTextView: TextView? = null
        @BindView(R.id.descriptionTextView)
        internal var descriptionTextView: TextView? = null

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

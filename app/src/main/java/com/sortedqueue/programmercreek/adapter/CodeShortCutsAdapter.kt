package com.sortedqueue.programmercreek.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.CodeShortCuts

import java.util.ArrayList




/**
 * Created by Alok on 14/09/17.
 */

class CodeShortCutsAdapter(private val mCodeShortCuts: ArrayList<CodeShortCuts>, private val mAdapterClickListner: CustomProgramRecyclerViewAdapter.AdapterClickListner) : RecyclerView.Adapter<CodeShortCutsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_code_shortcut, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.codeTextView!!.text = mCodeShortCuts[position].key
    }

    override fun getItemCount(): Int {
        return mCodeShortCuts.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var codeTextView: TextView = itemView.findViewById(R.id.codeTextView) as TextView

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                mAdapterClickListner.onItemClick(position)
        }
    }

}

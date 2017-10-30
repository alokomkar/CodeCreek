package com.sortedqueue.programmercreek.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.sortedqueue.programmercreek.R



import org.w3c.dom.Text

/**
 * Created by Alok Omkar on 2017-09-01.
 */

class FillCodeRecyclerAdapter(private val codeWords: Array<String>, private val randomIndex: Int) : RecyclerView.Adapter<FillCodeRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_code_text, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.codeTextView!!.text = codeWords[position]
        holder.codeTextView!!.setTextColor(if (position == randomIndex) Color.parseColor("#006699") else Color.parseColor("#000000"))
    }

    fun setCode(option: String) {
        codeWords[randomIndex] = option
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return codeWords.size
    }

    val blankSpace: String
        get() = codeWords[randomIndex]

    val codeLine: String
        get() {
            var codeLine = ""
            for (programLine in codeWords) {
                codeLine += programLine
            }
            return codeLine
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var codeTextView: TextView = itemView.findViewById(R.id.codeTextView) as TextView

        init {

            codeTextView!!.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION && position == randomIndex) {
                codeTextView!!.text = "________"
            }
        }
    }
}

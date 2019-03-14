package com.sortedqueue.programmercreek.v2.ui.module

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.BaseAdapterClickListener
import com.sortedqueue.programmercreek.v2.base.hide
import com.sortedqueue.programmercreek.v2.base.show
import com.sortedqueue.programmercreek.v2.data.helper.SimpleContent
import com.sortedqueue.programmercreek.view.CodeEditor
import java.util.*

@SuppressLint("InflateParams")
class SimpleContentAdapter(private val contentList : ArrayList<SimpleContent>,
                           private val adapterClickListener: BaseAdapterClickListener<SimpleContent> )
    : RecyclerView.Adapter<SimpleContentViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleContentViewHolder
            = SimpleContentViewHolder( parent )

    override fun getItemCount(): Int = contentList.size

    override fun onBindViewHolder(holder: SimpleContentViewHolder, position: Int) {
        holder.onItemClickListener = {

            if( holder.adapterPosition != RecyclerView.NO_POSITION ) {
                adapterClickListener.onItemClick(holder.adapterPosition, contentList[holder.adapterPosition])
                holder.clearAnimation(contentList[holder.adapterPosition].contentType)
            }

        }
        holder.bindData( contentList[position] )
    }

    fun addItem(simpleContent: SimpleContent) {
        contentList.add(simpleContent)
        notifyItemInserted(contentList.size - 1 )
    }
}
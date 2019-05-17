package com.sortedqueue.programmercreek.v2.ui.module

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.sortedqueue.programmercreek.v2.base.BaseAdapter
import com.sortedqueue.programmercreek.v2.base.BaseAdapterClickListener
import com.sortedqueue.programmercreek.v2.data.helper.SimpleContent
import java.util.*

@SuppressLint("InflateParams")
class SimpleContentAdapter(private val adapterClickListener: BaseAdapterClickListener<SimpleContent>)
    : BaseAdapter<SimpleContent, SimpleContentViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleContentViewHolder
            = SimpleContentViewHolder( parent )

    override fun onBindViewHolder(holder: SimpleContentViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.apply {
            onItemClickListener = {
                if( holder.adapterPosition != RecyclerView.NO_POSITION ) {
                    adapterClickListener.onItemClick(holder.adapterPosition, itemsList[holder.adapterPosition])
                    holder.clearAnimation(itemsList[holder.adapterPosition].contentType)
                }
            }
            bindData( itemsList[position] )
        }

    }
}
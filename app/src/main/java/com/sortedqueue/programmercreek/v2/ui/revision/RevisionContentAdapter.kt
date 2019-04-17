package com.sortedqueue.programmercreek.v2.ui.revision

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.sortedqueue.programmercreek.v2.base.BaseAdapterClickListener
import com.sortedqueue.programmercreek.v2.data.helper.SimpleContent
import java.util.*

class RevisionContentAdapter(private val contentList : ArrayList<SimpleContent>,
                           private val adapterClickListener: BaseAdapterClickListener<SimpleContent>)
    : RecyclerView.Adapter<RevisionContentViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RevisionContentViewHolder
            = RevisionContentViewHolder( parent )

    override fun getItemCount(): Int = contentList.size

    override fun onBindViewHolder(holder: RevisionContentViewHolder, position: Int) {
        holder.onItemClickListener = {

            if( holder.adapterPosition != RecyclerView.NO_POSITION ) {
                adapterClickListener.onItemClick(holder.adapterPosition, contentList[holder.adapterPosition])
            }

        }
        holder.bindData( contentList[position] )
    }

}
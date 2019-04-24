package com.sortedqueue.programmercreek.v2.ui.chapters

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.BaseAdapterClickListener
import com.sortedqueue.programmercreek.v2.base.toggleVisibility
import com.sortedqueue.programmercreek.v2.data.model.Chapter
import java.util.ArrayList

class SubModulesAdapter( private val chaptersList: ArrayList<Chapter>,
                         private val layoutId : Int,
                         private val adapterClickListener: BaseAdapterClickListener<Chapter> )
    : RecyclerView.Adapter<SubModulesAdapter.ModuleViewHolder>() {



    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder
            = ModuleViewHolder( LayoutInflater.from( parent.context ).inflate(
            if( layoutId != -1 )
                R.layout.item_sub_module_linear
            else
                R.layout.item_sub_module, null ) )

    override fun getItemCount(): Int
            = chaptersList.size

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        val chapter = chaptersList[position]
        holder.bindData( chapter, position )
    }


    inner class ModuleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val topicTextView: TextView = itemView.findViewById(R.id.topicTextView)
        private val topDividerView : View? = itemView.findViewById(R.id.topDivider)
        private val bottomDividerView : View? = itemView.findViewById(R.id.bottomDivider)
        //private val topicCardView: CardView = itemView.findViewById(R.id.topicCardView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                adapterClickListener.onItemClick( position, chaptersList[position] )
            }
        }

        fun bindData(chapter: Chapter, position: Int) {
            //topicCardView.setCardBackgroundColor(ContextCompat.getColor(topicCardView.context, color))
            topicTextView.text = "${( position + 1 )}. ${chapter.moduleTitle}"
            bottomDividerView?.apply {
                toggleVisibility( adapterPosition != chaptersList.size -1 )
            }
        }
    }
}
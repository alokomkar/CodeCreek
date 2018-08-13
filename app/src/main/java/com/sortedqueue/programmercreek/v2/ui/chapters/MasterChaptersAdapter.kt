package com.sortedqueue.programmercreek.v2.ui.chapters

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.BaseAdapterClickListener
import com.sortedqueue.programmercreek.v2.data.model.Chapter


class MasterChaptersAdapter( private val chapterTitles : ArrayList<String>,
                             private val chaptersMap: LinkedHashMap<String, ArrayList<Chapter>>,
                             private val adapterClickListener: BaseAdapterClickListener<Chapter> )
    : RecyclerView.Adapter<MasterChaptersAdapter.ChapterViewHolder>() {

    private val colors : Array<Int> = arrayOf(
            R.color.md_amber_800,
            R.color.md_cyan_500,
            R.color.md_purple_500,
            R.color.md_brown_700,
            R.color.md_blue_grey_700,
            R.color.md_light_green_900,
            R.color.md_cyan_900,
            R.color.md_deep_orange_900
    )

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder
            = ChapterViewHolder( LayoutInflater.from( parent.context ).inflate( R.layout.item_chapter, null ) )

    override fun getItemCount(): Int = chaptersMap.size

    override fun onBindViewHolder(holderChapter: ChapterViewHolder, position: Int)
        = holderChapter.bindData( chapterTitles[position], chaptersMap[chapterTitles[position]] )


    inner class ChapterViewHolder(itemView : View ) : RecyclerView.ViewHolder( itemView ) {

        private val rvModules : RecyclerView = itemView.findViewById(R.id.rvModules)
        private val tvHeader : TextView = itemView.findViewById(R.id.tvHeader)

        fun bindData( chapterTitle: String, chaptersList: java.util.ArrayList<Chapter>?) {
            tvHeader.text = chapterTitle
            rvModules.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL)
            rvModules.adapter = SubModulesAdapter( chaptersList, colors[adapterPosition % colors.size], adapterClickListener )
        }

    }


}
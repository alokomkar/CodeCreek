package com.sortedqueue.programmercreek.v2.ui.chapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.BaseAdapterClickListener
import com.sortedqueue.programmercreek.v2.base.BaseFragment
import com.sortedqueue.programmercreek.v2.data.model.Chapter
import com.sortedqueue.programmercreek.v2.data.model.Streak
import com.sortedqueue.programmercreek.v2.ui.HomeActivity
import com.sortedqueue.programmercreek.v2.ui.module.ModuleActivity
import kotlinx.android.synthetic.main.fragment_chapters_layout.*
import kotlinx.android.synthetic.main.view_practice_now.*
import java.util.*

class ChaptersFragment : BaseFragment(), BaseAdapterClickListener<Chapter> {

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

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle? ): View?
            = inflater.inflate(R.layout.fragment_chapters_layout, container, false)

    private val chaptersMap = LinkedHashMap<String, ArrayList<Chapter>>()
    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chaptersLayout.removeAllViews()

        val chapterList = Chapter.getAllChapters()
        val chapterTitleList = ArrayList<String>()

        for( chapter in chapterList ) {
           if( !chaptersMap.containsKey(chapter.chapterTitle) ){
                chapterTitleList.add(chapter.chapterTitle)
                chaptersMap[chapter.chapterTitle] = ArrayList()
            }
            chaptersMap[chapter.chapterTitle]?.add(chapter)
        }

        val layoutInflater = LayoutInflater.from(context)
        var position = 0
        for( (key, chaptersList) in chaptersMap ) {
            val chapterView = layoutInflater.inflate(R.layout.item_chapter, null)
            val tvHeader = chapterView.findViewById<TextView>(R.id.tvHeader)
            tvHeader.text = key
            val rvModules = chapterView.findViewById<RecyclerView>(R.id.rvModules)
            rvModules.layoutManager = StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL )
            rvModules.adapter = SubModulesAdapter( chaptersList, -1, this )
            chaptersLayout.addView(chapterView)
            position++
        }

        tvRevise?.setOnClickListener {
            (activity as HomeActivity).practiceNow()
        }

        rvStreak?.apply {
            adapter = StreakAdapter().apply {
                add(Streak(""))
            }
        }

    }

    override fun onItemClick(position: Int, item: Chapter) {
        ModuleActivity.loadChapter( context!!, item, position, chaptersMap[item.chapterTitle]  )
    }
}
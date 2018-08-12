package com.sortedqueue.programmercreek.v2.ui.chapters

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.BaseAdapterClickListener
import com.sortedqueue.programmercreek.v2.base.BaseFragment
import com.sortedqueue.programmercreek.v2.data.model.Chapter
import kotlinx.android.synthetic.main.fragment_chapters.*

class ChaptersFragment : BaseFragment(), BaseAdapterClickListener<Chapter> {


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle? ): View?
            = inflater.inflate(R.layout.fragment_chapters, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        modulesRecyclerView.layoutManager = LinearLayoutManager( context )

        val chapterList = Chapter.getAllChapters()
        val chaptersMap = LinkedHashMap<String, ArrayList<Chapter>>()
        val chapterTitleList = ArrayList<String>()

        for( chapter in chapterList ) {

            if( !chaptersMap.containsKey(chapter.chapterTitle) ){
                chapterTitleList.add(chapter.chapterTitle)
                chaptersMap[chapter.chapterTitle] = ArrayList()
            }

            chaptersMap[chapter.chapterTitle]!!.add(chapter)

        }
        modulesRecyclerView.adapter = MasterChaptersAdapter( chapterTitleList, chaptersMap, this )

    }

    override fun onItemClick(position: Int, item: Chapter) {

    }
}
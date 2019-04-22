package com.sortedqueue.programmercreek.v2.ui.chapters

import android.annotation.SuppressLint
import android.content.ClipData
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.util.AnimationUtils
import com.sortedqueue.programmercreek.v2.base.*
import com.sortedqueue.programmercreek.v2.data.model.Chapter
import com.sortedqueue.programmercreek.v2.data.model.Streak
import com.sortedqueue.programmercreek.v2.ui.HomeActivity
import com.sortedqueue.programmercreek.v2.ui.module.ModuleActivity
import kotlinx.android.synthetic.main.fragment_chapters_layout.*
import kotlinx.android.synthetic.main.view_practice_now.*
import java.util.*

class ChaptersFragment : BaseFragment(), BaseAdapterClickListener<Chapter> {

    private var currentDragView: View? = null
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
            val ivChapterLocked = chapterView.findViewById<View>(R.id.ivChapterLocked)
            val cvChapter = chapterView.findViewById<View>(R.id.cvChapter)
            val tvHeader = chapterView.findViewById<TextView>(R.id.tvHeader)
            val unlockChapterLayout = chapterView.findViewById<View>(R.id.unlockChapterLayout)

            tvHeader.text = key
            val rvModules = chapterView.findViewById<RecyclerView>(R.id.rvModules)
            //rvModules.layoutManager = LinearLayoutManager(context)//StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL )
            rvModules.adapter = SubModulesAdapter( chaptersList, -1, this )
            chaptersLayout.addView(chapterView)

            if( position == 0 ) {
                rvModules.show()
                tvHeader.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        ContextCompat.getDrawable(tvHeader.context,
                                R.drawable.ic_down_arrow),
                        null)
                ivChapterLocked.hide()
                unlockChapterLayout.show()
            }
            else {
                rvModules.hide()
                ivChapterLocked.show()
                unlockChapterLayout.hide()
            }

            unlockChapterLayout.setOnLongClickListener {
                currentDragView = unlockChapterLayout
                unlockChapterLayout.setOnTouchListener { view, event ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        val data = ClipData.newPlainText("", "")
                        val shadowBuilder = View.DragShadowBuilder(view)
                        //start dragging the item touched
                        view.startDragAndDrop(data, shadowBuilder, view, 0)
                        true
                    } else {
                        false
                    }
                }
                //To start drag immediately after a view has been selected.
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, shadowBuilder, it, 0)
                false
            }

            ivChapterLocked.setOnDragListener { v, event ->

                when (event.action) {
                    DragEvent.ACTION_DRAG_STARTED -> {
                    }
                    DragEvent.ACTION_DRAG_ENTERED -> {
                    }
                    DragEvent.ACTION_DRAG_EXITED -> {
                    }
                    DragEvent.ACTION_DROP -> {
                        AnimationUtils.exitRevealGone(ivChapterLocked)
                        currentDragView?.hide()
                        currentDragView = null
                    }
                    DragEvent.ACTION_DRAG_ENDED -> {
                    }
                    else -> {
                    }
                }
                true
            }

            cvChapter.setOnClickListener {
                if( !ivChapterLocked.isVisible() ) {
                    rvModules.toggleVisibility()
                    unlockChapterLayout.visibility = rvModules.visibility
                    tvHeader.setCompoundDrawablesWithIntrinsicBounds(
                            null,
                            null,
                            ContextCompat.getDrawable(tvHeader.context,
                                    if( rvModules.isVisible() )
                                        R.drawable.ic_down_arrow
                                    else
                                        R.drawable.ic_right_arrow ),
                            null)
                }
            }
            /*ivChapterLocked.setOnClickListener {
                AnimationUtils.exitRevealGone(ivChapterLocked)
            }*/
            /*if( position % 2 == 0 )
                Handler().postDelayed({
                    AnimationUtils.slideInToLeft(cvChapter)
                }, (400 + (position * 250)).toLong())

            else
                Handler().postDelayed({
                    AnimationUtils.slideInToRight(cvChapter)
                }, (400 + (position * 250)).toLong())*/




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
        context?.let {
            ModuleActivity.loadChapter( it, item, position, chaptersMap[item.chapterTitle]  )
        }
    }
}
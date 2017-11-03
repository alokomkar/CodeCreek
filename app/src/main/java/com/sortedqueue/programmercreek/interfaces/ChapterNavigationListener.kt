package com.sortedqueue.programmercreek.interfaces

import com.sortedqueue.programmercreek.database.Chapter

/**
 * Created by Alok on 06/01/17.
 */

interface ChapterNavigationListener {
    fun onChapterSelected(chapter: Chapter, nextChapter: Chapter?)
    fun toggleFabDrawable(drawable: Int)
    fun onProgessStatsUpdate(points: Int)
}

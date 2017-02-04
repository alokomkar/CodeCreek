package com.sortedqueue.programmercreek.interfaces;

import com.sortedqueue.programmercreek.database.Chapter;

/**
 * Created by Alok on 06/01/17.
 */

public interface ChapterNavigationListener {
    void onChapterSelected(Chapter chapter, Chapter nextChapter);
    void toggleFabDrawable( int drawable );
}

package com.sortedqueue.programmercreek.interfaces;

import com.sortedqueue.programmercreek.database.lessons.Lesson;

/**
 * Created by Alok on 29/08/17.
 */

public interface LessonNavigationListener {
    void onLessonSelected(Lesson lesson);
    void onProgessStatsUpdate( int points );
}

package com.sortedqueue.programmercreek.interfaces;

/**
 * Created by Alok on 15/09/17.
 */

public interface NewIntroNavigationListener {
    void loadIntroTopicsFragment();
    void loadTopicDetailsFragment( String topic );
    boolean onBackPressFromFragment();
}

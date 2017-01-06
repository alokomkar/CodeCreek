package com.sortedqueue.programmercreek.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Alok on 02/01/17.
 */

public class ScrollableViewPager extends ViewPager {


    private boolean canScroll = true;
    public ScrollableViewPager(Context context) {
        super(context);
    }
    public ScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
            return canScroll && super.onTouchEvent(ev);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
            return canScroll && super.onInterceptTouchEvent(ev);
    }

}

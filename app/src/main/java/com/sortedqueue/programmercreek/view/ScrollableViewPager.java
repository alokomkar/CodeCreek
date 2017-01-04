package com.sortedqueue.programmercreek.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Alok on 02/01/17.
 */

public class ScrollableViewPager extends ViewPager {

    public enum SwipeDirection {
        all, left, right, none ;
    }

    private float initialXValue;
    private SwipeDirection direction;

    private boolean canScroll = true;
    public ScrollableViewPager(Context context) {
        super(context);
    }
    public ScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.direction = SwipeDirection.all;
    }
    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        /*if( canScroll && this.isSwipeAllowed(ev) ) {
            return super.onTouchEvent(ev);
        }
        else */{
            return canScroll && super.onTouchEvent(ev);
        }

    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /*if( canScroll && this.isSwipeAllowed(ev)) {
            return super.onInterceptTouchEvent(ev);
        }
        else */{
            return canScroll && super.onInterceptTouchEvent(ev);
        }

    }

    private boolean isSwipeAllowed(MotionEvent event) {
        if(this.direction == SwipeDirection.all) return true;

        if(direction == SwipeDirection.none )//disable any swipe
            return false;

        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            initialXValue = event.getX();
            return true;
        }

        if(event.getAction()==MotionEvent.ACTION_MOVE) {
            try {
                float diffX = event.getX() - initialXValue;
                if (diffX > 0 && direction == SwipeDirection.right ) {
                    // swipe from left to right detected
                    return false;
                }else if (diffX < 0 && direction == SwipeDirection.left ) {
                    // swipe from right to left detected
                    return false;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return true;
    }

    public void setAllowedSwipeDirection(SwipeDirection direction) {
        this.direction = direction;
    }
}

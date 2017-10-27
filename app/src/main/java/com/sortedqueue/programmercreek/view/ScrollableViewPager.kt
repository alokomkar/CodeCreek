package com.sortedqueue.programmercreek.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by Alok on 02/01/17.
 */

class ScrollableViewPager : ViewPager {


    private var canScroll = true

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    fun setCanScroll(canScroll: Boolean) {
        this.canScroll = canScroll
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return canScroll && super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return canScroll && super.onInterceptTouchEvent(ev)
    }

}

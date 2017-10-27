package com.sortedqueue.programmercreek.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by Alok on 06/01/17.
 */

class OneDirectionalScrollableViewPager : ViewPager {

    private var initialXValue: Float = 0.toFloat()
    private var direction: SwipeDirection? = null

    constructor(context: Context) : super(context) {
        this.direction = SwipeDirection.all
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.direction = SwipeDirection.all
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (this.IsSwipeAllowed(event)) {
            return super.onInterceptTouchEvent(event)
        }

        return false
    }

    private fun IsSwipeAllowed(event: MotionEvent): Boolean {
        if (this.direction == SwipeDirection.all) return true

        if (direction == SwipeDirection.none)
        //disable any swipe
            return false

        if (event.action == MotionEvent.ACTION_DOWN) {
            initialXValue = event.x
            return true
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            try {
                val diffX = event.x - initialXValue
                if (diffX > 0 && direction == SwipeDirection.right) {
                    // swipe from left to right detected
                    return false
                } else if (diffX < 0 && direction == SwipeDirection.left) {
                    // swipe from right to left detected
                    return false
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

        }

        return true
    }

    fun setAllowedSwipeDirection(direction: SwipeDirection) {
        this.direction = direction
    }


}

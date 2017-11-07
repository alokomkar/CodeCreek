package com.sortedqueue.programmercreek.util

/**
 * Created by Alok on 29/08/17.
 */

import android.support.v4.view.ViewPager
import android.view.View

import com.sortedqueue.programmercreek.R

/**
 * Created by Alok on 29/08/17.
 */

class ParallaxPageTransformer : ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {

        val pageWidth = view.width


        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.alpha = 1f

        } else if (position <= 1) { // [-1,1]

            view.findViewById<View>(R.id.checkFAB).translationX = -position * (pageWidth / 2) //Half the normal speed

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.alpha = 1f
        }


    }
}


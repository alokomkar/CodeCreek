package com.sortedqueue.programmercreek.util;

/**
 * Created by Alok on 29/08/17.
 */

import android.support.v4.view.ViewPager;
import android.view.View;

import com.sortedqueue.programmercreek.R;

/**
 * Created by Alok on 29/08/17.
 */

public class ParallaxPageTransformer implements ViewPager.PageTransformer {

    public void transformPage(View view, float position) {

        int pageWidth = view.getWidth();


        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(1);

        } else if (position <= 1) { // [-1,1]

            view.findViewById(R.id.checkFAB).setTranslationX(-position * (pageWidth / 2)); //Half the normal speed

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(1);
        }


    }
}


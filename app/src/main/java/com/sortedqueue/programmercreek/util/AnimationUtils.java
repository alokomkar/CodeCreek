package com.sortedqueue.programmercreek.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by Alok Omkar on 2017-02-04.
 */

public class AnimationUtils {

    public static void enterReveal( View myView ) {
        try {
            if( myView != null ) {
                // previously invisible view

                // get the center for the clipping circle
                int cx = myView.getMeasuredWidth() / 2;
                int cy = myView.getMeasuredHeight() / 2;

                // get the final radius for the clipping circle
                int finalRadius = Math.max(myView.getWidth(), myView.getHeight()) / 2;

                // create the animator for this view (the start radius is zero)
                Animator anim =
                        ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

                // make the view visible and start the animation
                myView.setVisibility(View.VISIBLE);
                anim.start();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            myView.setVisibility(View.VISIBLE);
        }

    }

    public static void exitReveal(final View myView) {
        try {
            if( myView != null ) {
                // previously visible view

                // get the center for the clipping circle
                int cx = myView.getMeasuredWidth() / 2;
                int cy = myView.getMeasuredHeight() / 2;

                // get the initial radius for the clipping circle
                int initialRadius = myView.getWidth() / 2;

                // create the animation (the final radius is zero)
                Animator anim =
                        ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

                // make the view invisible when the animation is done
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        myView.setVisibility(View.INVISIBLE);
                    }
                });

                // start the animation
                anim.start();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            myView.setVisibility(View.INVISIBLE);
        }


    }

    public static void exitRevealGone(final View myView) {
        try {
            if( myView != null ) {
                // previously visible view

                // get the center for the clipping circle
                int cx = myView.getMeasuredWidth() / 2;
                int cy = myView.getMeasuredHeight() / 2;

                // get the initial radius for the clipping circle
                int initialRadius = myView.getWidth() / 2;

                // create the animation (the final radius is zero)
                Animator anim =
                        ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

                // make the view invisible when the animation is done
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        myView.setVisibility(View.GONE);
                    }
                });

                // start the animation
                anim.start();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            myView.setVisibility(View.GONE);
        }


    }
}

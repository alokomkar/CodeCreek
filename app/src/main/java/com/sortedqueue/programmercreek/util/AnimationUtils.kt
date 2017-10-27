package com.sortedqueue.programmercreek.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation

import com.sortedqueue.programmercreek.R

/**
 * Created by Alok Omkar on 2017-02-04.
 */

object AnimationUtils {

    fun enterReveal(myView: View?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                if (myView != null) {
                    // previously invisible view

                    // get the center for the clipping circle
                    val cx = myView.measuredWidth / 2
                    val cy = myView.measuredHeight / 2

                    // get the final radius for the clipping circle
                    val finalRadius = Math.max(myView.width, myView.height) / 2

                    // create the animator for this view (the start radius is zero)
                    val anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0f, finalRadius.toFloat())

                    // make the view visible and start the animation
                    myView.visibility = View.VISIBLE
                    anim.start()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                myView!!.visibility = View.VISIBLE
            }

        } else {
            myView!!.visibility = View.VISIBLE
        }

    }

    fun exitReveal(myView: View?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            myView!!.visibility = View.INVISIBLE
            return
        }
        try {
            if (myView != null) {
                // previously visible view

                // get the center for the clipping circle
                val cx = myView.measuredWidth / 2
                val cy = myView.measuredHeight / 2

                // get the initial radius for the clipping circle
                val initialRadius = myView.width / 2

                // create the animation (the final radius is zero)
                val anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius.toFloat(), 0f)

                // make the view invisible when the animation is done
                anim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        myView.visibility = View.INVISIBLE
                    }
                })

                // start the animation
                anim.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            myView!!.visibility = View.INVISIBLE
        }


    }

    fun exitRevealGone(myView: View?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            myView!!.visibility = View.GONE
            return
        }
        try {
            if (myView != null) {
                // previously visible view

                // get the center for the clipping circle
                val cx = myView.measuredWidth / 2
                val cy = myView.measuredHeight / 2

                // get the initial radius for the clipping circle
                val initialRadius = myView.width / 2

                // create the animation (the final radius is zero)
                val anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius.toFloat(), 0f)

                // make the view invisible when the animation is done
                anim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        myView.visibility = View.GONE
                    }
                })

                // start the animation
                anim.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            myView!!.visibility = View.GONE
        }


    }

    fun slideDown(view: View?) {
        if (view != null) {
            val viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.context,
                    R.anim.slide_down)
            viewAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {

                }

                override fun onAnimationEnd(animation: Animation) {
                    view.visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })
            view.startAnimation(viewAnimation)
        }

    }

    fun slideDownGone(view: View?) {
        if (view != null) {
            val viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.context,
                    R.anim.slide_down)
            viewAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {

                }

                override fun onAnimationEnd(animation: Animation) {
                    view.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })
            view.startAnimation(viewAnimation)
        }

    }

    fun slideInToRight(view: View?) {
        if (view != null) {
            val viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.context,
                    R.anim.anim_slide_in_right)
            viewAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {

                }

                override fun onAnimationEnd(animation: Animation) {
                    view.visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })
            view.startAnimation(viewAnimation)
        }

    }

    fun slideInToLeft(view: View?) {
        if (view != null) {
            val viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.context,
                    R.anim.anim_slide_in_left)
            viewAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {

                }

                override fun onAnimationEnd(animation: Animation) {
                    view.visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })
            view.startAnimation(viewAnimation)
        }

    }

    fun slideOutToRight(view: View?) {
        if (view != null) {
            val viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.context,
                    R.anim.anim_slide_out_right)
            viewAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {

                }

                override fun onAnimationEnd(animation: Animation) {
                    view.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })
            view.startAnimation(viewAnimation)
        }

    }

    fun slideOutToLeft(view: View?) {
        if (view != null) {
            val viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.context,
                    R.anim.anim_slide_out_left)
            viewAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {

                }

                override fun onAnimationEnd(animation: Animation) {
                    view.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })
            view.startAnimation(viewAnimation)
        }

    }

    fun slideOut(view: View?) {
        if (view != null) {
            val viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.context,
                    R.anim.anim_slide_out_left)
            viewAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {

                }

                override fun onAnimationEnd(animation: Animation) {
                    view.visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })
            view.startAnimation(viewAnimation)
        }

    }

    fun slideUpVisible(view: View?) {
        if (view != null) {
            val viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.context,
                    R.anim.slide_up)
            viewAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {

                }

                override fun onAnimationEnd(animation: Animation) {
                    view.visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })
            view.startAnimation(viewAnimation)
        }

    }

    fun slideUp(view: View?) {
        if (view != null) {
            val viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.context,
                    R.anim.slide_up)
            viewAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {

                }

                override fun onAnimationEnd(animation: Animation) {
                    view.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })
            view.startAnimation(viewAnimation)
        }

    }
}

package com.sortedqueue.programmercreek.util

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.support.annotation.AnyRes
import android.support.design.widget.Snackbar
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.sortedqueue.programmercreek.R

/**
 * Created by Alok on 07/12/16.
 */

object CommonUtils {

    val TAG = "CommonUtils"
    private var mProgressDialog: ProgressDialog? = null

    fun displayProgressDialog(context: Context?, message: String) {
        try {
            if (context != null) {
                /*mProgressDialog = new ProgressDialog(context);
                mProgressDialog.show();
                mProgressDialog.setContentView(R.layout.progress_content_view);*/
                mProgressDialog = ProgressDialog(context, android.R.style.Theme_Translucent)
                mProgressDialog!!.show()
                mProgressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#59000000")))
                //mProgressDialog.setMessage(dialogueMessage);
                mProgressDialog!!.setContentView(R.layout.progress_dialog)
                mProgressDialog!!.setCancelable(false)
                val progressTextView = mProgressDialog!!.findViewById(R.id.progressTextView) as TextView
                progressTextView.text = message
                val animation = AlphaAnimation(1f, 0f)
                animation.duration = 800
                animation.interpolator = LinearInterpolator()
                animation.repeatCount = Animation.INFINITE
                animation.repeatMode = Animation.REVERSE
                progressTextView.startAnimation(animation)
                //((ImageView) mProgressDialog.findViewById(R.id.progressImageView)).startAnimation(animation);
                /*Glide.with(context)
                    .load(R.drawable.ip)
                    .asGif()
                    .placeholder(R.mipmap.ic_launcher)
                    .into((ImageView) mProgressDialog.findViewById(R.id.progressImageView));*/
                mProgressDialog!!.setCancelable(false)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * get uri to drawable or any other resource type if u wish
     * @param context - context
     * *
     * @param drawableId - drawable res id
     * *
     * @return - uri
     */
    fun getUriToDrawable(context: Context, @AnyRes drawableId: Int): Uri {
        val imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.resources.getResourcePackageName(drawableId)
                + '/' + context.resources.getResourceTypeName(drawableId)
                + '/' + context.resources.getResourceEntryName(drawableId))
        return imageUri
    }

    fun dismissProgressDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun displayToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun displayToast(context: Context, message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun displayToastLong(context: Context, message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun displayToastLong(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun displaySnackBar(activity: Activity?, messageInternetUnavailable: Int) {
        try {
            if (activity != null) {
                Snackbar.make(activity.findViewById(android.R.id.content), messageInternetUnavailable, Snackbar.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun displaySnackBar(activity: Activity?, messageInternetUnavailable: String) {
        try {
            if (activity != null) {
                Snackbar.make(activity.findViewById(android.R.id.content), messageInternetUnavailable, Snackbar.LENGTH_LONG).show()

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun displaySnackBar(activity: Activity?, message: Int, option: Int, onClickListener: View.OnClickListener) {
        try {
            if (activity != null) {
                Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).setAction(option, onClickListener).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun displaySnackBarIndefinite(activity: Activity?, message: Int, option: Int, onClickListener: View.OnClickListener) {
        try {
            if (activity != null) {
                Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE).setAction(option, onClickListener).show()
            }
        } catch (e: Exception) {

            e.printStackTrace()
        }

    }
}

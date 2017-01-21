package com.sortedqueue.programmercreek.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sortedqueue.programmercreek.R;

/**
 * Created by Alok on 07/12/16.
 */

public class CommonUtils {

    public static final String TAG = "CommonUtils";
    private static ProgressDialog mProgressDialog;

    public static void displayProgressDialog(Context context, String message ) {
        if( context != null ) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_content_view);

            TextView progressTextView = (TextView) mProgressDialog.findViewById(R.id.progressTextView);
            progressTextView.setText(message);
            Animation animation = new AlphaAnimation(1, 0);
            animation.setDuration(800);
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatCount(Animation.INFINITE);
            animation.setRepeatMode(Animation.REVERSE);
            ((ImageView) mProgressDialog.findViewById(R.id.progressImageView)).startAnimation(animation);
            /*Glide.with(context)
                    .load(R.drawable.ip)
                    .asGif()
                    .placeholder(R.mipmap.ic_launcher)
                    .into((ImageView) mProgressDialog.findViewById(R.id.progressImageView));*/
            mProgressDialog.setCancelable(false);

        }
    }

    public static void dismissProgressDialog( ) {
        if( mProgressDialog != null ) {
            mProgressDialog.dismiss();
        }
    }

    public static void displayToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void displayToast(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void displayToastLong(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void displaySnackBar(Activity activity, int messageInternetUnavailable) {
        try {
            if (activity != null) {
                Snackbar.make(activity.findViewById(android.R.id.content), messageInternetUnavailable, Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void displaySnackBar(Activity activity, String messageInternetUnavailable) {
        try {
            if (activity != null) {
                Snackbar.make(activity.findViewById(android.R.id.content), messageInternetUnavailable, Snackbar.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void displaySnackBar(Activity activity, int message, int option, View.OnClickListener onClickListener) {
        try {
            if (activity != null) {
                Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).setAction(option, onClickListener).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displaySnackBarIndefinite(Activity activity, int message, int option, View.OnClickListener onClickListener) {
        try {
            if (activity != null) {
                Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE).setAction(option, onClickListener).show();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

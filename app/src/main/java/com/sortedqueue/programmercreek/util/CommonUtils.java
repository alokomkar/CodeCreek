package com.sortedqueue.programmercreek.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sortedqueue.programmercreek.R;

/**
 * Created by Alok on 07/12/16.
 */

public class CommonUtils {

    public static final String TAG = "CommonUtils";
    private static ProgressDialog mProgressDialog;

    public static void displayProgressDialog(Context context, String message ) {
        try {
            if( context != null ) {
                /*mProgressDialog = new ProgressDialog(context);
                mProgressDialog.show();
                mProgressDialog.setContentView(R.layout.progress_content_view);*/
                mProgressDialog = new ProgressDialog(context, android.R.style.Theme_Translucent);
                mProgressDialog.show();
                mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#59000000")));
                //mProgressDialog.setMessage(dialogueMessage);
                mProgressDialog.setContentView(R.layout.progress_dialog);
                mProgressDialog.setCancelable(false);
                TextView progressTextView = (TextView) mProgressDialog.findViewById(R.id.progressTextView);
                progressTextView.setText(message);
                Animation animation = new AlphaAnimation(1, 0);
                animation.setDuration(800);
                animation.setInterpolator(new LinearInterpolator());
                animation.setRepeatCount(Animation.INFINITE);
                animation.setRepeatMode(Animation.REVERSE);
                progressTextView.startAnimation(animation);
                //((ImageView) mProgressDialog.findViewById(R.id.progressImageView)).startAnimation(animation);
            /*Glide.with(context)
                    .load(R.drawable.ip)
                    .asGif()
                    .placeholder(R.mipmap.ic_launcher)
                    .into((ImageView) mProgressDialog.findViewById(R.id.progressImageView));*/
                mProgressDialog.setCancelable(false);

            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }

    /**
     * get uri to drawable or any other resource type if u wish
     * @param context - context
     * @param drawableId - drawable res id
     * @return - uri
     */
    public static final Uri getUriToDrawable(@NonNull Context context, @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }

    public static void dismissProgressDialog( ) {
        try {
            if( mProgressDialog != null ) {
                mProgressDialog.dismiss();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
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

    public static void displayToastLong(Context context, String message) {
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

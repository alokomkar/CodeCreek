package com.sortedqueue.programmercreek.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Alok on 07/12/16.
 */

public class CommonUtils {

    public static final String TAG = "CommonUtils";
    private static ProgressDialog mProgressDialog;

    public static void displayProgressDialog(Context context, String message ) {
        if( context != null ) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(message);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
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

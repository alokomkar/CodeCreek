package com.sortedqueue.programmercreek.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by Alok on 07/12/16.
 */

public class CommonUtils {

    public static final String TAG = "CommonUtils";
    private static ProgressDialog mProgressDialog;

    public static void displayProgressDialog(Activity context, String message ) {
        dismissProgressDialog();
        try {
            if( context != null ) {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setMessage(message);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setIndeterminateDrawable(ContextCompat.getDrawable(context, android.R.drawable.progress_indeterminate_horizontal));
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }
        } catch ( Exception e ) {
            Log.d(TAG, "displayProgress : " + e.getMessage());

            e.printStackTrace();
        }

    }

    public static void dismissProgressDialog( ) {
        try {
            if( mProgressDialog != null ) {
                mProgressDialog.dismiss();
            }
        } catch ( Exception e ) {
            Log.d(TAG, "dismissProgressDialog : " + e.getMessage());
            e.printStackTrace();
        }

    }
}

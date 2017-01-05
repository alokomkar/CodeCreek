package com.sortedqueue.programmercreek.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdView;

/**
 * Created by Alok on 07/12/16.
 */

public class CommonUtils {

    public static final String TAG = "CommonUtils";
    private static ProgressDialog mProgressDialog;

    public static void displayProgressDialog(Context context, String message ) {
        dismissProgressDialog();
        try {
            if( context != null ) {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setMessage(message);
                mProgressDialog.setIndeterminate(false);
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

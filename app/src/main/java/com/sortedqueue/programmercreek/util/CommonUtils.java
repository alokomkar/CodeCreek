package com.sortedqueue.programmercreek.util;

import android.app.ProgressDialog;
import android.content.Context;

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
}

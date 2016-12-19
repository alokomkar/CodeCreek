package com.sortedqueue.programmercreek.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Alok on 07/12/16.
 */

public class CommonUtils {

    private static ProgressDialog mProgressDialog;

    public static void displayProgressDialog(Context context, String message ) {
        dismissProgressDialog();
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public static void updateProgress( String message ) {
        if( mProgressDialog != null ) {
            mProgressDialog.setMessage(message);
        }
    }


    public static void dismissProgressDialog( ) {
        if( mProgressDialog != null ) {
            mProgressDialog.dismiss();
        }
    }
}

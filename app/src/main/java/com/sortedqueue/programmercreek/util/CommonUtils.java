package com.sortedqueue.programmercreek.util;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.android.gms.ads.AdView;

/**
 * Created by Alok on 07/12/16.
 */

public class CommonUtils {

    private static ProgressDialog mProgressDialog;
    private static AdView mAdView;

    public static void displayAdsProgressDialog(Context context, String message ) {
        dismissProgressDialog();
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(message);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

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

        }

    }

    public static void dismissProgressDialog( ) {
        try {
            if( mProgressDialog != null ) {
                mProgressDialog.dismiss();
                if( mAdView != null ) {
                    mAdView.destroy();
                }
            }
        } catch ( Exception e ) {

        }

    }
}

package com.sortedqueue.programmercreek.util;

/**
 * Created by cognitive on 7/22/16.
 */

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class PermissionUtils {


    public final static int PERMISSION_REQUEST = 9999;

    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkSelfPermission(Fragment fragment, final String[] permissions) {
        String[] deniedPermissions = checkDeniedPermissions((AppCompatActivity) fragment.getActivity(), permissions );
        if( deniedPermissions.length == 0 ) {
            return true;
        }
        else {
            fragment.requestPermissions(deniedPermissions, PERMISSION_REQUEST);
            return false;
        }
    }

    public static boolean checkSelfPermission(Fragment fragment, final String[] permissions, int requestCode) {
        String[] deniedPermissions = checkDeniedPermissions((AppCompatActivity) fragment.getActivity(), permissions );
        if( deniedPermissions.length == 0 ) {
            return true;
        }
        else {
            fragment.requestPermissions(deniedPermissions, requestCode);
            return false;
        }
    }

    public static boolean checkSelfPermission(final AppCompatActivity activity, final String[] permissions ) {
        String[] deniedPermissions = checkDeniedPermissions( activity, permissions );
        if( deniedPermissions.length == 0 ) {
            return true;
        }
        else {
            ActivityCompat.requestPermissions( activity, deniedPermissions, PERMISSION_REQUEST);
            return false;
        }

    }

    public static boolean checkSelfPermission(final AppCompatActivity activity, final String[] permissions, int requestCode ) {
        String[] deniedPermissions = checkDeniedPermissions( activity, permissions );
        if( deniedPermissions.length == 0 ) {
            return true;
        }
        else {
            ActivityCompat.requestPermissions( activity, deniedPermissions, requestCode);
            return false;
        }

    }

    public static String[] checkDeniedPermissions(AppCompatActivity activity, String[] permissions) {
        ArrayList<String> deniedPermissions = new ArrayList<>();
        for( String permission : permissions ) {
            if( ActivityCompat.checkSelfPermission( activity, permission ) != PackageManager.PERMISSION_GRANTED ) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions.toArray( new String[0] );
    }

}

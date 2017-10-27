package com.sortedqueue.programmercreek.util

/**
 * Created by cognitive on 7/22/16.
 */

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

import java.util.ArrayList

object PermissionUtils {


    val PERMISSION_REQUEST = 9999

    fun verifyPermissions(grantResults: IntArray): Boolean {
        // At least one result must be checked.
        if (grantResults.isEmpty()) {
            return false
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun checkSelfPermission(fragment: Fragment, permissions: Array<String>): Boolean {
        val deniedPermissions = checkDeniedPermissions(fragment.activity as AppCompatActivity, permissions)
        if (deniedPermissions.isEmpty()) {
            return true
        } else {
            fragment.requestPermissions(deniedPermissions, PERMISSION_REQUEST)
            return false
        }
    }

    fun checkSelfPermission(fragment: Fragment, permissions: Array<String>, requestCode: Int): Boolean {
        val deniedPermissions = checkDeniedPermissions(fragment.activity as AppCompatActivity, permissions)
        if (deniedPermissions.isEmpty()) {
            return true
        } else {
            fragment.requestPermissions(deniedPermissions, requestCode)
            return false
        }
    }

    fun checkSelfPermission(activity: AppCompatActivity, permissions: Array<String>): Boolean {
        val deniedPermissions = checkDeniedPermissions(activity, permissions)
        if (deniedPermissions.isEmpty()) {
            return true
        } else {
            ActivityCompat.requestPermissions(activity, deniedPermissions, PERMISSION_REQUEST)
            return false
        }

    }

    fun checkSelfPermission(activity: AppCompatActivity, permissions: Array<String>, requestCode: Int): Boolean {
        val deniedPermissions = checkDeniedPermissions(activity, permissions)
        if (deniedPermissions.isEmpty()) {
            return true
        } else {
            ActivityCompat.requestPermissions(activity, deniedPermissions, requestCode)
            return false
        }

    }

    fun checkDeniedPermissions(activity: AppCompatActivity, permissions: Array<String>): Array<String> {
        val deniedPermissions = ArrayList<String>()
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission)
            }
        }
        return deniedPermissions.toTypedArray()
    }

}

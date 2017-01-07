package com.sortedqueue.programmercreek.database;

import android.content.Context;

import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;

import co.uk.rushorm.core.RushObject;

/**
 * Created by binay on 05/12/16.
 */

public class CreekUser {

    private String emailId;
    private String userFullName;
    private String userPhotoUrl;

    public CreekUser() {
    }

    public CreekUser(String emailId, String userFullName, String userPhotoUrl) {
        this.emailId = emailId;
        this.userFullName = userFullName;
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    @Override
    public String toString() {
        return "CreekUser{" +
                "emailId='" + emailId + '\'' +
                ", userFullName='" + userFullName + '\'' +
                ", userPhotoUrl='" + userPhotoUrl + '\'' +
                '}';
    }

    public void save(Context context) {
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        firebaseDatabaseHandler.writeCreekUser(this);
    }
}

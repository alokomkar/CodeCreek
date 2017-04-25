package com.sortedqueue.programmercreek.database;

import android.content.Context;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;

/**
 * Created by binay on 05/12/16.
 */

public class CreekUser {

    private String emailId;
    private String userFullName;
    private String userPhotoUrl;
    private String programLanguage;

    public CreekUser() {
    }

    public CreekUser(String emailId, String userFullName, String userPhotoUrl, String programLanguage) {
        this.emailId = emailId;
        this.userFullName = userFullName;
        this.userPhotoUrl = userPhotoUrl;
        this.programLanguage = programLanguage;
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

    public String getProgramLanguage() {
        return programLanguage;
    }

    public void setProgramLanguage(String programLanguage) {
        this.programLanguage = programLanguage;
    }

    @Override
    public String toString() {
        return "CreekUser{" +
                "emailId='" + emailId + '\'' +
                ", userFullName='" + userFullName + '\'' +
                ", userPhotoUrl='" + userPhotoUrl + '\'' +
                ", programLanguage='" + programLanguage + '\'' +
                '}';
    }

    public void save(Context context) {
        FirebaseDatabaseHandler firebaseDatabaseHandler = CreekApplication.getFirebaseDatabaseHandler();
        firebaseDatabaseHandler.writeCreekUser(this);
    }
}

package com.sortedqueue.programmercreek.database;

import android.content.Context;

import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;

/**
 * Created by binay on 05/12/16.
 */

public class CreekUser {

    private String emailId;
    private String userFullName;
    private String userPhotoUrl;
    private String programLanguage;
    private String wasAnonUser = "No";
    private String userId = "";


    public CreekUser() {
    }

    public CreekUser(String emailId, String userFullName, String userPhotoUrl, String programLanguage) {
        this.emailId = emailId;
        this.userFullName = userFullName;
        this.userPhotoUrl = userPhotoUrl;
        this.programLanguage = programLanguage;
    }

    public String getWasAnonUser() {
        return wasAnonUser;
    }

    public void setWasAnonUser(String wasAnonUser) {
        this.wasAnonUser = wasAnonUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
                ", wasAnonUser='" + wasAnonUser + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public void save(Context context) {
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        firebaseDatabaseHandler.writeCreekUser(this);
    }
}

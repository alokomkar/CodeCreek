package com.sortedqueue.programmercreek.database;

/**
 * Created by Alok Omkar on 2017-02-09.
 */

public class UserRanking {

    public UserRanking() {
    }

    private String emailId;
    private String userFullName;
    private String userPhotoUrl;
    private String programLanguage;

    private int reputation;

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public int getReputation() {
        return reputation;
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
        return "UserRanking{" +
                "emailId='" + emailId + '\'' +
                ", userFullName='" + userFullName + '\'' +
                ", userPhotoUrl='" + userPhotoUrl + '\'' +
                ", programLanguage='" + programLanguage + '\'' +
                ", reputation=" + reputation +
                '}';
    }
}

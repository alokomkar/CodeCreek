package com.sortedqueue.programmercreek.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Alok on 07/12/16.
 */

public class CreekPreferences {

    private Context context;
    private int programIndex;
    private int programTables;
    private String signInAccount;
    private String accountName;
    private String accountPhoto;

    public static final String SIGN_IN_ACCOUNT = "SIGN_IN_ACCOUNT";
    public static final String ACCOUNT_NAME = "ACCOUNT_NAME";
    public static final String ACCOUNT_PHOTO = "ACCOUNT_PHOTO";
    public static final String KEY_PROG_TABLE_INSERT = "insertProgramTable";
    public static final String KEY_PROG_INDEX_INSERT = "insertProgramIndex";
    public static final String KEY_PROG_INDEX_INSERT_JAVA = "insertProgramIndexJava";
    private static final String KEY_PROG_TABLE_INSERT_JAVA = "insertProgramTableJava";
    private static final String PROGRAM_LANGUAGE = "program_language";

    private SharedPreferences sharedPreferences;

    public CreekPreferences(Context context) {
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getProgramIndex() {
        switch ( getProgramLanguage() ) {
            case "c" :
                programIndex = sharedPreferences.getInt(KEY_PROG_INDEX_INSERT, -1);
                break;
            case "java" :
                programIndex = sharedPreferences.getInt(KEY_PROG_INDEX_INSERT_JAVA, -1);
                break;
        }
        return programIndex;
    }

    public void setProgramIndex(int programIndex) {
        this.programIndex = programIndex;
        switch ( getProgramLanguage() ) {
            case "c" :
                sharedPreferences.edit().putInt(KEY_PROG_INDEX_INSERT, programIndex).apply();
                break;
            case "java" :
                sharedPreferences.edit().putInt(KEY_PROG_INDEX_INSERT_JAVA, programIndex).apply();
                break;
        }
    }

    public int getProgramTables() {

        switch ( getProgramLanguage() ) {
            case "c" :
                programTables = sharedPreferences.getInt(KEY_PROG_TABLE_INSERT, -1);
                break;
            case "java" :
                programTables = sharedPreferences.getInt(KEY_PROG_TABLE_INSERT_JAVA, -1);
                break;
        }
        return programTables;
    }

    public void setProgramTables(int programTables) {
        this.programTables = programTables;
        switch ( getProgramLanguage() ) {
            case "c" :
                sharedPreferences.edit().putInt(KEY_PROG_TABLE_INSERT, programTables).apply();
                break;
            case "java" :
                sharedPreferences.edit().putInt(KEY_PROG_TABLE_INSERT_JAVA, programTables).apply();
                break;
        }
    }

    public String getSignInAccount() {
        signInAccount = sharedPreferences.getString(SIGN_IN_ACCOUNT, "");
        return signInAccount;
    }

    public void setSignInAccount(String signInAccount) {
        this.signInAccount = signInAccount;
        sharedPreferences.edit().putString(SIGN_IN_ACCOUNT, signInAccount).apply();
    }

    public String getAccountName() {
        accountName = sharedPreferences.getString(ACCOUNT_NAME, "");
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
        sharedPreferences.edit().putString(ACCOUNT_NAME, accountName).apply();
    }

    public String getAccountPhoto() {
        accountPhoto = sharedPreferences.getString(ACCOUNT_PHOTO, "");
        return accountPhoto;
    }

    public void setAccountPhoto(String accountPhoto) {
        this.accountPhoto = accountPhoto;
        sharedPreferences.edit().putString(ACCOUNT_PHOTO, accountPhoto).apply();
    }

    public String getProgramLanguage() {
        return sharedPreferences.getString(PROGRAM_LANGUAGE, "c");
    }
    
    public void setProgramLanguage( String language ) {
        sharedPreferences.edit().putString(PROGRAM_LANGUAGE, language).apply();
    }

    public String getProgramWiki() {
        String wikiUrl = "https://programercreek.blogspot.in/2016/12/c-programming-hello-world.html";
        if( getProgramLanguage().equalsIgnoreCase("java")) {
            wikiUrl = "http://www.instanceofjava.com/2014/12/program-to-print-prime-numbers-in-java.html";
        }
        return wikiUrl;
    }
}

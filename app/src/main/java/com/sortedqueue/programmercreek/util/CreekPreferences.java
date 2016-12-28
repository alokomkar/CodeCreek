package com.sortedqueue.programmercreek.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sortedqueue.programmercreek.database.CreekUserDB;
import com.sortedqueue.programmercreek.database.LanguageModule;
import com.sortedqueue.programmercreek.database.SyntaxModule;

import java.util.List;

import co.uk.rushorm.core.RushCore;
import co.uk.rushorm.core.RushSearch;
import co.uk.rushorm.core.RushSearchCallback;

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
    public static final String KEY_PROG_TABLE_INSERT_JAVA = "insertProgramTableJava";
    public static final String KEY_PROG_INDEX_INSERT_CPP = "insertProgramIndexCpp";
    public static final String KEY_PROG_TABLE_INSERT_CPP = "insertProgramTableCpp";
    public static final String KEY_C_MODULE = "keyCModule";
    public static final String KEY_CPP_MODULE = "keyCppModule";
    public static final String KEY_JAVA_MODULE = "keyJavaModule";
    public static final String KEY_C_SYNTAX = "keyCSyntax";
    public static final String KEY_CPP_SYNTAX = "keyCppSyntax";
    public static final String KEY_JAVA_SYNTAX = "keyJavaSyntax";

    private static final String PROGRAM_LANGUAGE = "program_language";

    private SharedPreferences sharedPreferences;
    private String WIKI_HELP = "Wiki_help";
    private String KEY_WELCOME_DONE = "welcome_done";

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
            case "cpp" :
            case "c++" :
                programIndex = sharedPreferences.getInt(KEY_PROG_INDEX_INSERT_CPP, -1);
                break;
        }
        return programIndex;
    }

    public void setProgramIndex(int programIndex) {
        this.programIndex = programIndex;
        switch ( getProgramLanguage() ) {
            case "c" :
                setCProgramIndex( programIndex );
                break;
            case "java" :
                setJavaProgramIndex( programIndex );
                break;
            case "cpp" :
            case "c++" :
                setCppProgramIndex( programIndex );
                break;

        }
    }

    private void setCppProgramIndex(int programIndex) {
        sharedPreferences.edit().putInt(KEY_PROG_INDEX_INSERT_CPP, programIndex).apply();
    }

    private void setCProgramIndex(int programIndex) {
        sharedPreferences.edit().putInt(KEY_PROG_INDEX_INSERT, programIndex).apply();
    }

    private void setJavaProgramIndex( int programIndex ) {
        sharedPreferences.edit().putInt(KEY_PROG_INDEX_INSERT_JAVA, programIndex).apply();
    }

    public int getProgramTables() {

        switch ( getProgramLanguage() ) {
            case "c" :
                programTables = sharedPreferences.getInt(KEY_PROG_TABLE_INSERT, -1);
                break;
            case "java" :
                programTables = sharedPreferences.getInt(KEY_PROG_TABLE_INSERT_JAVA, -1);
                break;
            case "cpp" :
            case "c++" :
                programTables = sharedPreferences.getInt(KEY_PROG_TABLE_INSERT_CPP, -1);
                break;
        }
        return programTables;
    }

    public void setProgramTables(int programTables) {
        this.programTables = programTables;
        switch ( getProgramLanguage() ) {
            case "c" :
                setCProgramTablesIndex(programTables);
                break;
            case "java" :
                setJavaProgramTablesIndex(programTables);
                break;
            case "cpp" :
            case "c++" :
                setCPPProgramTablesIndex(programTables);
                break;
        }
    }

    private void setCPPProgramTablesIndex(int programTables) {
        sharedPreferences.edit().putInt(KEY_PROG_TABLE_INSERT_CPP, programTables).apply();
    }

    private void setJavaProgramTablesIndex(int programTables) {
        sharedPreferences.edit().putInt(KEY_PROG_TABLE_INSERT_JAVA, programTables).apply();
    }

    private void setCProgramTablesIndex(int programTables) {
        sharedPreferences.edit().putInt(KEY_PROG_TABLE_INSERT, programTables).apply();
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

    public boolean getWikiHelp() {
        return sharedPreferences.getBoolean(WIKI_HELP, false);
    }

    public void setWikihelp(boolean wikiHelp) {
        sharedPreferences.edit().putBoolean(WIKI_HELP, wikiHelp).apply();
    }

    public boolean getModulesInserted() {
        switch ( getProgramLanguage() ) {
            case "java" :
                return sharedPreferences.getBoolean(KEY_JAVA_MODULE, false);
            case "c" :
                return sharedPreferences.getBoolean(KEY_C_MODULE, false);
            case "c++" :
            case "cpp" :
                return sharedPreferences.getBoolean(KEY_CPP_MODULE, false);
        }
        return false;
    }

    public void setModulesInserted( boolean modulesInserted ) {
        switch ( getProgramLanguage() ) {
            case "java" :
                setJavaModulesInserted(modulesInserted);
                break;
            case "c" :
                setCModulesInserted(modulesInserted);
                break;
            case "c++" :
            case "cpp" :
                setCPPModulesInserted(modulesInserted);
                break;
        }
    }

    private void setCPPModulesInserted(boolean modulesInserted) {
        sharedPreferences.edit().putBoolean(KEY_CPP_MODULE, modulesInserted).apply();
    }

    private void setCModulesInserted(boolean modulesInserted) {
        sharedPreferences.edit().putBoolean(KEY_C_MODULE, modulesInserted).apply();
    }

    private void setJavaModulesInserted(boolean modulesInserted) {
        sharedPreferences.edit().putBoolean(KEY_JAVA_MODULE, modulesInserted).apply();
    }

    public boolean getSyntaxInserted() {
        switch ( getProgramLanguage().toLowerCase() ) {
            case "java" :
                return sharedPreferences.getBoolean(KEY_JAVA_SYNTAX, false);
            case "c" :
                return sharedPreferences.getBoolean(KEY_C_SYNTAX, false);
            case "c++" :
            case "cpp" :
                return sharedPreferences.getBoolean(KEY_CPP_SYNTAX, false);
        }
        return false;
    }

    public void setSyntaxInserted( boolean modulesInserted ) {
        switch ( getProgramLanguage().toLowerCase() ) {
            case "java" :
                setJavaSyntaxInserted( modulesInserted );
                break;
            case "c" :
                setCSyntaxInserted( modulesInserted );
                break;
            case "c++" :
            case "cpp" :
               setCPPSyntaxInserted( modulesInserted );
               break;
        }
    }

    private void setCPPSyntaxInserted(boolean modulesInserted) {
        sharedPreferences.edit().putBoolean(KEY_CPP_SYNTAX, modulesInserted).apply();
    }

    private void setCSyntaxInserted(boolean modulesInserted) {
        sharedPreferences.edit().putBoolean(KEY_C_SYNTAX, modulesInserted).apply();
    }

    private void setJavaSyntaxInserted(boolean modulesInserted) {
        sharedPreferences.edit().putBoolean(KEY_JAVA_SYNTAX, modulesInserted).apply();
    }

    public void checkUpdateDB(final CreekUserDB creekUserDB) {
        new RushSearch().find(CreekUserDB.class, new RushSearchCallback<CreekUserDB>() {
            @Override
            public void complete(List<CreekUserDB> list) {
                if( list == null || list.size() == 0 ) {
                    //save creekDB
                    creekUserDB.save();

                }
                else {
                    for( CreekUserDB localDB : list ) {
                        if( !creekUserDB.equals(localDB) ) {
                            //Check which db needs to be updated
                            if( creekUserDB.getcModuleDBVersion() > (localDB.getcModuleDBVersion()) ) {
                                setCModulesInserted(false);
                                new RushSearch().whereEqual("moduleLanguage", "c").find(LanguageModule.class, new RushSearchCallback<LanguageModule>() {
                                    @Override
                                    public void complete(List<LanguageModule> list) {
                                        if( list != null ) {
                                            RushCore.getInstance().delete(list);
                                        }
                                    }
                                });
                            }
                            if( creekUserDB.getcProgramIndexDBVersion() > (localDB.getcProgramIndexDBVersion())) {
                                setCProgramIndex(-1);
                            }
                            if( creekUserDB.getcProgramTableDBVersion() > (localDB.getcProgramTableDBVersion()) ) {
                                setCProgramTablesIndex(-1);
                            }
                            if( creekUserDB.getcSyntaxDBVersion() > (localDB.getcSyntaxDBVersion()) ) {
                                setCSyntaxInserted(false);
                                new RushSearch().whereEqual("syntaxLanguage", "c").find(SyntaxModule.class,
                                        new RushSearchCallback<SyntaxModule>() {
                                    @Override
                                    public void complete(List<SyntaxModule> list) {
                                        if( list != null ) {
                                            RushCore.getInstance().delete(list);
                                        }
                                    }
                                });

                            }

                            //Cpp
                            if( creekUserDB.getCppModuleDBVersion() > (localDB.getCppModuleDBVersion()) ) {
                                setCPPModulesInserted(false);
                                new RushSearch().whereEqual("moduleLanguage", "cpp").or().whereEqual("moduleLanguage", "c++").find(LanguageModule.class, new RushSearchCallback<LanguageModule>() {
                                    @Override
                                    public void complete(List<LanguageModule> list) {
                                        if( list != null ) {
                                            RushCore.getInstance().delete(list);
                                        }
                                    }
                                });
                            }
                            if( creekUserDB.getCppProgramIndexDBVersion() > (localDB.getCppProgramIndexDBVersion())) {
                                setCppProgramIndex(-1);
                            }
                            if( creekUserDB.getCppProgramTableDBVersion() > (localDB.getCppProgramTableDBVersion()) ) {
                                setCPPProgramTablesIndex(-1);
                            }
                            if( creekUserDB.getCppSyntaxDBVersion() > (localDB.getCppSyntaxDBVersion()) ) {
                                setCPPSyntaxInserted(false);
                                new RushSearch().whereEqual("syntaxLanguage", "cpp").or().whereEqual("syntaxLanguage", "c++").find(SyntaxModule.class,
                                        new RushSearchCallback<SyntaxModule>() {
                                            @Override
                                            public void complete(List<SyntaxModule> list) {
                                                if( list != null ) {
                                                    RushCore.getInstance().delete(list);
                                                }
                                            }
                                        });
                            }

                            //Java
                            if( creekUserDB.getJavaModuleDBVersion() > (localDB.getJavaModuleDBVersion()) ) {
                                setJavaModulesInserted(false);
                                new RushSearch().whereEqual("moduleLanguage", "java").find(LanguageModule.class, new RushSearchCallback<LanguageModule>() {
                                    @Override
                                    public void complete(List<LanguageModule> list) {
                                        if( list != null ) {
                                            RushCore.getInstance().delete(list);
                                        }
                                    }
                                });
                            }
                            if( creekUserDB.getJavaProgramIndexDBVersion() > (localDB.getJavaProgramIndexDBVersion())) {
                                setJavaProgramIndex(-1);
                            }
                            if( creekUserDB.getJavaProgramTableDBVersion() > (localDB.getJavaProgramTableDBVersion()) ) {
                                setJavaProgramTablesIndex(-1);
                            }
                            if( creekUserDB.getJavaSyntaxDBVersion() > (localDB.getJavaSyntaxDBVersion()) ) {
                                setJavaSyntaxInserted(false);
                                new RushSearch().whereEqual("syntaxLanguage", "java").find(SyntaxModule.class,
                                        new RushSearchCallback<SyntaxModule>() {
                                            @Override
                                            public void complete(List<SyntaxModule> list) {
                                                if( list != null ) {
                                                    RushCore.getInstance().delete(list);
                                                }
                                            }
                                        });
                            }

                            //C Premium
                            if( creekUserDB.getcModuleDBVersionPremium() > (localDB.getcModuleDBVersionPremium()) ) {

                            }
                            if( creekUserDB.getcProgramIndexDBVersionPremium() > (localDB.getcProgramIndexDBVersionPremium())) {

                            }
                            if( creekUserDB.getcProgramTableDBVersionPremium() > (localDB.getcProgramTableDBVersionPremium()) ) {

                            }
                            if( creekUserDB.getcSyntaxDBVersionPremium() > (localDB.getcSyntaxDBVersionPremium()) ) {

                            }

                            //CPP Premium
                            if( creekUserDB.getCppModuleDBVersionPremium() > (localDB.getCppModuleDBVersionPremium()) ) {

                            }
                            if( creekUserDB.getCppProgramIndexDBVersionPremium() > (localDB.getCppProgramIndexDBVersionPremium())) {

                            }
                            if( creekUserDB.getCppProgramTableDBVersionPremium() > (localDB.getCppProgramTableDBVersionPremium()) ) {

                            }
                            if( creekUserDB.getCppSyntaxDBVersionPremium() > (localDB.getCppSyntaxDBVersionPremium()) ) {

                            }
                            

                            //Java Premium
                            if( creekUserDB.getJavaModuleDBVersionPremium() > (localDB.getJavaModuleDBVersionPremium()) ) {

                            }
                            if( creekUserDB.getJavaProgramIndexDBVersionPremium() > (localDB.getJavaProgramIndexDBVersionPremium())) {

                            }
                            if( creekUserDB.getJavaProgramTableDBVersionPremium() > (localDB.getJavaProgramTableDBVersionPremium()) ) {

                            }
                            if( creekUserDB.getJavaSyntaxDBVersionPremium() > (localDB.getJavaSyntaxDBVersionPremium()) ) {

                            }
                        }
                    }
                }
            }
        });
    }

    public boolean isWelcomeDone() {
        return sharedPreferences.getBoolean(KEY_WELCOME_DONE, false);
    }

    public void setWelcomeDone(boolean welcomeDone) {
        sharedPreferences.edit().putBoolean(KEY_WELCOME_DONE, welcomeDone).apply();
    }
}

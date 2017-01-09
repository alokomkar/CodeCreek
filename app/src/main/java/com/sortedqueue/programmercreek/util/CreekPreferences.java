package com.sortedqueue.programmercreek.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.database.CreekUserDB;
import com.sortedqueue.programmercreek.database.CreekUserStats;
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

    public static final String KEY_C_WIKI = "keyCWiki";
    public static final String KEY_CPP_WIKI = "keyCppWiki";
    public static final String KEY_JAVA_WIKI = "keyJavaWiki";

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
        return sharedPreferences.getString(PROGRAM_LANGUAGE, "");
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

    public int getModulesInserted() {
        switch ( getProgramLanguage() ) {
            case "java" :
                return sharedPreferences.getInt(KEY_JAVA_MODULE, -1);
            case "c" :
                return sharedPreferences.getInt(KEY_C_MODULE, -1);
            case "c++" :
            case "cpp" :
                return sharedPreferences.getInt(KEY_CPP_MODULE, -1);
        }
        return -1;
    }

    public void setModulesInserted( String modulesInserted ) {
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

    private void setCPPModulesInserted(String modulesInserted) {
        sharedPreferences.edit().putString(KEY_CPP_MODULE, modulesInserted).apply();
    }

    private void setCModulesInserted(String modulesInserted) {
        sharedPreferences.edit().putString(KEY_C_MODULE, modulesInserted).apply();
    }

    private void setJavaModulesInserted(String modulesInserted) {
        sharedPreferences.edit().putString(KEY_JAVA_MODULE, modulesInserted).apply();
    }

    public String getProgramWikiInserted() {
        switch ( getProgramLanguage().toLowerCase() ) {
            case "java" :
                return sharedPreferences.getString(KEY_JAVA_WIKI, "0");
            case "c" :
                return sharedPreferences.getString(KEY_C_WIKI, "0");
            case "c++" :
            case "cpp" :
                return sharedPreferences.getString(KEY_CPP_WIKI, "0");
        }
        return "0";
    }

    public String getSyntaxInserted() {
        switch ( getProgramLanguage().toLowerCase() ) {
            case "java" :
                return sharedPreferences.getString(KEY_JAVA_SYNTAX, "0");
            case "c" :
                return sharedPreferences.getString(KEY_C_SYNTAX, "0");
            case "c++" :
            case "cpp" :
                return sharedPreferences.getString(KEY_CPP_SYNTAX, "0");
        }
        return "0";
    }
    public void setProgramWikiInserted( String modulesInserted ) {
        switch ( getProgramLanguage().toLowerCase() ) {
            case "java" :
                setJavaWikiInserted( modulesInserted );
                break;
            case "c" :
                setCWikiInserted( modulesInserted );
                break;
            case "c++" :
            case "cpp" :
                setCPPWikiInserted( modulesInserted );
                break;
        }
    }

    public void setSyntaxInserted( String modulesInserted ) {
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

    private void setCPPSyntaxInserted(String modulesInserted) {
        sharedPreferences.edit().putString(KEY_CPP_SYNTAX, modulesInserted).apply();
    }

    private void setCSyntaxInserted(String modulesInserted) {
        sharedPreferences.edit().putString(KEY_C_SYNTAX, modulesInserted).apply();
    }

    private void setJavaSyntaxInserted(String modulesInserted) {
        sharedPreferences.edit().putString(KEY_JAVA_SYNTAX, modulesInserted).apply();
    }

    private void setCPPWikiInserted(String modulesInserted) {
        sharedPreferences.edit().putString(KEY_CPP_WIKI, modulesInserted).apply();
    }

    private void setCWikiInserted(String modulesInserted) {
        sharedPreferences.edit().putString(KEY_C_WIKI, modulesInserted).apply();
    }

    private void setJavaWikiInserted(String modulesInserted) {
        sharedPreferences.edit().putString(KEY_JAVA_WIKI, modulesInserted).apply();
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
                            if( !creekUserDB.getcModuleDBVersion().equals (localDB.getcModuleDBVersion()) ) {
                                //TODO
                            }
                            if( creekUserDB.getcProgramIndexDBVersion() > (localDB.getcProgramIndexDBVersion())) {
                                setCProgramIndex(-1);
                            }
                            if( creekUserDB.getcProgramTableDBVersion() > (localDB.getcProgramTableDBVersion()) ) {
                                setCProgramTablesIndex(-1);
                            }
                            if( !creekUserDB.getcSyntaxDBVersion().equals (localDB.getcSyntaxDBVersion()) ) {
                                setCSyntaxInserted("0");
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
                            if( !creekUserDB.getCppModuleDBVersion().equals (localDB.getCppModuleDBVersion()) ) {
                                setCPPModulesInserted("0");
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
                            if( !creekUserDB.getCppSyntaxDBVersion().equals(localDB.getCppSyntaxDBVersion()) ) {
                                setCPPSyntaxInserted("0");
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
                            if( !creekUserDB.getJavaModuleDBVersion().equals (localDB.getJavaModuleDBVersion()) ) {
                                setJavaModulesInserted("0");
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
                            if( !creekUserDB.getJavaSyntaxDBVersion().equals(localDB.getJavaSyntaxDBVersion()) ) {
                                setJavaSyntaxInserted("0");
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
                            if( creekUserDB.getcModuleDBVersionPremium().equals (localDB.getcModuleDBVersionPremium()) ) {

                            }
                            if( creekUserDB.getcProgramIndexDBVersionPremium() > (localDB.getcProgramIndexDBVersionPremium())) {

                            }
                            if( creekUserDB.getcProgramTableDBVersionPremium() > (localDB.getcProgramTableDBVersionPremium()) ) {

                            }
                            if( creekUserDB.getcSyntaxDBVersionPremium().equals (localDB.getcSyntaxDBVersionPremium()) ) {

                            }

                            //CPP Premium
                            if( !creekUserDB.getCppModuleDBVersionPremium().equals (localDB.getCppModuleDBVersionPremium()) ) {

                            }
                            if( creekUserDB.getCppProgramIndexDBVersionPremium() > (localDB.getCppProgramIndexDBVersionPremium())) {

                            }
                            if( creekUserDB.getCppProgramTableDBVersionPremium() > (localDB.getCppProgramTableDBVersionPremium()) ) {

                            }
                            if( creekUserDB.getCppSyntaxDBVersionPremium().equals(localDB.getCppSyntaxDBVersionPremium()) ) {

                            }
                            

                            //Java Premium
                            if( !creekUserDB.getJavaModuleDBVersionPremium().equals (localDB.getJavaModuleDBVersionPremium()) ) {

                            }
                            if( creekUserDB.getJavaProgramIndexDBVersionPremium() > (localDB.getJavaProgramIndexDBVersionPremium())) {

                            }
                            if( creekUserDB.getJavaProgramTableDBVersionPremium() > (localDB.getJavaProgramTableDBVersionPremium()) ) {

                            }
                            if( !creekUserDB.getJavaSyntaxDBVersionPremium().equals(localDB.getJavaSyntaxDBVersionPremium()) ) {

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

    public void saveCreekUserStats(CreekUserStats creekUserStats) {
        sharedPreferences.edit().putInt("javaProgressIndex", creekUserStats.getJavaProgressIndex()).apply();
        sharedPreferences.edit().putInt("cProgressIndex", creekUserStats.getcProgramIndex()).apply();
        sharedPreferences.edit().putInt("cppProgressIndex", creekUserStats.getCppProgramIndex()).apply();
        CreekApplication.getInstance().setCreekUserStats(creekUserStats);
    }

    public CreekUserStats getCreekUserStats( ) {
        CreekUserStats creekUserStats = new CreekUserStats();
        creekUserStats.setCppProgramIndex(sharedPreferences.getInt("cppProgressIndex", 0));
        creekUserStats.setcProgramIndex(sharedPreferences.getInt("cProgressIndex", 0));
        creekUserStats.setJavaProgressIndex(sharedPreferences.getInt("javaProgressIndex", 0));
        return creekUserStats;
    }

    public void setCreekUserDB(CreekUserDB creekUserDB) {
        Gson gson = new Gson();
        String creekUserDBString = gson.toJson(creekUserDB);
        sharedPreferences.edit().putString("CreekUserDB", creekUserDBString).apply();
    }

    public CreekUserDB getCreekUserDB() {
        String jsonString = sharedPreferences.getString("CreekUserDB", "");
        if( !jsonString.equals("") ) {
            return new Gson().fromJson(jsonString, CreekUserDB.class);
        }
        else {
            return null;
        }
    }

    public boolean checkProgramIndexUpdate() {
        CreekUserDB creekUserDB = getCreekUserDB();
        boolean result = true;
        switch ( getProgramLanguage() ) {
            case "c" :
                result = (getProgramIndex() == creekUserDB.getcProgramIndexDBVersion());
                break;
            case "c++":
            case "cpp":
                result = (getProgramIndex() == (int)creekUserDB.getCppProgramIndexDBVersion());
                break;
            case "java":
                result = (getProgramIndex() == (int)creekUserDB.getJavaProgramIndexDBVersion());
                break;
        }
        return result;
    }

    public boolean checkProgramTableUpdate() {
        CreekUserDB creekUserDB = getCreekUserDB();
        boolean result = true;
        switch ( getProgramLanguage() ) {
            case "c" :
                result = getProgramTables() == (int)creekUserDB.getcProgramTableDBVersion();
                break;
            case "c++":
            case "cpp":
                result = getProgramTables() == (int)creekUserDB.getCppProgramTableDBVersion();
                break;
            case "java":
                result = getProgramTables() == (int)creekUserDB.getJavaProgramTableDBVersion();
                break;
        }
        return result;
    }

    public int getProgramIndexDifference() {
        CreekUserDB creekUserDB = getCreekUserDB();
        int result;
        result = getProgramIndex();
        if( result == -1 ) {
            result = 0;
        }

        switch ( getProgramLanguage() ) {
            case "c" :
                result = (int)(creekUserDB.getcProgramIndexDBVersion() - result);
                break;
            case "c++":
            case "cpp":
                result = (int)(creekUserDB.getCppProgramIndexDBVersion() - result);
                break;
            case "java":
                result = (int)(creekUserDB.getJavaProgramIndexDBVersion() - result);
                break;
        }
        return result;
    }

    public int getProgramTableDifference() {
        CreekUserDB creekUserDB = getCreekUserDB();
        int result;
        result = getProgramTables();
        if( result == -1 ) {
            result = 0;
        }

        switch ( getProgramLanguage() ) {
            case "c" :
                result = (int)(creekUserDB.getcProgramTableDBVersion() - result);
                break;
            case "c++":
            case "cpp":
                result = (int)(creekUserDB.getCppProgramTableDBVersion() - result);
                break;
            case "java":
                result = (int)(creekUserDB.getJavaProgramTableDBVersion() - result);
                break;
        }
        return result;
    }
}

package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alok Omkar on 2017-01-01.
 */

public class CreekUserStats implements Parcelable {

    private int javaProgressIndex = 0;
    private int cProgressIndex = 0;
    private int cppProgressIndex = 0;
    private int uspProgramIndex = 0;
    private int sqlProgressIndex = 0;
    private int totalLanguages = 0;
    private int creekUserReputation = 0;

    private ArrayList<Integer> unlockedCProgramIndexList = new ArrayList<>();
    private ArrayList<Integer> unlockedUspProgramIndexList = new ArrayList<>();
    private ArrayList<Integer> unlockedCppProgramIndexList = new ArrayList<>();
    private ArrayList<Integer> unlockedJavaProgramIndexList = new ArrayList<>();
    private ArrayList<Integer> unlockedSqlProgramIndexList = new ArrayList<>();

    private ArrayList<String> unlockedCLanguageModuleIdList = new ArrayList<>();
    private ArrayList<String> unlockedUspLanguageModuleIdList = new ArrayList<>();
    private ArrayList<String> unlockedCppLanguageModuleIdList = new ArrayList<>();
    private ArrayList<String> unlockedJavaLanguageModuleIdList = new ArrayList<>();
    private ArrayList<String> unlockedSqlLanguageModuleIdList = new ArrayList<>();

    private ArrayList<String> unlockedCSyntaxModuleIdList = new ArrayList<>();
    private ArrayList<String> unlockedUspSyntaxModuleIdList = new ArrayList<>();
    private ArrayList<String> unlockedCppSyntaxModuleIdList = new ArrayList<>();
    private ArrayList<String> unlockedJavaSyntaxModuleIdList = new ArrayList<>();
    private ArrayList<String> unlockedSqlSyntaxModuleIdList = new ArrayList<>();

    private ArrayList<String> unlockedCWikiIdList = new ArrayList<>();
    private ArrayList<String> unlockedUspWikiIdList = new ArrayList<>();
    private ArrayList<String> unlockedCppWikiIdList = new ArrayList<>();
    private ArrayList<String> unlockedJavaWikiIdList = new ArrayList<>();
    private ArrayList<String> unlockedSqlWikiIdList = new ArrayList<>();

    private ArrayList<String> userAddedPrograms = new ArrayList<>();
    private ArrayList<String> unlockedUserAddedPrograms = new ArrayList<>();

    public CreekUserStats(int javaProgressIndex, int cProgressIndex, int cppProgressIndex, int uspProgramIndex) {
        this.javaProgressIndex = javaProgressIndex;
        this.cProgressIndex = cProgressIndex;
        this.cppProgressIndex = cppProgressIndex;
        this.uspProgramIndex = uspProgramIndex;
    }

    public CreekUserStats() {
    }

    public int getTotalLanguages() {
        return totalLanguages;
    }

    public void setTotalLanguages(int totalLanguages) {
        this.totalLanguages = totalLanguages;
    }

    public int getJavaProgressIndex() {
        return javaProgressIndex;
    }

    public void setJavaProgressIndex(int javaProgressIndex) {
        this.javaProgressIndex = javaProgressIndex;
    }

    public int getSqlProgressIndex() {
        return sqlProgressIndex;
    }

    public void setSqlProgressIndex(int sqlProgressIndex) {
        this.sqlProgressIndex = sqlProgressIndex;
    }

    public int getcProgressIndex() {
        return cProgressIndex;
    }

    public void setcProgressIndex(int cProgressIndex) {
        this.cProgressIndex = cProgressIndex;
    }

    public int getCppProgressIndex() {
        return cppProgressIndex;
    }

    public void setCppProgressIndex(int cppProgressIndex) {
        this.cppProgressIndex = cppProgressIndex;
    }

    public ArrayList<Integer> getUnlockedCProgramIndexList() {
        if( unlockedCProgramIndexList.size() == 0 ) {
            unlockedCProgramIndexList.add(1);
        }
        return unlockedCProgramIndexList;
    }

    public void setUnlockedCProgramIndexList(ArrayList<Integer> unlockedCProgramIndexList) {
        this.unlockedCProgramIndexList = unlockedCProgramIndexList;
    }

    public ArrayList<Integer> getUnlockedCppProgramIndexList() {
        if( unlockedCppProgramIndexList.size() == 0 ) {
            unlockedCppProgramIndexList.add(1);
        }
        return unlockedCppProgramIndexList;
    }

    public void setUnlockedCppProgramIndexList(ArrayList<Integer> unlockedCppProgramIndexList) {
        this.unlockedCppProgramIndexList = unlockedCppProgramIndexList;
    }

    public ArrayList<Integer> getUnlockedJavaProgramIndexList() {
        if( unlockedJavaProgramIndexList.size() == 0 ) {
            unlockedJavaProgramIndexList.add(1);
        }
        return unlockedJavaProgramIndexList;
    }

    public void setUnlockedJavaProgramIndexList(ArrayList<Integer> unlockedJavaProgramIndexList) {
        this.unlockedJavaProgramIndexList = unlockedJavaProgramIndexList;
    }

    public ArrayList<String> getUnlockedCLanguageModuleIdList() {
        if( unlockedCLanguageModuleIdList.size() == 0 ) {
            unlockedCLanguageModuleIdList.add("c_1");
        }
        return unlockedCLanguageModuleIdList;
    }

    public void setUnlockedCLanguageModuleIdList(ArrayList<String> unlockedCLanguageModuleIdList) {
        this.unlockedCLanguageModuleIdList = unlockedCLanguageModuleIdList;
    }

    public ArrayList<String> getUnlockedCppLanguageModuleIdList() {
        if( unlockedCppLanguageModuleIdList.size() == 0 ) {
            unlockedCppLanguageModuleIdList.add("cpp_1");
        }
        return unlockedCppLanguageModuleIdList;
    }

    public void setUnlockedCppLanguageModuleIdList(ArrayList<String> unlockedCppLanguageModuleIdList) {
        this.unlockedCppLanguageModuleIdList = unlockedCppLanguageModuleIdList;
    }

    public ArrayList<String> getUnlockedJavaLanguageModuleIdList() {
        if( unlockedJavaLanguageModuleIdList.size() == 0 ) {
            unlockedJavaLanguageModuleIdList.add("java_1");
        }
        return unlockedJavaLanguageModuleIdList;
    }

    public ArrayList<String> getUnlockedSqlLanguageModuleIdList() {
        if( unlockedSqlLanguageModuleIdList.size() == 0 ) {
            unlockedSqlLanguageModuleIdList.add("sql_1");
        }
        return unlockedSqlLanguageModuleIdList;
    }

    public void setUnlockedSqlLanguageModuleIdList(ArrayList<String> unlockedSqlLanguageModuleIdList) {
        this.unlockedSqlLanguageModuleIdList = unlockedSqlLanguageModuleIdList;
    }

    public void setUnlockedJavaLanguageModuleIdList(ArrayList<String> unlockedJavaLanguageModuleIdList) {
        this.unlockedJavaLanguageModuleIdList = unlockedJavaLanguageModuleIdList;
    }

    public ArrayList<String> getUnlockedCSyntaxModuleIdList() {
        if( unlockedCSyntaxModuleIdList.size() == 0 ) {
            unlockedCSyntaxModuleIdList.add("c_1_s_1");
        }
        return unlockedCSyntaxModuleIdList;
    }

    public void setUnlockedCSyntaxModuleIdList(ArrayList<String> unlockedCSyntaxModuleIdList) {
        this.unlockedCSyntaxModuleIdList = unlockedCSyntaxModuleIdList;
    }

    public ArrayList<String> getUnlockedCppSyntaxModuleIdList() {
        if( unlockedCppSyntaxModuleIdList.size() == 0 ) {
            unlockedCppSyntaxModuleIdList.add("cpp_1_s_1");
        }
        return unlockedCppSyntaxModuleIdList;
    }

    public void setUnlockedCppSyntaxModuleIdList(ArrayList<String> unlockedCppSyntaxModuleIdList) {
        this.unlockedCppSyntaxModuleIdList = unlockedCppSyntaxModuleIdList;
    }

    public ArrayList<String> getUnlockedJavaSyntaxModuleIdList() {
        if( unlockedJavaSyntaxModuleIdList.size() == 0 ) {
            unlockedJavaSyntaxModuleIdList.add("java_1_s_1");
        }
        return unlockedJavaSyntaxModuleIdList;
    }

    public void setUnlockedJavaSyntaxModuleIdList(ArrayList<String> unlockedJavaSyntaxModuleIdList) {
        this.unlockedJavaSyntaxModuleIdList = unlockedJavaSyntaxModuleIdList;
    }

    public ArrayList<String> getUnlockedSqlSyntaxModuleIdList() {
        if( unlockedSqlSyntaxModuleIdList.size() == 0 ) {
            unlockedSqlSyntaxModuleIdList.add("sql_1_s_1");
        }
        return unlockedSqlSyntaxModuleIdList;
    }

    public void setUnlockedSqlSyntaxModuleIdList(ArrayList<String> unlockedSqlSyntaxModuleIdList) {
        this.unlockedSqlSyntaxModuleIdList = unlockedSqlSyntaxModuleIdList;
    }

    public ArrayList<String> getUnlockedCWikiIdList() {
        if( unlockedCWikiIdList.size() == 0 ) {
            unlockedCWikiIdList.add("c1");
        }
        return unlockedCWikiIdList;
    }

    public void setUnlockedCWikiIdList(ArrayList<String> unlockedCWikiIdList) {
        this.unlockedCWikiIdList = unlockedCWikiIdList;
    }

    public ArrayList<String> getUnlockedCppWikiIdList() {
        if( unlockedCppWikiIdList.size() == 0 ) {
            unlockedCppWikiIdList.add("cpp1");
        }
        return unlockedCppWikiIdList;
    }

    public void setUnlockedCppWikiIdList(ArrayList<String> unlockedCppWikiIdList) {
        this.unlockedCppWikiIdList = unlockedCppWikiIdList;
    }

    public ArrayList<String> getUnlockedJavaWikiIdList() {
        if( unlockedJavaWikiIdList.size() == 0 ) {
            unlockedJavaWikiIdList.add("java1");
        }
        return unlockedJavaWikiIdList;
    }

    public void setUnlockedJavaWikiIdList(ArrayList<String> unlockedJavaWikiIdList) {
        this.unlockedJavaWikiIdList = unlockedJavaWikiIdList;
    }

    public int getUspProgramIndex() {
        return uspProgramIndex;
    }

    public void setUspProgramIndex(int uspProgramIndex) {
        this.uspProgramIndex = uspProgramIndex;
    }

    public ArrayList<Integer> getUnlockedUspProgramIndexList() {
        if( unlockedUspProgramIndexList.size() == 0 ) {
            unlockedUspProgramIndexList.add(1);
        }
        return unlockedUspProgramIndexList;
    }

    public ArrayList<Integer> getUnlockedSqlProgramIndexList() {
        if( unlockedSqlProgramIndexList.size() == 0 ) {
            unlockedSqlProgramIndexList.add(1);
        }
        return unlockedSqlProgramIndexList;
    }

    public void setUnlockedSqlProgramIndexList(ArrayList<Integer> unlockedSqlProgramIndexList) {
        this.unlockedSqlProgramIndexList = unlockedSqlProgramIndexList;
    }

    public ArrayList<String> getUnlockedSqlWikiIdList() {
        return unlockedSqlWikiIdList;
    }

    public void setUnlockedSqlWikiIdList(ArrayList<String> unlockedSqlWikiIdList) {
        this.unlockedSqlWikiIdList = unlockedSqlWikiIdList;
    }

    public void setUnlockedUspProgramIndexList(ArrayList<Integer> unlockedUspProgramIndexList) {

        this.unlockedUspProgramIndexList = unlockedUspProgramIndexList;
    }

    public ArrayList<String> getUnlockedUspLanguageModuleIdList() {
        if( unlockedUspLanguageModuleIdList.size() == 0 ) {
            unlockedUspLanguageModuleIdList.add("usp_1");
        }
        return unlockedUspLanguageModuleIdList;
    }

    public void setUnlockedUspLanguageModuleIdList(ArrayList<String> unlockedUspLanguageModuleIdList) {
        this.unlockedUspLanguageModuleIdList = unlockedUspLanguageModuleIdList;
    }

    public ArrayList<String> getUnlockedUspSyntaxModuleIdList() {
        if( unlockedUspSyntaxModuleIdList.size() == 0 ) {
            unlockedUspSyntaxModuleIdList.add("usp_1_s_1");
        }
        return unlockedUspSyntaxModuleIdList;
    }

    public void setUnlockedUspSyntaxModuleIdList(ArrayList<String> unlockedUspSyntaxModuleIdList) {
        this.unlockedUspSyntaxModuleIdList = unlockedUspSyntaxModuleIdList;
    }

    public ArrayList<String> getUnlockedUspWikiIdList() {
        if( unlockedUspWikiIdList.size() == 0 ) {
            unlockedUspWikiIdList.add("usp1");
        }
        return unlockedUspWikiIdList;
    }

    public void setUnlockedUspWikiIdList(ArrayList<String> unlockedUspWikiIdList) {
        this.unlockedUspWikiIdList = unlockedUspWikiIdList;
    }

    public int getCreekUserReputation() {
        return creekUserReputation;
    }

    public void setCreekUserReputation(int creekUserReputation) {
        this.creekUserReputation = creekUserReputation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreekUserStats that = (CreekUserStats) o;

        if (javaProgressIndex != that.javaProgressIndex) return false;
        if (cProgressIndex != that.cProgressIndex) return false;
        if (cppProgressIndex != that.cppProgressIndex) return false;
        if (uspProgramIndex != that.uspProgramIndex) return false;
        if (sqlProgressIndex != that.sqlProgressIndex) return false;
        if (totalLanguages != that.totalLanguages) return false;
        if (creekUserReputation != that.creekUserReputation) return false;
        if (unlockedCProgramIndexList != null ? !unlockedCProgramIndexList.equals(that.unlockedCProgramIndexList) : that.unlockedCProgramIndexList != null)
            return false;
        if (unlockedUspProgramIndexList != null ? !unlockedUspProgramIndexList.equals(that.unlockedUspProgramIndexList) : that.unlockedUspProgramIndexList != null)
            return false;
        if (unlockedCppProgramIndexList != null ? !unlockedCppProgramIndexList.equals(that.unlockedCppProgramIndexList) : that.unlockedCppProgramIndexList != null)
            return false;
        if (unlockedJavaProgramIndexList != null ? !unlockedJavaProgramIndexList.equals(that.unlockedJavaProgramIndexList) : that.unlockedJavaProgramIndexList != null)
            return false;
        if (unlockedSqlProgramIndexList != null ? !unlockedSqlProgramIndexList.equals(that.unlockedSqlProgramIndexList) : that.unlockedSqlProgramIndexList != null)
            return false;
        if (unlockedCLanguageModuleIdList != null ? !unlockedCLanguageModuleIdList.equals(that.unlockedCLanguageModuleIdList) : that.unlockedCLanguageModuleIdList != null)
            return false;
        if (unlockedUspLanguageModuleIdList != null ? !unlockedUspLanguageModuleIdList.equals(that.unlockedUspLanguageModuleIdList) : that.unlockedUspLanguageModuleIdList != null)
            return false;
        if (unlockedCppLanguageModuleIdList != null ? !unlockedCppLanguageModuleIdList.equals(that.unlockedCppLanguageModuleIdList) : that.unlockedCppLanguageModuleIdList != null)
            return false;
        if (unlockedJavaLanguageModuleIdList != null ? !unlockedJavaLanguageModuleIdList.equals(that.unlockedJavaLanguageModuleIdList) : that.unlockedJavaLanguageModuleIdList != null)
            return false;
        if (unlockedSqlLanguageModuleIdList != null ? !unlockedSqlLanguageModuleIdList.equals(that.unlockedSqlLanguageModuleIdList) : that.unlockedSqlLanguageModuleIdList != null)
            return false;
        if (unlockedCSyntaxModuleIdList != null ? !unlockedCSyntaxModuleIdList.equals(that.unlockedCSyntaxModuleIdList) : that.unlockedCSyntaxModuleIdList != null)
            return false;
        if (unlockedUspSyntaxModuleIdList != null ? !unlockedUspSyntaxModuleIdList.equals(that.unlockedUspSyntaxModuleIdList) : that.unlockedUspSyntaxModuleIdList != null)
            return false;
        if (unlockedCppSyntaxModuleIdList != null ? !unlockedCppSyntaxModuleIdList.equals(that.unlockedCppSyntaxModuleIdList) : that.unlockedCppSyntaxModuleIdList != null)
            return false;
        if (unlockedJavaSyntaxModuleIdList != null ? !unlockedJavaSyntaxModuleIdList.equals(that.unlockedJavaSyntaxModuleIdList) : that.unlockedJavaSyntaxModuleIdList != null)
            return false;
        if (unlockedSqlSyntaxModuleIdList != null ? !unlockedSqlSyntaxModuleIdList.equals(that.unlockedSqlSyntaxModuleIdList) : that.unlockedSqlSyntaxModuleIdList != null)
            return false;
        if (unlockedCWikiIdList != null ? !unlockedCWikiIdList.equals(that.unlockedCWikiIdList) : that.unlockedCWikiIdList != null)
            return false;
        if (unlockedUspWikiIdList != null ? !unlockedUspWikiIdList.equals(that.unlockedUspWikiIdList) : that.unlockedUspWikiIdList != null)
            return false;
        if (unlockedCppWikiIdList != null ? !unlockedCppWikiIdList.equals(that.unlockedCppWikiIdList) : that.unlockedCppWikiIdList != null)
            return false;
        if (unlockedJavaWikiIdList != null ? !unlockedJavaWikiIdList.equals(that.unlockedJavaWikiIdList) : that.unlockedJavaWikiIdList != null)
            return false;
        return unlockedSqlWikiIdList != null ? unlockedSqlWikiIdList.equals(that.unlockedSqlWikiIdList) : that.unlockedSqlWikiIdList == null;

    }

    @Override
    public int hashCode() {
        int result = javaProgressIndex;
        result = 31 * result + cProgressIndex;
        result = 31 * result + cppProgressIndex;
        result = 31 * result + uspProgramIndex;
        result = 31 * result + sqlProgressIndex;
        result = 31 * result + totalLanguages;
        result = 31 * result + creekUserReputation;
        result = 31 * result + (unlockedCProgramIndexList != null ? unlockedCProgramIndexList.hashCode() : 0);
        result = 31 * result + (unlockedUspProgramIndexList != null ? unlockedUspProgramIndexList.hashCode() : 0);
        result = 31 * result + (unlockedCppProgramIndexList != null ? unlockedCppProgramIndexList.hashCode() : 0);
        result = 31 * result + (unlockedJavaProgramIndexList != null ? unlockedJavaProgramIndexList.hashCode() : 0);
        result = 31 * result + (unlockedSqlProgramIndexList != null ? unlockedSqlProgramIndexList.hashCode() : 0);
        result = 31 * result + (unlockedCLanguageModuleIdList != null ? unlockedCLanguageModuleIdList.hashCode() : 0);
        result = 31 * result + (unlockedUspLanguageModuleIdList != null ? unlockedUspLanguageModuleIdList.hashCode() : 0);
        result = 31 * result + (unlockedCppLanguageModuleIdList != null ? unlockedCppLanguageModuleIdList.hashCode() : 0);
        result = 31 * result + (unlockedJavaLanguageModuleIdList != null ? unlockedJavaLanguageModuleIdList.hashCode() : 0);
        result = 31 * result + (unlockedSqlLanguageModuleIdList != null ? unlockedSqlLanguageModuleIdList.hashCode() : 0);
        result = 31 * result + (unlockedCSyntaxModuleIdList != null ? unlockedCSyntaxModuleIdList.hashCode() : 0);
        result = 31 * result + (unlockedUspSyntaxModuleIdList != null ? unlockedUspSyntaxModuleIdList.hashCode() : 0);
        result = 31 * result + (unlockedCppSyntaxModuleIdList != null ? unlockedCppSyntaxModuleIdList.hashCode() : 0);
        result = 31 * result + (unlockedJavaSyntaxModuleIdList != null ? unlockedJavaSyntaxModuleIdList.hashCode() : 0);
        result = 31 * result + (unlockedSqlSyntaxModuleIdList != null ? unlockedSqlSyntaxModuleIdList.hashCode() : 0);
        result = 31 * result + (unlockedCWikiIdList != null ? unlockedCWikiIdList.hashCode() : 0);
        result = 31 * result + (unlockedUspWikiIdList != null ? unlockedUspWikiIdList.hashCode() : 0);
        result = 31 * result + (unlockedCppWikiIdList != null ? unlockedCppWikiIdList.hashCode() : 0);
        result = 31 * result + (unlockedJavaWikiIdList != null ? unlockedJavaWikiIdList.hashCode() : 0);
        result = 31 * result + (unlockedSqlWikiIdList != null ? unlockedSqlWikiIdList.hashCode() : 0);
        return result;
    }

    public static final int MODULE_SCORE = 15;
    public static final int CHAPTER_SCORE = 25;
    public static final int PROGRAM_SCORE = 30;

    public boolean addToUnlockedCLanguageModuleIdList(String syntaxId) {
        if( !unlockedCLanguageModuleIdList.contains(syntaxId) ) {
            unlockedCLanguageModuleIdList.add(syntaxId);
            creekUserReputation += CHAPTER_SCORE;
            Log.d("CreekUserStats", "Unlocked Language Modules : " + unlockedCLanguageModuleIdList.toString());
            return true;
        }
        return false;
    }

    public boolean addToUnlockedCSyntaxModuleIdList(String s) {
        if( !unlockedCSyntaxModuleIdList.contains(s) ) {
            unlockedCSyntaxModuleIdList.add(s);
            creekUserReputation += MODULE_SCORE;
            return true;
        }
        return false;
    }

    public boolean addToUnlockedCProgramIndexList(int i) {
        if( !unlockedCProgramIndexList.contains(i) ) {
            unlockedCProgramIndexList.add(i);
            creekUserReputation += PROGRAM_SCORE;
            return true;
        }
        return false;
    }

    public boolean addToUnlockedCWikiIdList(String chapterReferenceId) {
        if( !unlockedCWikiIdList.contains(chapterReferenceId) ) {
            unlockedCWikiIdList.add(chapterReferenceId);
            return true;
        }
        return false;
    }

    public boolean addToUnlockedCppLanguageModuleIdList(String syntaxId) {
        if( !unlockedCppLanguageModuleIdList.contains(syntaxId) ) {
            unlockedCppLanguageModuleIdList.add(syntaxId);
            creekUserReputation += CHAPTER_SCORE;
            return true;
        }
        return false;
    }

    public boolean addToUnlockedCppSyntaxModuleIdList(String s) {
        if( !unlockedCppSyntaxModuleIdList.contains(s) ) {
            unlockedCppSyntaxModuleIdList.add(s);
            creekUserReputation += MODULE_SCORE;
            return true;
        }
        return false;
    }

    public boolean addToUnlockedCppProgramIndexList(int i) {
        if( !unlockedCppProgramIndexList.contains(i) ) {
            unlockedCppProgramIndexList.add(i);
            creekUserReputation += PROGRAM_SCORE;
            return true;
        }
        return false;
    }

    public boolean addToUnlockedCppWikiIdList(String chapterReferenceId) {
        if( !unlockedCppWikiIdList.contains(chapterReferenceId) ) {
            unlockedCppWikiIdList.add(chapterReferenceId);
            return true;
        }
        return false;
    }

    public boolean addToUnlockedJavaLanguageModuleIdList(String syntaxId) {
        if( !unlockedJavaLanguageModuleIdList.contains(syntaxId) ) {
            unlockedJavaLanguageModuleIdList.add(syntaxId);
            creekUserReputation += CHAPTER_SCORE;
            return true;
        }
        return false;
    }

    public boolean addToUnlockedSqlLanguageModuleIdList(String syntaxId) {
        if( !unlockedSqlLanguageModuleIdList.contains(syntaxId) ) {
            unlockedSqlLanguageModuleIdList.add(syntaxId);
            creekUserReputation += CHAPTER_SCORE;
            return true;
        }
        return false;
    }

    public boolean addToUnlockedJavaSyntaxModuleIdList(String s) {
        if( !unlockedJavaSyntaxModuleIdList.contains(s) ) {
            unlockedJavaSyntaxModuleIdList.add(s);
            creekUserReputation += MODULE_SCORE;
            return true;
        }
        return false;
    }

    public boolean addToUnlockedSqlSyntaxModuleIdList(String s) {
        if( !unlockedSqlSyntaxModuleIdList.contains(s) ) {
            unlockedSqlSyntaxModuleIdList.add(s);
            creekUserReputation += MODULE_SCORE;
            return true;
        }
        return false;
    }

    public boolean addToUnlockedJavaProgramIndexList(int i) {
        if( !unlockedJavaProgramIndexList.contains(i) ) {
            unlockedJavaProgramIndexList.add(i);
            creekUserReputation += PROGRAM_SCORE;
            return true;
        }
        return false;
    }

    public boolean addToUnlockedSqlProgramIndexList(int i) {
        if( !unlockedSqlProgramIndexList.contains(i) ) {
            unlockedSqlProgramIndexList.add(i);
            creekUserReputation += PROGRAM_SCORE;
            return true;
        }
        return false;
    }

    public boolean addToUnlockedJavaWikiIdList(String chapterReferenceId) {
        if( !unlockedJavaWikiIdList.contains(chapterReferenceId) ) {
            unlockedJavaWikiIdList.add(chapterReferenceId);
            return true;
        }
        return false;
    }

    public boolean addToUnlockedSqlWikiIdList(String chapterReferenceId) {
        if( !unlockedSqlWikiIdList.contains(chapterReferenceId) ) {
            unlockedSqlWikiIdList.add(chapterReferenceId);
            return true;
        }
        return false;
    }

    public boolean addToUnlockedUspProgramIndexList(int i) {
        if( !unlockedUspProgramIndexList.contains(i) ) {
            unlockedUspProgramIndexList.add(i);
            creekUserReputation += PROGRAM_SCORE;
            return true;
        }
        return false;
    }

    public boolean addToUnlockedUspLanguageModuleIdList(String syntaxId) {
        if( !unlockedUspLanguageModuleIdList.contains(syntaxId) ) {
            unlockedUspLanguageModuleIdList.add(syntaxId);
            creekUserReputation += CHAPTER_SCORE;
            return true;
        }
        return false;
    }

    public boolean addToUnlockedUspSyntaxModuleIdList(String s) {
        if( !unlockedUspSyntaxModuleIdList.contains(s) ) {
            unlockedUspSyntaxModuleIdList.add(s);
            creekUserReputation += MODULE_SCORE;
            return true;
        }
        return false;
    }

    public boolean addToUnlockedUspWikiIdList(String chapterReferenceId) {
        if( !unlockedUspWikiIdList.contains(chapterReferenceId) ) {
            unlockedUspWikiIdList.add(chapterReferenceId);
            return true;
        }
        return false;
    }

    public ArrayList<String> getUserAddedPrograms() {
        return userAddedPrograms;
    }

    public void setUserAddedPrograms(ArrayList<String> userAddedPrograms) {
        this.userAddedPrograms = userAddedPrograms;
    }

    public boolean addToUserAddedPrograms(String programId) {
        if( !userAddedPrograms.contains(programId) ) {
            userAddedPrograms.add(programId);
            return true;
        }
        return false;
    }

    public ArrayList<String> getUnlockedUserAddedPrograms() {
        return unlockedUserAddedPrograms;
    }

    public void setUnlockedUserAddedPrograms(ArrayList<String> unlockedUserAddedPrograms) {
        this.unlockedUserAddedPrograms = unlockedUserAddedPrograms;
    }

    public boolean addToUnlockedUserAddedPrograms(String programId) {
        if( !unlockedUserAddedPrograms.contains(programId) ) {
            unlockedUserAddedPrograms.add(programId);
            return true;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.javaProgressIndex);
        dest.writeInt(this.cProgressIndex);
        dest.writeInt(this.cppProgressIndex);
        dest.writeInt(this.uspProgramIndex);
        dest.writeInt(this.sqlProgressIndex);
        dest.writeInt(this.totalLanguages);
        dest.writeList(this.unlockedCProgramIndexList);
        dest.writeList(this.unlockedUspProgramIndexList);
        dest.writeList(this.unlockedCppProgramIndexList);
        dest.writeList(this.unlockedJavaProgramIndexList);
        dest.writeStringList(this.unlockedCLanguageModuleIdList);
        dest.writeStringList(this.unlockedUspLanguageModuleIdList);
        dest.writeStringList(this.unlockedCppLanguageModuleIdList);
        dest.writeStringList(this.unlockedJavaLanguageModuleIdList);
        dest.writeStringList(this.unlockedSqlLanguageModuleIdList);
        dest.writeStringList(this.unlockedCSyntaxModuleIdList);
        dest.writeStringList(this.unlockedUspSyntaxModuleIdList);
        dest.writeStringList(this.unlockedCppSyntaxModuleIdList);
        dest.writeStringList(this.unlockedJavaSyntaxModuleIdList);
        dest.writeStringList(this.unlockedSqlSyntaxModuleIdList);
        dest.writeStringList(this.unlockedCWikiIdList);
        dest.writeStringList(this.unlockedUspWikiIdList);
        dest.writeStringList(this.unlockedCppWikiIdList);
        dest.writeStringList(this.unlockedJavaWikiIdList);
        dest.writeStringList(this.userAddedPrograms);
        dest.writeStringList(this.unlockedUserAddedPrograms);
    }

    protected CreekUserStats(Parcel in) {
        this.javaProgressIndex = in.readInt();
        this.cProgressIndex = in.readInt();
        this.cppProgressIndex = in.readInt();
        this.uspProgramIndex = in.readInt();
        this.sqlProgressIndex = in.readInt();
        this.totalLanguages = in.readInt();
        this.unlockedCProgramIndexList = new ArrayList<Integer>();
        in.readList(this.unlockedCProgramIndexList, Integer.class.getClassLoader());
        this.unlockedUspProgramIndexList = new ArrayList<Integer>();
        in.readList(this.unlockedUspProgramIndexList, Integer.class.getClassLoader());
        this.unlockedCppProgramIndexList = new ArrayList<Integer>();
        in.readList(this.unlockedCppProgramIndexList, Integer.class.getClassLoader());
        this.unlockedJavaProgramIndexList = new ArrayList<Integer>();
        in.readList(this.unlockedJavaProgramIndexList, Integer.class.getClassLoader());
        this.unlockedCLanguageModuleIdList = in.createStringArrayList();
        this.unlockedUspLanguageModuleIdList = in.createStringArrayList();
        this.unlockedCppLanguageModuleIdList = in.createStringArrayList();
        this.unlockedJavaLanguageModuleIdList = in.createStringArrayList();
        this.unlockedSqlLanguageModuleIdList = in.createStringArrayList();
        this.unlockedCSyntaxModuleIdList = in.createStringArrayList();
        this.unlockedUspSyntaxModuleIdList = in.createStringArrayList();
        this.unlockedCppSyntaxModuleIdList = in.createStringArrayList();
        this.unlockedJavaSyntaxModuleIdList = in.createStringArrayList();
        this.unlockedSqlSyntaxModuleIdList = in.createStringArrayList();
        this.unlockedCWikiIdList = in.createStringArrayList();
        this.unlockedUspWikiIdList = in.createStringArrayList();
        this.unlockedCppWikiIdList = in.createStringArrayList();
        this.unlockedJavaWikiIdList = in.createStringArrayList();
        this.userAddedPrograms = in.createStringArrayList();
        this.unlockedUserAddedPrograms = in.createStringArrayList();
    }

    public static final Creator<CreekUserStats> CREATOR = new Creator<CreekUserStats>() {
        @Override
        public CreekUserStats createFromParcel(Parcel source) {
            return new CreekUserStats(source);
        }

        @Override
        public CreekUserStats[] newArray(int size) {
            return new CreekUserStats[size];
        }
    };

    public void calculateReputation() {
        creekUserReputation = 0;

        int userStats = 0;
        /** Chapters unlocked : TODO On New Language*/
        if( getUnlockedCLanguageModuleIdList().size() > 1 ) {
            userStats += getUnlockedCLanguageModuleIdList().size() * CHAPTER_SCORE;
        }
        if( getUnlockedCppLanguageModuleIdList().size() > 1 ) {
            userStats += getUnlockedCppLanguageModuleIdList().size() * CHAPTER_SCORE;
        }
        if( getUnlockedJavaLanguageModuleIdList().size() > 1 ) {
            userStats += getUnlockedJavaLanguageModuleIdList().size() * CHAPTER_SCORE;
        }
        if( getUnlockedUspLanguageModuleIdList().size() > 1 ) {
            userStats += getUnlockedUspLanguageModuleIdList().size() * CHAPTER_SCORE;
        }
        if( getUnlockedSqlLanguageModuleIdList().size() > 1 ) {
            userStats += getUnlockedSqlLanguageModuleIdList().size() * CHAPTER_SCORE;
        }

        /** Program Index unlocked : TODO On New Language*/
        if( getUnlockedCProgramIndexList().size() > 1 ) {
            userStats += getUnlockedCProgramIndexList().size() * PROGRAM_SCORE;
        }
        if( getUnlockedCppProgramIndexList().size() > 1 ) {
            userStats += getUnlockedCppProgramIndexList().size() * PROGRAM_SCORE;
        }
        if( getUnlockedUspProgramIndexList().size() > 1 ) {
            userStats += getUnlockedUspProgramIndexList().size() * PROGRAM_SCORE;
        }
        if( getUnlockedJavaProgramIndexList().size() > 1 ) {
            userStats += getUnlockedJavaProgramIndexList().size() * PROGRAM_SCORE;
        }
        if( getUnlockedSqlProgramIndexList().size() > 1 ) {
            userStats += getUnlockedSqlProgramIndexList().size() * PROGRAM_SCORE;
        }

        /**  Syntax modules unlocked : TODO On New Language*/
        if( getUnlockedCSyntaxModuleIdList().size() > 1 ) {
            userStats += getUnlockedCSyntaxModuleIdList().size() * MODULE_SCORE;
        }
        if( getUnlockedCppSyntaxModuleIdList().size() > 1 ) {
            userStats += getUnlockedCppSyntaxModuleIdList().size() * MODULE_SCORE;
        }
        if( getUnlockedUspSyntaxModuleIdList().size() > 1 ) {
            userStats += getUnlockedUspSyntaxModuleIdList().size() * MODULE_SCORE;
        }
        if( getUnlockedJavaSyntaxModuleIdList().size() > 1 ) {
            userStats += getUnlockedJavaSyntaxModuleIdList().size() * MODULE_SCORE;
        }
        if( getUnlockedSqlSyntaxModuleIdList().size() > 1 ) {
            userStats += getUnlockedSqlSyntaxModuleIdList().size() * MODULE_SCORE;
        }

        if( getUserAddedPrograms().size() > 1 ) {
            userStats += getUserAddedPrograms().size() * MODULE_SCORE;
        }
        if( getUnlockedUserAddedPrograms().size() > 1 ) {
            userStats += getUnlockedUserAddedPrograms().size() * PROGRAM_SCORE;
        }
        creekUserReputation += userStats;

    }
}

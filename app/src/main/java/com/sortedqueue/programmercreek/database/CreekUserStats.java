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

    private ArrayList<Integer> unlockedCProgramIndexList = new ArrayList<>();
    private ArrayList<Integer> unlockedCppProgramIndexList = new ArrayList<>();
    private ArrayList<Integer> unlockedJavaProgramIndexList = new ArrayList<>();

    private ArrayList<String> unlockedCLanguageModuleIdList = new ArrayList<>();
    private ArrayList<String> unlockedCppLanguageModuleIdList = new ArrayList<>();
    private ArrayList<String> unlockedJavaLanguageModuleIdList = new ArrayList<>();

    private ArrayList<String> unlockedCSyntaxModuleIdList = new ArrayList<>();
    private ArrayList<String> unlockedCppSyntaxModuleIdList = new ArrayList<>();
    private ArrayList<String> unlockedJavaSyntaxModuleIdList = new ArrayList<>();

    private ArrayList<String> unlockedCWikiIdList = new ArrayList<>();
    private ArrayList<String> unlockedCppWikiIdList = new ArrayList<>();
    private ArrayList<String> unlockedJavaWikiIdList = new ArrayList<>();

    public CreekUserStats(int javaProgressIndex, int cProgressIndex, int cppProgressIndex) {
        this.javaProgressIndex = javaProgressIndex;
        this.cProgressIndex = cProgressIndex;
        this.cppProgressIndex = cppProgressIndex;
    }

    public CreekUserStats() {
    }

    public int getJavaProgressIndex() {
        return javaProgressIndex;
    }

    public void setJavaProgressIndex(int javaProgressIndex) {
        this.javaProgressIndex = javaProgressIndex;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreekUserStats that = (CreekUserStats) o;

        if (javaProgressIndex != that.javaProgressIndex) return false;
        if (cProgressIndex != that.cProgressIndex) return false;
        if (cppProgressIndex != that.cppProgressIndex) return false;
        if (unlockedCProgramIndexList != null ? !unlockedCProgramIndexList.equals(that.unlockedCProgramIndexList) : that.unlockedCProgramIndexList != null)
            return false;
        if (unlockedCppProgramIndexList != null ? !unlockedCppProgramIndexList.equals(that.unlockedCppProgramIndexList) : that.unlockedCppProgramIndexList != null)
            return false;
        if (unlockedJavaProgramIndexList != null ? !unlockedJavaProgramIndexList.equals(that.unlockedJavaProgramIndexList) : that.unlockedJavaProgramIndexList != null)
            return false;
        if (unlockedCLanguageModuleIdList != null ? !unlockedCLanguageModuleIdList.equals(that.unlockedCLanguageModuleIdList) : that.unlockedCLanguageModuleIdList != null)
            return false;
        if (unlockedCppLanguageModuleIdList != null ? !unlockedCppLanguageModuleIdList.equals(that.unlockedCppLanguageModuleIdList) : that.unlockedCppLanguageModuleIdList != null)
            return false;
        if (unlockedJavaLanguageModuleIdList != null ? !unlockedJavaLanguageModuleIdList.equals(that.unlockedJavaLanguageModuleIdList) : that.unlockedJavaLanguageModuleIdList != null)
            return false;
        if (unlockedCSyntaxModuleIdList != null ? !unlockedCSyntaxModuleIdList.equals(that.unlockedCSyntaxModuleIdList) : that.unlockedCSyntaxModuleIdList != null)
            return false;
        if (unlockedCppSyntaxModuleIdList != null ? !unlockedCppSyntaxModuleIdList.equals(that.unlockedCppSyntaxModuleIdList) : that.unlockedCppSyntaxModuleIdList != null)
            return false;
        if (unlockedJavaSyntaxModuleIdList != null ? !unlockedJavaSyntaxModuleIdList.equals(that.unlockedJavaSyntaxModuleIdList) : that.unlockedJavaSyntaxModuleIdList != null)
            return false;
        if (unlockedCWikiIdList != null ? !unlockedCWikiIdList.equals(that.unlockedCWikiIdList) : that.unlockedCWikiIdList != null)
            return false;
        if (unlockedCppWikiIdList != null ? !unlockedCppWikiIdList.equals(that.unlockedCppWikiIdList) : that.unlockedCppWikiIdList != null)
            return false;
        return unlockedJavaWikiIdList != null ? unlockedJavaWikiIdList.equals(that.unlockedJavaWikiIdList) : that.unlockedJavaWikiIdList == null;

    }

    @Override
    public int hashCode() {
        int result = javaProgressIndex;
        result = 31 * result + cProgressIndex;
        result = 31 * result + cppProgressIndex;
        result = 31 * result + (unlockedCProgramIndexList != null ? unlockedCProgramIndexList.hashCode() : 0);
        result = 31 * result + (unlockedCppProgramIndexList != null ? unlockedCppProgramIndexList.hashCode() : 0);
        result = 31 * result + (unlockedJavaProgramIndexList != null ? unlockedJavaProgramIndexList.hashCode() : 0);
        result = 31 * result + (unlockedCLanguageModuleIdList != null ? unlockedCLanguageModuleIdList.hashCode() : 0);
        result = 31 * result + (unlockedCppLanguageModuleIdList != null ? unlockedCppLanguageModuleIdList.hashCode() : 0);
        result = 31 * result + (unlockedJavaLanguageModuleIdList != null ? unlockedJavaLanguageModuleIdList.hashCode() : 0);
        result = 31 * result + (unlockedCSyntaxModuleIdList != null ? unlockedCSyntaxModuleIdList.hashCode() : 0);
        result = 31 * result + (unlockedCppSyntaxModuleIdList != null ? unlockedCppSyntaxModuleIdList.hashCode() : 0);
        result = 31 * result + (unlockedJavaSyntaxModuleIdList != null ? unlockedJavaSyntaxModuleIdList.hashCode() : 0);
        result = 31 * result + (unlockedCWikiIdList != null ? unlockedCWikiIdList.hashCode() : 0);
        result = 31 * result + (unlockedCppWikiIdList != null ? unlockedCppWikiIdList.hashCode() : 0);
        result = 31 * result + (unlockedJavaWikiIdList != null ? unlockedJavaWikiIdList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CreekUserStats{" +
                "javaProgressIndex=" + javaProgressIndex +
                ", cProgressIndex=" + cProgressIndex +
                ", cppProgressIndex=" + cppProgressIndex +
                ", unlockedCProgramIndexList=" + unlockedCProgramIndexList +
                ", unlockedCppProgramIndexList=" + unlockedCppProgramIndexList +
                ", unlockedJavaProgramIndexList=" + unlockedJavaProgramIndexList +
                ", unlockedCLanguageModuleIdList=" + unlockedCLanguageModuleIdList +
                ", unlockedCppLanguageModuleIdList=" + unlockedCppLanguageModuleIdList +
                ", unlockedJavaLanguageModuleIdList=" + unlockedJavaLanguageModuleIdList +
                ", unlockedCSyntaxModuleIdList=" + unlockedCSyntaxModuleIdList +
                ", unlockedCppSyntaxModuleIdList=" + unlockedCppSyntaxModuleIdList +
                ", unlockedJavaSyntaxModuleIdList=" + unlockedJavaSyntaxModuleIdList +
                ", unlockedCWikiIdList=" + unlockedCWikiIdList +
                ", unlockedCppWikiIdList=" + unlockedCppWikiIdList +
                ", unlockedJavaWikiIdList=" + unlockedJavaWikiIdList +
                '}';
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
        dest.writeList(this.unlockedCProgramIndexList);
        dest.writeList(this.unlockedCppProgramIndexList);
        dest.writeList(this.unlockedJavaProgramIndexList);
        dest.writeStringList(this.unlockedCLanguageModuleIdList);
        dest.writeStringList(this.unlockedCppLanguageModuleIdList);
        dest.writeStringList(this.unlockedJavaLanguageModuleIdList);
        dest.writeStringList(this.unlockedCSyntaxModuleIdList);
        dest.writeStringList(this.unlockedCppSyntaxModuleIdList);
        dest.writeStringList(this.unlockedJavaSyntaxModuleIdList);
        dest.writeStringList(this.unlockedCWikiIdList);
        dest.writeStringList(this.unlockedCppWikiIdList);
        dest.writeStringList(this.unlockedJavaWikiIdList);
    }

    protected CreekUserStats(Parcel in) {
        this.javaProgressIndex = in.readInt();
        this.cProgressIndex = in.readInt();
        this.cppProgressIndex = in.readInt();
        this.unlockedCProgramIndexList = new ArrayList<Integer>();
        in.readList(this.unlockedCProgramIndexList, Integer.class.getClassLoader());
        this.unlockedCppProgramIndexList = new ArrayList<Integer>();
        in.readList(this.unlockedCppProgramIndexList, Integer.class.getClassLoader());
        this.unlockedJavaProgramIndexList = new ArrayList<Integer>();
        in.readList(this.unlockedJavaProgramIndexList, Integer.class.getClassLoader());
        this.unlockedCLanguageModuleIdList = in.createStringArrayList();
        this.unlockedCppLanguageModuleIdList = in.createStringArrayList();
        this.unlockedJavaLanguageModuleIdList = in.createStringArrayList();
        this.unlockedCSyntaxModuleIdList = in.createStringArrayList();
        this.unlockedCppSyntaxModuleIdList = in.createStringArrayList();
        this.unlockedJavaSyntaxModuleIdList = in.createStringArrayList();
        this.unlockedCWikiIdList = in.createStringArrayList();
        this.unlockedCppWikiIdList = in.createStringArrayList();
        this.unlockedJavaWikiIdList = in.createStringArrayList();
    }

    public static final Parcelable.Creator<CreekUserStats> CREATOR = new Parcelable.Creator<CreekUserStats>() {
        @Override
        public CreekUserStats createFromParcel(Parcel source) {
            return new CreekUserStats(source);
        }

        @Override
        public CreekUserStats[] newArray(int size) {
            return new CreekUserStats[size];
        }
    };

    public void addToUnlockedCLanguageModuleIdList(String syntaxId) {
        if( !unlockedCLanguageModuleIdList.contains(syntaxId) ) {
            unlockedCLanguageModuleIdList.add(syntaxId);
            Log.d("CreekUserStats", "Unlocked Language Modules : " + unlockedCLanguageModuleIdList.toString());
        }
    }

    public void addToUnlockedCSyntaxModuleIdList(String s) {
        if( !unlockedCSyntaxModuleIdList.contains(s) ) {
            unlockedCSyntaxModuleIdList.add(s);
        }
    }

    public void addToUnlockedCProgramIndexList(int i) {
        if( !unlockedCProgramIndexList.contains(i) ) {
            unlockedCProgramIndexList.add(i);
        }
    }

    public void addToUnlockedCWikiIdList(String chapterReferenceId) {
        if( !unlockedCWikiIdList.contains(chapterReferenceId) ) {
            unlockedCWikiIdList.add(chapterReferenceId);
        }
    }

    public void addToUnlockedCppLanguageModuleIdList(String syntaxId) {
        if( !unlockedCppLanguageModuleIdList.contains(syntaxId) ) {
            unlockedCppLanguageModuleIdList.add(syntaxId);
        }
    }

    public void addToUnlockedCppSyntaxModuleIdList(String s) {
        if( !unlockedCppSyntaxModuleIdList.contains(s) ) {
            unlockedCppSyntaxModuleIdList.add(s);
        }
    }

    public void addToUnlockedCppProgramIndexList(int i) {
        if( !unlockedCppProgramIndexList.contains(i) ) {
            unlockedCppProgramIndexList.add(i);
        }
    }

    public void addToUnlockedCppWikiIdList(String chapterReferenceId) {
        if( !unlockedCppWikiIdList.contains(chapterReferenceId) ) {
            unlockedCppWikiIdList.add(chapterReferenceId);
        }
    }

    public void addToUnlockedJavaLanguageModuleIdList(String syntaxId) {
        if( !unlockedJavaLanguageModuleIdList.contains(syntaxId) ) {
            unlockedJavaLanguageModuleIdList.add(syntaxId);
        }
    }

    public void addToUnlockedJavaSyntaxModuleIdList(String s) {
        if( !unlockedJavaSyntaxModuleIdList.contains(s) ) {
            unlockedJavaSyntaxModuleIdList.add(s);
        }
    }

    public void addToUnlockedJavaProgramIndexList(int i) {
        if( !unlockedJavaProgramIndexList.contains(i) ) {
            unlockedJavaProgramIndexList.add(i);
        }
    }

    public void addToUnlockedJavaWikiIdList(String chapterReferenceId) {
        if( !unlockedJavaWikiIdList.contains(chapterReferenceId) ) {
            unlockedJavaWikiIdList.add(chapterReferenceId);
        }
    }
}

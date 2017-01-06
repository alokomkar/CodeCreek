package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushList;

/**
 * Created by Alok on 05/01/17.
 */

public class Chapter extends RushObject implements Parcelable {

    private String program_Language;
    private String chapterId;
    private String chapterName;
    private String chapteBrief;

    @RushList(classType = ChapterDetails.class)
    private ArrayList<ChapterDetails> wizardModules = new ArrayList<>();

    public Chapter() {
    }

    public Chapter(String program_Language, String chapterId, String chapterName, String chapteBrief, ArrayList<ChapterDetails> wizardModules) {
        this.program_Language = program_Language;
        this.chapterId = chapterId;
        this.chapteBrief = chapteBrief;
        this.chapterName = chapterName;
        this.wizardModules = wizardModules;
    }

    public String getProgram_Language() {
        return program_Language;
    }

    public void setProgram_Language(String program_Language) {
        this.program_Language = program_Language;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public ArrayList<ChapterDetails> getWizardModules() {
        return wizardModules;
    }

    public void setWizardModules(ArrayList<ChapterDetails> wizardModules) {
        this.wizardModules = wizardModules;
    }

    public String getChapteBrief() {
        return chapteBrief;
    }

    public void setChapteBrief(String chapteBrief) {
        this.chapteBrief = chapteBrief;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chapter that = (Chapter) o;

        if (program_Language != null ? !program_Language.equals(that.program_Language) : that.program_Language != null)
            return false;
        if (chapterId != null ? !chapterId.equals(that.chapterId) : that.chapterId != null)
            return false;
        if (chapterName != null ? !chapterName.equals(that.chapterName) : that.chapterName != null)
            return false;
        return wizardModules != null ? wizardModules.equals(that.wizardModules) : that.wizardModules == null;

    }

    @Override
    public int hashCode() {
        int result = program_Language != null ? program_Language.hashCode() : 0;
        result = 31 * result + (chapterId != null ? chapterId.hashCode() : 0);
        result = 31 * result + (chapterName != null ? chapterName.hashCode() : 0);
        result = 31 * result + (wizardModules != null ? wizardModules.hashCode() : 0);
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.program_Language);
        dest.writeString(this.chapterId);
        dest.writeString(this.chapterName);
        dest.writeString(this.chapteBrief);
        dest.writeTypedList(this.wizardModules);
    }

    protected Chapter(Parcel in) {
        this.program_Language = in.readString();
        this.chapterId = in.readString();
        this.chapterName = in.readString();
        this.chapteBrief = in.readString();
        this.wizardModules = in.createTypedArrayList(ChapterDetails.CREATOR);
    }

    public static final Parcelable.Creator<Chapter> CREATOR = new Parcelable.Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel source) {
            return new Chapter(source);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };
}

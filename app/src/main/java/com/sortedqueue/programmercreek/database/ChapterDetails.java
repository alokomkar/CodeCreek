package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushTableAnnotation;

/**
 * Created by Alok on 05/01/17.
 */
@RushTableAnnotation
public class ChapterDetails extends RushObject implements Parcelable {

    private int chapterModuleIndex;
    private int chapterType;
    private String chapterReferenceId;
    private String syntaxId;
    private String programLanguage;
    private int chapterTestType; //match, test, quiz
    private int progressIndex;

    public static final int TYPE_WIKI = 1;
    public static final int TYPE_PROGRAM_INDEX = 2;
    public static final int TYPE_LANGUAGE_MODULE = 3;
    public static final int TYPE_SYNTAX_MODULE = 3;

    public ChapterDetails() {
    }

    public ChapterDetails(int chapterModuleIndex, int chapterType, String chapterReferenceId, String programLanguage, int progressIndex ) {
        this.chapterModuleIndex = chapterModuleIndex;
        this.chapterType = chapterType;
        this.chapterReferenceId = chapterReferenceId;
        this.programLanguage = programLanguage;
        this.progressIndex = progressIndex;
    }

    public int getProgressIndex() {
        return progressIndex;
    }

    public void setProgressIndex(int progressIndex) {
        this.progressIndex = progressIndex;
    }

    public String getSyntaxId() {
        return syntaxId;
    }

    public void setSyntaxId(String syntaxId) {
        this.syntaxId = syntaxId;
    }

    public int getChapterModuleIndex() {
        return chapterModuleIndex;
    }

    public void setChapterModuleIndex(int chapterModuleIndex) {
        this.chapterModuleIndex = chapterModuleIndex;
    }

    public int getChapterType() {
        return chapterType;
    }

    public void setChapterType(int chapterType) {
        this.chapterType = chapterType;
    }

    public String getChapterReferenceId() {
        return chapterReferenceId;
    }

    public void setChapterReferenceId(String chapterReferenceId) {
        this.chapterReferenceId = chapterReferenceId;
    }

    public String getProgramLanguage() {
        return programLanguage;
    }

    public void setProgramLanguage(String programLanguage) {
        this.programLanguage = programLanguage;
    }

    public int getChapterTestType() {
        return chapterTestType;
    }

    public void setChapterTestType(int chapterTestType) {
        this.chapterTestType = chapterTestType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.chapterModuleIndex);
        dest.writeInt(this.chapterType);
        dest.writeString(this.chapterReferenceId);
        dest.writeString(this.syntaxId);
        dest.writeString(this.programLanguage);
        dest.writeInt(this.chapterTestType);
        dest.writeInt(this.progressIndex);
    }

    protected ChapterDetails(Parcel in) {
        this.chapterModuleIndex = in.readInt();
        this.chapterType = in.readInt();
        this.chapterReferenceId = in.readString();
        this.syntaxId = in.readString();
        this.programLanguage = in.readString();
        this.chapterTestType = in.readInt();
        this.progressIndex = in.readInt();
    }

    public static final Parcelable.Creator<ChapterDetails> CREATOR = new Parcelable.Creator<ChapterDetails>() {
        @Override
        public ChapterDetails createFromParcel(Parcel source) {
            return new ChapterDetails(source);
        }

        @Override
        public ChapterDetails[] newArray(int size) {
            return new ChapterDetails[size];
        }
    };

    @Override
    public String toString() {
        return "ChapterDetails{" +
                "chapterModuleIndex=" + chapterModuleIndex +
                ", chapterType=" + chapterType +
                ", chapterReferenceId='" + chapterReferenceId + '\'' +
                ", syntaxId='" + syntaxId + '\'' +
                ", programLanguage='" + programLanguage + '\'' +
                ", chapterTestType=" + chapterTestType +
                ", progressIndex=" + progressIndex +
                '}';
    }
}

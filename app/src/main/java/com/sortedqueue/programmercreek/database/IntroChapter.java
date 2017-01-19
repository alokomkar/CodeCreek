package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushTableAnnotation;

/**
 * Created by Alok on 19/01/17.
 */
@RushTableAnnotation
public class IntroChapter extends RushObject implements Parcelable {

    private int chapterIndex;
    private String chapterId;
    private String chapterHeader;
    private String chapterIntro;
    private String chapterNote;
    private String chapterProgram;
    private String chapterProgramOutput;
    private String chapterProgramDescription;
    private String chapterLanguage;

    public IntroChapter(int chapterIndex, String chapterId, String chapterHeader,
                        String chapterIntro, String chapterNote, String chapterProgram,
                        String chapterProgramOutput, String chapterProgramDescription, String chapterLanguage) {
        this.chapterIndex = chapterIndex;
        this.chapterId = chapterId;
        this.chapterHeader = chapterHeader;
        this.chapterIntro = chapterIntro;
        this.chapterNote = chapterNote;
        this.chapterProgram = chapterProgram;
        this.chapterProgramOutput = chapterProgramOutput;
        this.chapterProgramDescription = chapterProgramDescription;
        this.chapterLanguage = chapterLanguage;
    }

    public IntroChapter() {
    }

    public int getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterHeader() {
        return chapterHeader;
    }

    public void setChapterHeader(String chapterHeader) {
        this.chapterHeader = chapterHeader;
    }

    public String getChapterIntro() {
        return chapterIntro;
    }

    public void setChapterIntro(String chapterIntro) {
        this.chapterIntro = chapterIntro;
    }

    public String getChapterNote() {
        return chapterNote;
    }

    public void setChapterNote(String chapterNote) {
        this.chapterNote = chapterNote;
    }

    public String getChapterProgram() {
        return chapterProgram;
    }

    public void setChapterProgram(String chapterProgram) {
        this.chapterProgram = chapterProgram;
    }

    public String getChapterProgramDescription() {
        return chapterProgramDescription;
    }

    public void setChapterProgramDescription(String chapterProgramDescription) {
        this.chapterProgramDescription = chapterProgramDescription;
    }

    public String getChapterLanguage() {
        return chapterLanguage;
    }

    public void setChapterLanguage(String chapterLanguage) {
        this.chapterLanguage = chapterLanguage;
    }

    public String getChapterProgramOutput() {
        return chapterProgramOutput;
    }

    public void setChapterProgramOutput(String chapterProgramOutput) {
        this.chapterProgramOutput = chapterProgramOutput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntroChapter that = (IntroChapter) o;

        if (chapterIndex != that.chapterIndex) return false;
        if (chapterId != null ? !chapterId.equals(that.chapterId) : that.chapterId != null)
            return false;
        if (chapterHeader != null ? !chapterHeader.equals(that.chapterHeader) : that.chapterHeader != null)
            return false;
        if (chapterIntro != null ? !chapterIntro.equals(that.chapterIntro) : that.chapterIntro != null)
            return false;
        if (chapterNote != null ? !chapterNote.equals(that.chapterNote) : that.chapterNote != null)
            return false;
        if (chapterProgram != null ? !chapterProgram.equals(that.chapterProgram) : that.chapterProgram != null)
            return false;
        if (chapterProgramOutput != null ? !chapterProgramOutput.equals(that.chapterProgramOutput) : that.chapterProgramOutput != null)
            return false;
        if (chapterProgramDescription != null ? !chapterProgramDescription.equals(that.chapterProgramDescription) : that.chapterProgramDescription != null)
            return false;
        return chapterLanguage != null ? chapterLanguage.equals(that.chapterLanguage) : that.chapterLanguage == null;

    }

    @Override
    public int hashCode() {
        int result = chapterIndex;
        result = 31 * result + (chapterId != null ? chapterId.hashCode() : 0);
        result = 31 * result + (chapterHeader != null ? chapterHeader.hashCode() : 0);
        result = 31 * result + (chapterIntro != null ? chapterIntro.hashCode() : 0);
        result = 31 * result + (chapterNote != null ? chapterNote.hashCode() : 0);
        result = 31 * result + (chapterProgram != null ? chapterProgram.hashCode() : 0);
        result = 31 * result + (chapterProgramOutput != null ? chapterProgramOutput.hashCode() : 0);
        result = 31 * result + (chapterProgramDescription != null ? chapterProgramDescription.hashCode() : 0);
        result = 31 * result + (chapterLanguage != null ? chapterLanguage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IntroChapter{" +
                "chapterIndex=" + chapterIndex +
                ", chapterId='" + chapterId + '\'' +
                ", chapterHeader='" + chapterHeader + '\'' +
                ", chapterIntro='" + chapterIntro + '\'' +
                ", chapterNote='" + chapterNote + '\'' +
                ", chapterProgram='" + chapterProgram + '\'' +
                ", chapterProgramOutput='" + chapterProgramOutput + '\'' +
                ", chapterProgramDescription='" + chapterProgramDescription + '\'' +
                ", chapterLanguage='" + chapterLanguage + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.chapterIndex);
        dest.writeString(this.chapterId);
        dest.writeString(this.chapterHeader);
        dest.writeString(this.chapterIntro);
        dest.writeString(this.chapterNote);
        dest.writeString(this.chapterProgram);
        dest.writeString(this.chapterProgramOutput);
        dest.writeString(this.chapterProgramDescription);
        dest.writeString(this.chapterLanguage);
    }

    protected IntroChapter(Parcel in) {
        this.chapterIndex = in.readInt();
        this.chapterId = in.readString();
        this.chapterHeader = in.readString();
        this.chapterIntro = in.readString();
        this.chapterNote = in.readString();
        this.chapterProgram = in.readString();
        this.chapterProgramOutput = in.readString();
        this.chapterProgramDescription = in.readString();
        this.chapterLanguage = in.readString();
    }

    public static final Creator<IntroChapter> CREATOR = new Creator<IntroChapter>() {
        @Override
        public IntroChapter createFromParcel(Parcel source) {
            return new IntroChapter(source);
        }

        @Override
        public IntroChapter[] newArray(int size) {
            return new IntroChapter[size];
        }
    };
}

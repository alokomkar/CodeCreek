package com.sortedqueue.programmercreek.database.lessons;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Alok on 28/08/17.
 */

public class Lesson implements Parcelable {

    private String programLanguage;
    private String lessonId;
    private String title;

    private ArrayList<BitModule> bitModules;

    public Lesson() {
    }

    public Lesson(String programLanguage, String lessonId, String title, ArrayList<BitModule> bitModules) {
        this.programLanguage = programLanguage;
        this.lessonId = lessonId;
        this.title = title;
        this.bitModules = bitModules;
    }

    public String getProgramLanguage() {
        return programLanguage;
    }

    public void setProgramLanguage(String programLanguage) {
        this.programLanguage = programLanguage;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<BitModule> getBitModules() {
        return bitModules;
    }

    public void setBitModules(ArrayList<BitModule> bitModules) {
        this.bitModules = bitModules;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.programLanguage);
        dest.writeString(this.lessonId);
        dest.writeString(this.title);
        dest.writeTypedList(this.bitModules);
    }

    protected Lesson(Parcel in) {
        this.programLanguage = in.readString();
        this.lessonId = in.readString();
        this.title = in.readString();
        this.bitModules = in.createTypedArrayList(BitModule.CREATOR);
    }

    public static final Parcelable.Creator<Lesson> CREATOR = new Parcelable.Creator<Lesson>() {
        @Override
        public Lesson createFromParcel(Parcel source) {
            return new Lesson(source);
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lesson lesson = (Lesson) o;

        if (programLanguage != null ? !programLanguage.equals(lesson.programLanguage) : lesson.programLanguage != null)
            return false;
        if (lessonId != null ? !lessonId.equals(lesson.lessonId) : lesson.lessonId != null)
            return false;
        if (title != null ? !title.equals(lesson.title) : lesson.title != null) return false;
        return bitModules != null ? bitModules.equals(lesson.bitModules) : lesson.bitModules == null;
    }

    @Override
    public int hashCode() {
        int result = programLanguage != null ? programLanguage.hashCode() : 0;
        result = 31 * result + (lessonId != null ? lessonId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (bitModules != null ? bitModules.hashCode() : 0);
        return result;
    }
}

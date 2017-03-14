package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushTableAnnotation;

/**
 * Created by Alok Omkar on 2017-03-14.
 */
@RushTableAnnotation
public class AlgorithmsIndex extends RushObject implements Parcelable {

    private int programIndex;
    private String programTitle;
    private String programDescription;
    private String programLanguage;

    public AlgorithmsIndex(int programIndex, String programTitle, String programDescription, String programLanguage) {
        this.programIndex = programIndex;
        this.programTitle = programTitle;
        this.programDescription = programDescription;
        this.programLanguage = programLanguage;
    }

    public AlgorithmsIndex() {
    }

    public int getProgramIndex() {
        return programIndex;
    }

    public void setProgramIndex(int programIndex) {
        this.programIndex = programIndex;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }

    public String getProgramLanguage() {
        return programLanguage;
    }

    public void setProgramLanguage(String programLanguage) {
        this.programLanguage = programLanguage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlgorithmsIndex that = (AlgorithmsIndex) o;

        if (programIndex != that.programIndex) return false;
        if (programTitle != null ? !programTitle.equals(that.programTitle) : that.programTitle != null)
            return false;
        if (programDescription != null ? !programDescription.equals(that.programDescription) : that.programDescription != null)
            return false;
        return programLanguage != null ? programLanguage.equals(that.programLanguage) : that.programLanguage == null;

    }

    @Override
    public int hashCode() {
        int result = programIndex;
        result = 31 * result + (programTitle != null ? programTitle.hashCode() : 0);
        result = 31 * result + (programDescription != null ? programDescription.hashCode() : 0);
        result = 31 * result + (programLanguage != null ? programLanguage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AlgorithmsIndex{" +
                "programIndex=" + programIndex +
                ", programTitle='" + programTitle + '\'' +
                ", programDescription='" + programDescription + '\'' +
                ", programLanguage='" + programLanguage + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.programIndex);
        dest.writeString(this.programTitle);
        dest.writeString(this.programDescription);
        dest.writeString(this.programLanguage);
    }

    protected AlgorithmsIndex(Parcel in) {
        this.programIndex = in.readInt();
        this.programTitle = in.readString();
        this.programDescription = in.readString();
        this.programLanguage = in.readString();
    }

    public static final Parcelable.Creator<AlgorithmsIndex> CREATOR = new Parcelable.Creator<AlgorithmsIndex>() {
        @Override
        public AlgorithmsIndex createFromParcel(Parcel source) {
            return new AlgorithmsIndex(source);
        }

        @Override
        public AlgorithmsIndex[] newArray(int size) {
            return new AlgorithmsIndex[size];
        }
    };
}

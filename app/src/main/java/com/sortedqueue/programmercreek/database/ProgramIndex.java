package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import co.uk.rushorm.core.RushObject;

/**
 * Created by Alok on 04/01/17.
 */

public class ProgramIndex extends RushObject implements Parcelable {

    private int index;
    private String program_Description;
    private String program_Language;
    private String wiki;

    public ProgramIndex() {
    }

    public ProgramIndex(int index, String program_Description, String program_Language, String wiki) {
        this.index = index;
        this.program_Description = program_Description;
        this.program_Language = program_Language;
        this.wiki = wiki;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getProgram_Description() {
        return program_Description;
    }

    public void setProgram_Description(String program_Description) {
        this.program_Description = program_Description;
    }

    public String getProgram_Language() {
        return program_Language;
    }

    public void setProgram_Language(String program_Language) {
        this.program_Language = program_Language;
    }

    public String getWiki() {
        return wiki;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProgramIndex that = (ProgramIndex) o;

        if (index != that.index) return false;
        if (program_Description != null ? !program_Description.equals(that.program_Description) : that.program_Description != null)
            return false;
        if (program_Language != null ? !program_Language.equals(that.program_Language) : that.program_Language != null)
            return false;
        return wiki != null ? wiki.equals(that.wiki) : that.wiki == null;

    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + (program_Description != null ? program_Description.hashCode() : 0);
        result = 31 * result + (program_Language != null ? program_Language.hashCode() : 0);
        result = 31 * result + (wiki != null ? wiki.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProgramIndex{" +
                "index=" + index +
                ", program_Description='" + program_Description + '\'' +
                ", program_Language='" + program_Language + '\'' +
                ", wiki='" + wiki + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.index);
        dest.writeString(this.program_Description);
        dest.writeString(this.program_Language);
        dest.writeString(this.wiki);
    }

    protected ProgramIndex(Parcel in) {
        this.index = in.readInt();
        this.program_Description = in.readString();
        this.program_Language = in.readString();
        this.wiki = in.readString();
    }

    public static final Parcelable.Creator<ProgramIndex> CREATOR = new Parcelable.Creator<ProgramIndex>() {
        @Override
        public ProgramIndex createFromParcel(Parcel source) {
            return new ProgramIndex(source);
        }

        @Override
        public ProgramIndex[] newArray(int size) {
            return new ProgramIndex[size];
        }
    };
}

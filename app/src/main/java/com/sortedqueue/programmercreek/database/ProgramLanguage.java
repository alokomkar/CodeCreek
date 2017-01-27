package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import co.uk.rushorm.core.RushObject;

/**
 * Created by Alok on 27/01/17.
 */

public class ProgramLanguage extends RushObject implements Parcelable {

    private String programLanguage;
    private String languageId;
    private String description;

    public ProgramLanguage(String programLanguage, String languageId, String description) {
        this.programLanguage = programLanguage;
        this.languageId = languageId;
        this.description = description;
    }

    public ProgramLanguage() {
    }

    public String getProgramLanguage() {
        return programLanguage;
    }

    public void setProgramLanguage(String programLanguage) {
        this.programLanguage = programLanguage;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProgramLanguage that = (ProgramLanguage) o;

        if (programLanguage != null ? !programLanguage.equals(that.programLanguage) : that.programLanguage != null)
            return false;
        if (languageId != null ? !languageId.equals(that.languageId) : that.languageId != null)
            return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = programLanguage != null ? programLanguage.hashCode() : 0;
        result = 31 * result + (languageId != null ? languageId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.programLanguage);
        dest.writeString(this.languageId);
        dest.writeString(this.description);
    }

    protected ProgramLanguage(Parcel in) {
        this.programLanguage = in.readString();
        this.languageId = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<ProgramLanguage> CREATOR = new Parcelable.Creator<ProgramLanguage>() {
        @Override
        public ProgramLanguage createFromParcel(Parcel source) {
            return new ProgramLanguage(source);
        }

        @Override
        public ProgramLanguage[] newArray(int size) {
            return new ProgramLanguage[size];
        }
    };
}

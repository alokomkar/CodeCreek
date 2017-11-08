package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushTableAnnotation;

/**
 * Created by Alok Omkar on 2016-12-25.
 */
@RushTableAnnotation
public class ModuleOption extends RushObject implements Parcelable {

    private int optionId;
    private String option;
    private String syntaxOptionId;

    public ModuleOption(int optionId, String option) {
        this.optionId = optionId;
        this.option = option;
    }

    public ModuleOption() {
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getSyntaxOptionId() {
        return syntaxOptionId;
    }

    public void setSyntaxOptionId(String syntaxOptionId) {
        this.syntaxOptionId = syntaxOptionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModuleOption that = (ModuleOption) o;

        if (optionId != that.optionId) return false;
        if (option != null ? !option.equals(that.option) : that.option != null) return false;
        return syntaxOptionId != null ? syntaxOptionId.equals(that.syntaxOptionId) : that.syntaxOptionId == null;
    }

    @Override
    public int hashCode() {
        int result = optionId;
        result = 31 * result + (option != null ? option.hashCode() : 0);
        result = 31 * result + (syntaxOptionId != null ? syntaxOptionId.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.optionId);
        dest.writeString(this.option);
        dest.writeString(this.syntaxOptionId);
    }

    protected ModuleOption(Parcel in) {
        this.optionId = in.readInt();
        this.option = in.readString();
        this.syntaxOptionId = in.readString();
    }

    public static final Creator<ModuleOption> CREATOR = new Creator<ModuleOption>() {
        @Override
        public ModuleOption createFromParcel(Parcel source) {
            return new ModuleOption(source);
        }

        @Override
        public ModuleOption[] newArray(int size) {
            return new ModuleOption[size];
        }
    };
}

package com.sortedqueue.programmercreek.database.firebase;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alok Omkar on 2017-03-25.
 */

public class Code implements Parcelable {

    @SerializedName("language")
    @Expose
    private int language;
    @SerializedName("sourceCode")
    @Expose
    private String sourceCode;

    public Code(int language, String sourceCode) {
        this.language = language;
        this.sourceCode = sourceCode;
    }

    public Code() {
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Code code = (Code) o;

        if (language != code.language) return false;
        return sourceCode != null ? sourceCode.equals(code.sourceCode) : code.sourceCode == null;

    }

    @Override
    public int hashCode() {
        int result = language;
        result = 31 * result + (sourceCode != null ? sourceCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Code{" +
                "language=" + language +
                ", sourceCode='" + sourceCode + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.language);
        dest.writeString(this.sourceCode);
    }

    protected Code(Parcel in) {
        this.language = in.readInt();
        this.sourceCode = in.readString();
    }

    public static final Parcelable.Creator<Code> CREATOR = new Parcelable.Creator<Code>() {
        @Override
        public Code createFromParcel(Parcel source) {
            return new Code(source);
        }

        @Override
        public Code[] newArray(int size) {
            return new Code[size];
        }
    };
}

package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseError;

import co.uk.rushorm.core.RushObject;

/**
 * Created by Alok Omkar on 2017-01-01.
 */

public class CreekUserStats implements Parcelable {

    private int javaProgressIndex = 0;
    private int cProgramIndex = 0;
    private int cppProgramIndex = 0;

    public CreekUserStats(int javaProgressIndex, int cProgramIndex, int cppProgramIndex) {
        this.javaProgressIndex = javaProgressIndex;
        this.cProgramIndex = cProgramIndex;
        this.cppProgramIndex = cppProgramIndex;
    }

    public CreekUserStats() {
    }

    public int getJavaProgressIndex() {
        return javaProgressIndex;
    }

    public void setJavaProgressIndex(int javaProgressIndex) {
        this.javaProgressIndex = javaProgressIndex;
    }

    public int getcProgramIndex() {
        return cProgramIndex;
    }

    public void setcProgramIndex(int cProgramIndex) {
        this.cProgramIndex = cProgramIndex;
    }

    public int getCppProgramIndex() {
        return cppProgramIndex;
    }

    public void setCppProgramIndex(int cppProgramIndex) {
        this.cppProgramIndex = cppProgramIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreekUserStats that = (CreekUserStats) o;

        if (javaProgressIndex != that.javaProgressIndex) return false;
        if (cProgramIndex != that.cProgramIndex) return false;
        return cppProgramIndex == that.cppProgramIndex;

    }

    @Override
    public int hashCode() {
        int result = javaProgressIndex;
        result = 31 * result + cProgramIndex;
        result = 31 * result + cppProgramIndex;
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.javaProgressIndex);
        dest.writeInt(this.cProgramIndex);
        dest.writeInt(this.cppProgramIndex);
    }

    protected CreekUserStats(Parcel in) {
        this.javaProgressIndex = in.readInt();
        this.cProgramIndex = in.readInt();
        this.cppProgramIndex = in.readInt();
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

    @Override
    public String toString() {
        return "CreekUserStats{" +
                "javaProgressIndex=" + javaProgressIndex +
                ", cProgramIndex=" + cProgramIndex +
                ", cppProgramIndex=" + cppProgramIndex +
                '}';
    }
}

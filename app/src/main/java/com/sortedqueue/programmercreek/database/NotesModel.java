package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alok Omkar on 2017-07-28.
 */

public class NotesModel implements Parcelable {

    public static final int TYPE_CODE = 0;
    public static final int TYPE_NOTES = 1;
    public static final int TYPE_HEADER = 2;
    public static final int TYPE_LINK = 3;


    private String noteLine;
    private int noteType;

    public NotesModel(String noteLine, int noteType) {
        this.noteLine = noteLine;
        this.noteType = noteType;
    }

    public String getNoteLine() {
        return noteLine;
    }

    public void setNoteLine(String noteLine) {
        this.noteLine = noteLine;
    }

    public int getNoteType() {
        return noteType;
    }

    public void setNoteType(int noteType) {
        this.noteType = noteType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotesModel that = (NotesModel) o;

        if (noteType != that.noteType) return false;
        return noteLine != null ? noteLine.equals(that.noteLine) : that.noteLine == null;

    }

    @Override
    public int hashCode() {
        int result = noteLine != null ? noteLine.hashCode() : 0;
        result = 31 * result + noteType;
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.noteLine);
        dest.writeInt(this.noteType);
    }

    protected NotesModel(Parcel in) {
        this.noteLine = in.readString();
        this.noteType = in.readInt();
    }

    public static final Parcelable.Creator<NotesModel> CREATOR = new Parcelable.Creator<NotesModel>() {
        @Override
        public NotesModel createFromParcel(Parcel source) {
            return new NotesModel(source);
        }

        @Override
        public NotesModel[] newArray(int size) {
            return new NotesModel[size];
        }
    };

    @Override
    public String toString() {
        return "NotesModel{" +
                "noteLine='" + noteLine + '\'' +
                ", noteType=" + noteType +
                '}';
    }
}

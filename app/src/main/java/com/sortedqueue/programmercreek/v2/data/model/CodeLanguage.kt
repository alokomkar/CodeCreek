package com.sortedqueue.programmercreek.v2.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull

@Entity(tableName = "CodeLanguage")
data class CodeLanguage(
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "id")
        var id: String = "",
        @NonNull
        @ColumnInfo(name = "language")
        var language: String = "",
        @NonNull
        @ColumnInfo(name = "description")
        var description: String = "",
        @NonNull
        @ColumnInfo(name = "languageExtension")
        var languageExtension: String = "",
        @ColumnInfo(name = "created")
        var created: Long = -1L,
        @NonNull
        @ColumnInfo(name = "updated")
        var updated: Long = -1L) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CodeLanguage

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readLong(),
            source.readLong()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(language)
        writeString(description)
        writeString(languageExtension)
        writeLong(created)
        writeLong(updated)
    }

    override fun toString(): String {
        return "CodeLanguage(id='$id', " +
                "\nlanguage='$language', " +
                "\ndescription='$description', " +
                "\nlanguageExtension='$languageExtension', " +
                "\ncreated=$created, " +
                "\nupdated=$updated)"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CodeLanguage> = object : Parcelable.Creator<CodeLanguage> {
            override fun createFromParcel(source: Parcel): CodeLanguage = CodeLanguage(source)
            override fun newArray(size: Int): Array<CodeLanguage?> = arrayOfNulls(size)
        }
    }


}
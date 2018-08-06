package com.sortedqueue.programmercreek.v2.data.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull

@Entity(tableName = "MasterContent",
        foreignKeys = [(ForeignKey(entity = CodeLanguage::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("languageId")))])
data class MasterContent(
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "id")
        var id: String = "",
        @NonNull
        @ColumnInfo(name = "contentDetails")
        var contentDetails: String = "",
        @NonNull
        @ColumnInfo(name = "languageId")
        var languageId: String = "",
        @NonNull
        @ColumnInfo(name = "contentType")
        var contentType: Int = 0,
        @ColumnInfo(name = "created")
        var created: Long = -1L,
        @NonNull
        @ColumnInfo(name = "updated")
        var updated: Long = -1L) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MasterContent

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
            source.readInt(),
            source.readLong(),
            source.readLong()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(contentDetails)
        writeString(languageId)
        writeInt(contentType)
        writeLong(created)
        writeLong(updated)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MasterContent> = object : Parcelable.Creator<MasterContent> {
            override fun createFromParcel(source: Parcel): MasterContent = MasterContent(source)
            override fun newArray(size: Int): Array<MasterContent?> = arrayOfNulls(size)
        }
    }
}
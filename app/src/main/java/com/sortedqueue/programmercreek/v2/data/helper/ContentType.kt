package com.sortedqueue.programmercreek.v2.data.helper

import android.os.Parcel
import android.os.Parcelable

data class ContentType(var contentType: Int = 0,
                       var contentTag: String = "") : Parcelable {
    var isSelected: Boolean = false

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(contentType)
        writeString(contentTag)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContentType

        if (contentType != other.contentType) return false
        if (contentTag != other.contentTag) return false

        return true
    }

    override fun hashCode(): Int {
        var result = contentType
        result = 31 * result + contentTag.hashCode()
        return result
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ContentType> = object : Parcelable.Creator<ContentType> {
            override fun createFromParcel(source: Parcel): ContentType = ContentType(source)
            override fun newArray(size: Int): Array<ContentType?> = arrayOfNulls(size)
        }
    }




}
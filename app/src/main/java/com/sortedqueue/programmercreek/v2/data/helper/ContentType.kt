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

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ContentType> = object : Parcelable.Creator<ContentType> {
            override fun createFromParcel(source: Parcel): ContentType = ContentType(source)
            override fun newArray(size: Int): Array<ContentType?> = arrayOfNulls(size)
        }
    }
}
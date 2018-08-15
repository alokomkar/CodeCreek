package com.sortedqueue.programmercreek.v2.data.helper

import android.os.Parcel
import android.os.Parcelable

data class Content(
        var contentString: String = "",
        var contentType: ContentType = ContentType(1, "")
) : Parcelable {
    var isSelected: Boolean = false

    constructor(source: Parcel) : this(
            source.readString(),
            source.readParcelable<ContentType>(ContentType::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(contentString)
        writeParcelable(contentType, 0)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Content

        if (contentString != other.contentString) return false

        return true
    }

    override fun hashCode(): Int {
        return contentString.hashCode()
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Content> = object : Parcelable.Creator<Content> {
            override fun createFromParcel(source: Parcel): Content = Content(source)
            override fun newArray(size: Int): Array<Content?> = arrayOfNulls(size)
        }
    }


}
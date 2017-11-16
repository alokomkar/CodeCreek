package com.sortedqueue.programmercreek.python

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Alok Omkar on 2017-11-16.
 */
data class PyIntroduction(var contentId: String = "",
                          var title: String = "",
                          var description: String = "",
                          var imageUrl: String = "",
                          var codeExample: String = "",
                          var programCode: String = "",
                          var quizQuestion: String = "",
                          var quizCode: String = "",
                          var quizOptions: String = "") : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(contentId)
        writeString(title)
        writeString(description)
        writeString(imageUrl)
        writeString(codeExample)
        writeString(programCode)
        writeString(quizQuestion)
        writeString(quizCode)
        writeString(quizOptions)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PyIntroduction> = object : Parcelable.Creator<PyIntroduction> {
            override fun createFromParcel(source: Parcel): PyIntroduction = PyIntroduction(source)
            override fun newArray(size: Int): Array<PyIntroduction?> = arrayOfNulls(size)
        }
    }
}
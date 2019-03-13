package com.sortedqueue.programmercreek.database

import android.content.Context
import android.os.Parcel
import android.os.Parcelable

import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler

/**
 * Created by binay on 05/12/16.
 */

class CreekUser : Parcelable {

    var emailId: String = ""
    var userFullName: String = ""
    var userPhotoUrl: String = ""
    var programLanguage: String = ""
    var wasAnonUser = "No"
    var userId = ""

    constructor(parcel: Parcel) : this() {
        emailId = parcel.readString() ?: ""
        userFullName = parcel.readString() ?: ""
        userPhotoUrl = parcel.readString()?: ""
        programLanguage = parcel.readString()?: ""
        wasAnonUser = parcel.readString()?: "No"
        userId = parcel.readString()?: ""
    }


    constructor() {}

    constructor(emailId: String, userFullName: String, userPhotoUrl: String, programLanguage: String) {
        this.emailId = emailId
        this.userFullName = userFullName
        this.userPhotoUrl = userPhotoUrl
        this.programLanguage = programLanguage
    }

    override fun toString(): String {
        return "CreekUser{emailId='$emailId', userFullName='$userFullName', userPhotoUrl='$userPhotoUrl', programLanguage='$programLanguage', wasAnonUser='$wasAnonUser', userId='$userId'}"
    }

    fun save(context: Context) {
        val firebaseDatabaseHandler = FirebaseDatabaseHandler(context)
        firebaseDatabaseHandler.writeCreekUser(this)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(emailId)
        parcel.writeString(userFullName)
        parcel.writeString(userPhotoUrl)
        parcel.writeString(programLanguage)
        parcel.writeString(wasAnonUser)
        parcel.writeString(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CreekUser> {
        override fun createFromParcel(parcel: Parcel): CreekUser {
            return CreekUser(parcel)
        }

        override fun newArray(size: Int): Array<CreekUser?> {
            return arrayOfNulls(size)
        }
    }
}

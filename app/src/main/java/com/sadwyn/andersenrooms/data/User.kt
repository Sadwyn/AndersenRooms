package com.sadwyn.andersenrooms.data

import android.os.Parcel
import android.os.Parcelable

data class User(
        var email: String? = null,
        var familyName: String? = null,
        var givenName: String? = null,
        var pictureUrl: String? = null,
        var verifiedEmail: Boolean? = false) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Boolean::class.java.classLoader) as Boolean?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(email)
        writeString(familyName)
        writeString(givenName)
        writeString(pictureUrl)
        writeValue(verifiedEmail)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}
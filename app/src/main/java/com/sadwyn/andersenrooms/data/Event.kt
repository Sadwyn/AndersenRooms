package com.sadwyn.andersenrooms.data

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp

data class Event(
        var eventType: Long? = -1,
        var title: String? = null,
        var description: String? = null,
        var startDate: Timestamp? = null,
        var endDate: Timestamp? = null,
        var duration: Long? = 0,
        var user: User? = null) : Parcelable {

    constructor(source: Parcel) : this(
            source.readLong(),
            source.readString(),
            source.readString(),
            source.readParcelable<Timestamp>(Timestamp::class.java.classLoader),
            source.readParcelable<Timestamp>(Timestamp::class.java.classLoader),
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readParcelable<User>(User::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(eventType)
        writeString(title)
        writeString(description)
        writeParcelable(startDate, 0)
        writeParcelable(endDate, 0)
        writeValue(duration)
        writeParcelable(user, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Event> = object : Parcelable.Creator<Event> {
            override fun createFromParcel(source: Parcel): Event = Event(source)
            override fun newArray(size: Int): Array<Event?> = arrayOfNulls(size)
        }
    }
}
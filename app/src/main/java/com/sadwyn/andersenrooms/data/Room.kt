package com.sadwyn.andersenrooms.data

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Room(var id: String? = null,
                var name: String? = null,
                var events: List<Event>? = null,
                var isBusy: Boolean? = false,
                var availableRentTime: List<Date>? = null,
                var roomPhotoUrl: String? = null,
                var currentRentProgress: Int? = -1,
                var currentEvent: Event? = null


) : Parcelable {

    val calendar = Calendar.getInstance()

    private fun parseEvents() {
        events?.let {
            for (event in it) {
                if (calendar.time.after(event.startDate?.toDate()) && calendar.before(event.endDate?.toDate())) {
                    currentEvent = event
                    isBusy = true
                    return
                }
            }
            isBusy = false
        }
    }

    fun initRoom(){
        parseEvents()
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.createTypedArrayList(Event.CREATOR),
            source.readValue(Boolean::class.java.classLoader) as Boolean?,
            ArrayList<Date>().apply { source.readList(this, Date::class.java.classLoader) },
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeTypedList(events)
        writeValue(isBusy)
        writeList(availableRentTime)
        writeString(roomPhotoUrl)
        writeValue(currentRentProgress)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Room> = object : Parcelable.Creator<Room> {
            override fun createFromParcel(source: Parcel): Room = Room(source)
            override fun newArray(size: Int): Array<Room?> = arrayOfNulls(size)
        }
    }
}



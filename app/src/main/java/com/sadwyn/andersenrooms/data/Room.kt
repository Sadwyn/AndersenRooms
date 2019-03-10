package com.sadwyn.andersenrooms.data

import java.util.*

data class Room(val id: String? = null,
                val name: String? = null,
                var events : List<Event>? = null,
                val isBusy: Boolean? = false,
                val availableRentTime: List<Date>? = null,
                val personPhotoUrl: String? = null,
                val roomPhotoUrl: String? = null,
                val currentRentProgress: Int? = -1)

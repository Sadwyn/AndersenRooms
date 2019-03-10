package com.sadwyn.andersenrooms.data

import com.google.firebase.Timestamp

data class Event(
        var eventType: Long = -1,
        var title: String? = null,
        var description: String? = null,
        var startDate: Timestamp? = null,
        var endDate: Timestamp? = null,
        var duration: Long? = 0,
        var user: User? = null)
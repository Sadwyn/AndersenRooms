package com.sadwyn.andersenrooms.data

data class User(
        var email: String? = null,
        var familyName: String? = null,
        var givenName: String? = null,
        var pictureUrl: String? = null,
        var verifiedEmail:Boolean? = false)
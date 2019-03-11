package com.sadwyn.andersenrooms.presentation.presenter

import com.google.firebase.firestore.QuerySnapshot

interface FirebaseSuccessListener {
    fun onEventsSuccess(querySnapshot : QuerySnapshot)
    fun onRoomsSuccess(querySnapshot : QuerySnapshot)
    fun onUsersSuccess(querySnapshot : QuerySnapshot)
}
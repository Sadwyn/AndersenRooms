package com.sadwyn.andersenrooms.ui.base

import com.arellomobile.mvp.MvpView
import com.sadwyn.andersenrooms.data.Room

interface BaseView : MvpView{
    fun updateRoomsStatus(rooms : List<Room>)
}

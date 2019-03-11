package com.sadwyn.andersenrooms.presentation.view

import com.sadwyn.andersenrooms.data.Room
import com.sadwyn.andersenrooms.ui.base.BaseView

interface MainActivityView : BaseView{
    fun updateRoomsStatus(rooms : List<Room>)
}

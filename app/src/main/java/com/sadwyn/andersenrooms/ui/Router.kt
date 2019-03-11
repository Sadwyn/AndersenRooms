package com.sadwyn.andersenrooms.ui

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.sadwyn.andersenrooms.R
import com.sadwyn.andersenrooms.data.Room
import com.sadwyn.andersenrooms.ui.fragment.DatePickerFragment
import com.sadwyn.andersenrooms.ui.fragment.RoomsFragment
import java.util.ArrayList

class Router(activity: MvpAppCompatActivity) {
    private val context = activity

    public fun replaceFirstFragment(fragment: MvpAppCompatFragment) {
        context.supportFragmentManager.beginTransaction().replace(R.id.content_id, fragment, fragment.tag).commit()
    }

    public fun replaceFragment(fragment: MvpAppCompatFragment, isNeedBackStack: Boolean, bundle: Bundle) {
        val transaction = context.supportFragmentManager.beginTransaction()
        if (isNeedBackStack) {
            transaction.addToBackStack(fragment.tag)
        }
        if (bundle != null) {
            fragment.arguments = bundle
        }
        transaction.replace(R.id.content_id, fragment, fragment.tag).commit()
    }

    public fun routeToRoomsFragment(rooms: List<Room>) {
        var fragment = RoomsFragment.newInstance()
        var bundle = Bundle()
        bundle.putParcelableArray("ROOMS", rooms.toTypedArray())
        replaceFragment(fragment, false, bundle)
    }

    public fun routeToDatePickerFragment(room: Room) {
        var fragment = DatePickerFragment.newInstance()
        var bundle = Bundle()
        bundle.putParcelable("ROOM", room)
        replaceFragment(fragment, false, bundle)
    }
}
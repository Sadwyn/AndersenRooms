package com.sadwyn.andersenrooms.ui.adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sadwyn.andersenrooms.R
import com.sadwyn.andersenrooms.data.Room
import kotlinx.android.synthetic.main.room_view.view.*


class RoomsViewAdapter : RecyclerView.Adapter<RoomsViewAdapter.RoomViewHolder>() {
    var rooms: List<Room> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.room_view, parent, false))
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(rooms[position])
    }

    class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(room: Room) = with(itemView) {

            bookNow.setOnClickListener {

            }

            showRoomSchedule.setOnClickListener {

            }
            val personPhotoUri = Uri.parse(room.personPhotoUrl)
            val roomPhotoUri = Uri.parse(room.roomPhotoUrl)
            roomName.text = room.name
            bookNow.isEnabled = room.isBusy!!
            personPhoto.setImageURI(personPhotoUri)
            meetUpRoomPhoto.setImageURI(roomPhotoUri)
            roomRentProgress.progress = room.currentRentProgress!!

        }
    }
}
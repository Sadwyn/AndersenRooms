package com.sadwyn.andersenrooms.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sadwyn.andersenrooms.R
import com.sadwyn.andersenrooms.data.Room
import kotlinx.android.synthetic.main.room_view.view.*


class RoomsViewAdapter(val onBookMeetRoom: OnBookMeetRoom) : RecyclerView.Adapter<RoomsViewAdapter.RoomViewHolder>() {
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

    inner class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(room: Room) = with(itemView) {
            bookNow.setOnClickListener {onBookMeetRoom.onBookClick(room)}
            showRoomSchedule.setOnClickListener { onBookMeetRoom.onScheduleClick(room) }
            room.roomPhotoUrl?.let{Glide.with(itemView.context).load(it).into(meetUpRoomPhoto) }
            room.currentEvent?.user?.pictureUrl?.let { Glide.with(itemView.context).load(it).into(personPhoto) }
            roomName.text = room.name
            bookNow.isEnabled = !room.isBusy!!
            roomRentProgress.progress = room.currentRentProgress!!
        }
    }

    interface OnBookMeetRoom {
        fun onBookClick(room: Room)
        fun onScheduleClick(room: Room)
    }
}
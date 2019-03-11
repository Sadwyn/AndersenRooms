package com.sadwyn.andersenrooms.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.sadwyn.andersenrooms.R
import com.sadwyn.andersenrooms.data.Room
import com.sadwyn.andersenrooms.presentation.presenter.RoomsPresenter
import com.sadwyn.andersenrooms.presentation.view.RoomsView
import com.sadwyn.andersenrooms.ui.adapter.RoomsViewAdapter
import com.sadwyn.andersenrooms.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_rooms.*

class RoomsFragment : BaseFragment(), RoomsView {
    @InjectPresenter
    internal lateinit var mRoomsPresenter: RoomsPresenter

    lateinit var adapter: RoomsViewAdapter
    lateinit var rooms : ArrayList<Room>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_rooms, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rooms = ArrayList()
        if(arguments!=null){
            (arguments!!.getParcelableArray("ROOMS") as Array<Room>).forEach {rooms.add(it)}
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomsRecycler.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = RoomsViewAdapter()
        }
    }

    override fun updateRoomsStatus(rooms: List<Room>) {
        adapter.rooms = rooms
        adapter.notifyDataSetChanged()
    }

    companion object {
        val TAG = "RoomsFragment"
        fun newInstance(): RoomsFragment {
            val fragment = RoomsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}

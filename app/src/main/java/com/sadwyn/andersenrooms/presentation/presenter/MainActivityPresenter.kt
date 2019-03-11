package com.sadwyn.andersenrooms.presentation.presenter


import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.sadwyn.andersenrooms.data.Event
import com.sadwyn.andersenrooms.data.Room
import com.sadwyn.andersenrooms.data.User
import com.sadwyn.andersenrooms.presentation.view.MainActivityView
import com.sadwyn.andersenrooms.ui.base.BasePresenter
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers


@InjectViewState
class MainActivityPresenter : BasePresenter<MainActivityView>(), FirebaseSuccessListener {

    private val events = "Events"
    private val places = "Places"
    private val users = "Users"
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private var eventsQuery: QuerySnapshot? = null
    private var roomsQuery: QuerySnapshot? = null
    private var usersQuery: QuerySnapshot? = null

    @SuppressLint("CheckResult")
    override fun attachView(view: MainActivityView?) {
        super.attachView(view)
        eventsQuery = null
        roomsQuery = null
        usersQuery = null
        Observable.zip(
                Observable.just(db.collection(events).get().addOnSuccessListener { onEventsSuccess(it) }),
                Observable.just(db.collection(places).get().addOnSuccessListener { onRoomsSuccess(it) }),
                Observable.just(db.collection(users).get().addOnSuccessListener { onUsersSuccess(it) }),
                Function3<Task<QuerySnapshot>, Task<QuerySnapshot>, Task<QuerySnapshot>, Completable>
                {events, places, users -> Completable.complete()})
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, { t: Throwable? -> t?.printStackTrace() })

    }

    override fun onEventsSuccess(querySnapshot: QuerySnapshot) {
        this.eventsQuery = querySnapshot
        if(roomsQuery!=null && usersQuery!=null){
            collectData(eventsQuery!!.documents, roomsQuery!!.documents, usersQuery!!.documents)
        }
    }

    override fun onRoomsSuccess(querySnapshot: QuerySnapshot) {
        this.roomsQuery = querySnapshot
        if(eventsQuery!=null && usersQuery!=null){
            collectData(eventsQuery!!.documents, roomsQuery!!.documents, usersQuery!!.documents)
        }
    }

    override fun onUsersSuccess(querySnapshot: QuerySnapshot) {
        this.usersQuery = querySnapshot
        if(roomsQuery!=null && eventsQuery!=null){
            collectData(eventsQuery!!.documents, roomsQuery!!.documents, usersQuery!!.documents)
        }
    }


    private fun collectData(eventsList: List<DocumentSnapshot>, placesList: List<DocumentSnapshot>, usersList: List<DocumentSnapshot>) {
        val rooms = ArrayList<Room>()
        for (placeSnapshot in placesList) {
            val room = Room()
            val events = collectEvents(eventsList, placeSnapshot, usersList)
            room.events = events
            room.id = placeSnapshot["id"] as? String
            room.name = placeSnapshot["name"] as? String
            room.roomPhotoUrl = placeSnapshot["avatar"] as? String
            room.initRoom()
            rooms.add(room)
        }
        viewState.updateRoomsStatus(rooms )
    }

    private fun collectEvents(eventsList: List<DocumentSnapshot>, placeSnapshot: DocumentSnapshot, usersList: List<DocumentSnapshot>): ArrayList<Event> {
        val events = ArrayList<Event>()
        for (eventSnapshot in eventsList) {
            val event = getEvent(placeSnapshot, eventSnapshot)
            if (event != null) {
                for (userSnapshot in usersList) {
                    val user = getUserForEvent(userSnapshot, eventSnapshot)
                    if (user != null) {
                        event.user = user
                        break
                    }
                }
                events.add(event)
            }
        }
        return events
    }

    private fun getEvent(placeSnapshot: DocumentSnapshot, eventSnapshot: DocumentSnapshot): Event? {
        if (placeSnapshot.id == eventSnapshot["place"]) {
            return Event(
                    eventSnapshot["eventType"] as? Long,
                    eventSnapshot["title"] as? String,
                    eventSnapshot["description"] as? String,
                    eventSnapshot["startDate"] as? Timestamp,
                    eventSnapshot["endDate"] as? Timestamp,
                    eventSnapshot["duration"] as? Long
            )
        }
        return null
    }

    private fun getUserForEvent(userSnapshot: DocumentSnapshot, eventSnapshot: DocumentSnapshot): User? {
        return if (userSnapshot.id == eventSnapshot["user"]) {
            User(userSnapshot["email"] as? String,
                    userSnapshot["family_name"] as? String,
                    userSnapshot["given_name"] as? String,
                    userSnapshot["picture"] as? String,
                    userSnapshot["verified_email"] as? Boolean)
        } else {
            null
        }
    }
}








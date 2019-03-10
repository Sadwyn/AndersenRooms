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
import com.sadwyn.andersenrooms.ui.base.BaseView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList


@InjectViewState
class MainActivityPresenter : BasePresenter<MainActivityView>() {
    private val events = "Events"
    private val places = "Places"
    private val users = "Users"
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()


    @SuppressLint("CheckResult")
    override fun attachView(view: BaseView?) {
        super.attachView(view)
        Observable.zip(
                Observable.just(db.collection(events).get()),
                Observable.just(db.collection(places).get()),
                Observable.just(db.collection(users).get()),
                Function3<Task<QuerySnapshot>, Task<QuerySnapshot>, Task<QuerySnapshot>, List<Room>>
                { questions, answers, favorites ->
                    collectData(questions.result!!.documents, answers.result!!.documents, favorites.result!!.documents)
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it != null) {
                        viewState.updateRoomsStatus(it)
                    }
                }, { t: Throwable? ->
                    t?.printStackTrace()
                })

    }


    private fun collectData(eventsList: List<DocumentSnapshot>, placesList: List<DocumentSnapshot>, usersList: List<DocumentSnapshot>): List<Room> {
        val rooms = ArrayList<Room>()
        for (placeSnapshot in placesList) {
            val room = Room()
            val events = collectEvents(eventsList, placeSnapshot, usersList)
            room.events = events
            rooms.add(room)
        }
        return rooms
    }

    private fun collectEvents(eventsList: List<DocumentSnapshot>, placeSnapshot: DocumentSnapshot, usersList: List<DocumentSnapshot>): ArrayList<Event> {
        val events = ArrayList<Event>()
        for (eventSnapshot in eventsList) {
            val event = getEvent(placeSnapshot, eventSnapshot)
            if (event != null) {
                for (userSnapshot in usersList) {
                    val user = getUserForEvent(userSnapshot, eventSnapshot, event)
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
                    eventSnapshot["eventType"] as Long,
                    eventSnapshot["title"] as String,
                    eventSnapshot["description"] as String,
                    eventSnapshot["startDate"] as Timestamp,
                    eventSnapshot["endDate"] as Timestamp,
                    eventSnapshot["duration"] as Long
            )
        }
        return null
    }

    private fun getUserForEvent(userSnapshot: DocumentSnapshot, eventSnapshot: DocumentSnapshot, event: Event): User? {
        return if (userSnapshot.id == eventSnapshot["user"]) {
            User(userSnapshot["email"] as String,
                    userSnapshot["family_name"] as String,
                    userSnapshot["given_name"] as String,
                    userSnapshot["picture"] as String,
                    userSnapshot["verified_email"] as Boolean)
        } else {
            null
        }
    }
}








package com.sadwyn.andersenrooms.presentation.presenter


import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.sadwyn.andersenrooms.presentation.view.MainActivityView
import com.sadwyn.andersenrooms.ui.base.BasePresenter
import com.sadwyn.andersenrooms.ui.base.BaseView
import java.text.SimpleDateFormat
import java.util.*


@InjectViewState
class MainActivityPresenter : BasePresenter<MainActivityView>() {
    private val events = "Events"
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault())


    override fun attachView(view: BaseView?) {
        super.attachView(view)
        db.collection(events).get().addOnSuccessListener {
            for (document in it) {
                val startDateCalendar = Calendar.getInstance()
                val endDateCalendar = Calendar.getInstance()
                startDateCalendar.time = (document["startDate"] as Timestamp).toDate()
                endDateCalendar.time = (document["endDate"] as Timestamp).toDate()
                Log.v("TAG", "Room with id ${document["place"] as String} is booked from  " +
                        "${startDateCalendar.get(Calendar.HOUR_OF_DAY)} :" +
                        " ${startDateCalendar.get(Calendar.MINUTE)}" +
                        " to ${endDateCalendar.get(Calendar.HOUR_OF_DAY)}" +
                        " : ${endDateCalendar.get(Calendar.MINUTE)} by user with id ${document["user"] as String}")
            }
        }.addOnFailureListener {
            Log.w("TAG", "Error getting documents.", it)
        }
    }

}

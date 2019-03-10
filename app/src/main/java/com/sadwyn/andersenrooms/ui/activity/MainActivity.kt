package com.sadwyn.andersenrooms.ui.activity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.sadwyn.andersenrooms.R
import com.sadwyn.andersenrooms.data.Room
import com.sadwyn.andersenrooms.presentation.presenter.MainActivityPresenter
import com.sadwyn.andersenrooms.presentation.view.MainActivityView
import com.sadwyn.andersenrooms.ui.Router
import com.sadwyn.andersenrooms.ui.fragment.RoomsFragment

class MainActivity : MvpAppCompatActivity(), MainActivityView {
    @InjectPresenter
    internal lateinit var mMainActivityPresenter: MainActivityPresenter

    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router = Router(this)
        router.replaceFirstFragment(RoomsFragment.newInstance())
    }

    override fun updateRoomsStatus(rooms: List<Room>) {
    }

    companion object {
        val TAG = "MainActivity"
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}

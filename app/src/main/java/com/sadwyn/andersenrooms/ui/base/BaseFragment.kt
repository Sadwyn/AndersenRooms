package com.sadwyn.andersenrooms.ui.base

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatFragment
import com.sadwyn.andersenrooms.ui.Router
import com.sadwyn.andersenrooms.ui.activity.MainActivity

abstract class BaseFragment : MvpAppCompatFragment(){
    protected var router : Router? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(activity is MainActivity){
            router = (activity as MainActivity).router
        }
    }
}

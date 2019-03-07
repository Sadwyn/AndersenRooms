package com.sadwyn.andersenrooms.ui

import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.sadwyn.andersenrooms.R

class Router(activity: MvpAppCompatActivity) {
    private val context = activity

    fun replaceFirstFragment(fragment: MvpAppCompatFragment){
        context.supportFragmentManager.beginTransaction().replace(R.id.content_id, fragment, fragment.tag).commit()
    }

    fun replaceFragment(fragment: MvpAppCompatFragment, isNeedBackStack : Boolean){
        val transaction = context.supportFragmentManager.beginTransaction()
        if(isNeedBackStack){
            transaction.addToBackStack(fragment.tag)
        }
        transaction.replace(R.id.content_id, fragment, fragment.tag).commit()
    }
}
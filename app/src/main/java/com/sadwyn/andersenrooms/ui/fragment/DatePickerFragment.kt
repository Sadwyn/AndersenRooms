package com.sadwyn.andersenrooms.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.sadwyn.andersenrooms.R
import com.sadwyn.andersenrooms.presentation.presenter.DatePickerPresenter
import com.sadwyn.andersenrooms.presentation.view.DatePickerView
import com.sadwyn.andersenrooms.ui.base.BaseFragment

class DatePickerFragment : BaseFragment(), DatePickerView {
    @InjectPresenter
    internal lateinit var mDatePickerPresenter: DatePickerPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_date_picker, container, false)
    }

    companion object {
        val TAG = "DatePickerFragment"
        fun newInstance(): DatePickerFragment {
            val fragment = DatePickerFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}

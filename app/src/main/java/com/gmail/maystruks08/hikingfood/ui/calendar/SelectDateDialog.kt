package com.gmail.maystruks08.hikingfood.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.gmail.maystruks08.hikingfood.R
import kotlinx.android.synthetic.main.dialog_select_date.*
import java.util.*

class SelectDateDialog : DialogFragment() {

    private lateinit var listener: (Date) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_select_date, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        arguments?.getString(CALENDAR)?.let {
            tvCalendarHeader.text = it
        }
        btnDateSelected?.setOnClickListener {
            listener.invoke(Date(cvDate.date))
            this.dismiss()
        }

        btnDismissCalendarDialog?.setOnClickListener { this.dismiss() }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }


    companion object {

        const val CALENDAR = "calendar_header_text"

        fun getInstance(headerText: String, listener: (Date) -> Unit) =
            SelectDateDialog().apply {
                this.listener = listener
                arguments?.apply {
                    arguments?.putString(CALENDAR, headerText)
                }
            }
    }
}
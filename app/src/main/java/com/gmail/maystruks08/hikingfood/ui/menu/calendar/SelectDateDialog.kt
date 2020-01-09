package com.gmail.maystruks08.hikingfood.ui.menu.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.utils.extensions.argument
import kotlinx.android.synthetic.main.dialog_select_date.*
import java.util.*

class SelectDateDialog : DialogFragment() {

    private lateinit var listener: (Date) -> Unit

    private var headerText: String by argument()

    private val calendar: Calendar = GregorianCalendar()

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
        tvCalendarHeader.text = headerText
        cvDate.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
        }
        btnDateSelected?.setOnClickListener {
            listener.invoke(calendar.time)
            this.dismiss()
        }

        btnDismissCalendarDialog?.setOnClickListener { this.dismiss() }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    companion object {

        fun getInstance(headerText: String, listener: (Date) -> Unit) =
            SelectDateDialog().apply {
                this.listener = listener
                this.headerText = headerText
            }
    }
}
package com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.calendar.SelectDateDialog
import kotlinx.android.synthetic.main.fragment_create_menu.*
import java.util.*
import javax.inject.Inject

class CreateMenuFragment : Fragment(), CreateMenuContract.View {

    @Inject
    lateinit var presenter: CreateMenuContract.Presenter

    @Inject
    lateinit var controller: ToolBarController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.createMenuComponent?.inject(this)
        return inflater.inflate(R.layout.fragment_create_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)
        init()
    }

    override fun configToolbar() {
        controller.configure(
            ToolbarDescriptor.Builder()
                .visibility(true)
                .navigationIcon(R.drawable.ic_arrow_back)
                .title("Создание меню")
                .build(),
            activity as ConfigToolbar
        )
    }

    private fun init() {
        btnCreateMenuNextStep.setOnClickListener {
            if (etMenuName.text.isNotEmpty()) {
                presenter.createNewMenuClicked()
            }
        }

        etMenuName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                presenter.onNameMenuChanged(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        npRelaxDayCountValue.minValue = 0
        npRelaxDayCountValue.value = 0
        npRelaxDayCountValue.maxValue = 10

        npReceptionCountValue.minValue = 1
        npReceptionCountValue.value = 1
        npReceptionCountValue.maxValue = 50

        npPeopleCountValue.minValue = 1
        npPeopleCountValue.value = 1
        npPeopleCountValue.maxValue = 50

        npRelaxDayCountValue.setOnValueChangedListener { picker, _, _ ->
            picker.value
            if (picker.value >= 0) {
                presenter.onRelaxDayCountChanged(picker.value)
            }
        }

        npReceptionCountValue.setOnValueChangedListener { picker, _, _ ->
            picker.value
            if (picker.value >= 0) {
                presenter.onDayCountChanged(picker.value)
            }
        }

        npPeopleCountValue.setOnValueChangedListener { picker, _, _ ->
            picker.value
            if (picker.value >= 0) {
                presenter.onCountOfPeopleChanged(picker.value)
            }
        }

        rgStartReception.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbBreakfast -> presenter.onTimeMenuStartChanged(0)
                R.id.rbLunch -> presenter.onTimeMenuStartChanged(1)
                R.id.rbDinner -> presenter.onTimeMenuStartChanged(2)
            }
        }

        tvDateOfStartMenu.setOnClickListener {
            presenter.onSetDateStartMenuClicked()
        }
    }

    override fun showCalendarDialog() {
        SelectDateDialog.getInstance("Выберите дату старта раскладки:", ::onDateSelected)
            .show(childFragmentManager, Screens.SELECT_DATE_DIALOG)
    }

    @SuppressLint("SetTextI18n")
    override fun showDateOfStartReception(date: String) {
        tvDateOfStartMenu.text = "${getString(R.string.date_of_start)} $date"
    }

    private fun onDateSelected(date: Date) {
        presenter.onDateMenuStartChanged(date)
    }

    override fun onDestroy() {
        presenter.end()
        App.clearCreateMenuComponent()
        super.onDestroy()
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}

}

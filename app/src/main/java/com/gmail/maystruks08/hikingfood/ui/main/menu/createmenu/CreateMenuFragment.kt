package com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.calendar.SelectDateDialog
import com.gmail.maystruks08.hikingfood.utils.extensions.toast
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
                .title("Создание раскладки")
                .build(),
            activity as ConfigToolbar
        )
    }

    private fun init() {
        btnCreateMenuNextStep.setOnClickListener {
            if (etMenuName.text.isNotEmpty()) {
                presenter.createNewMenuClicked()
            } else {
                context?.toast("Укажите название раскладки!")
            }
        }

        etMenuName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            enableNextStepButton(s.isNotEmpty())
                presenter.onNameMenuChanged(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        npRelaxDayCountValue.setOnValueChangedListener { picker, _, _ ->
            if (picker.value >= 0) {
                presenter.onRelaxDayCountChanged(picker.value)
            }
        }

        npReceptionCountValue.setOnValueChangedListener { picker, _, _ ->
            if (picker.value >= 0) {
                presenter.onDayCountChanged(picker.value)
            }
        }

        npPeopleCountValue.setOnValueChangedListener { picker, _, _ ->
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

    private fun enableNextStepButton(enable: Boolean){
        context?.let {
            val background = if (enable) {
                ContextCompat.getColor(it, R.color.colorPrimary)
            } else {
                ContextCompat.getColor(it, R.color.colorPrimaryInactive)
            }
            btnCreateMenuNextStep.setBackgroundColor(background)
        }
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}

}

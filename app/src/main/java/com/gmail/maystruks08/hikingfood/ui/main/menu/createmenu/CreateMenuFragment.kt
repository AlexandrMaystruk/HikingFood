package com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.core.base.BaseFragment
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.calendar.SelectDateDialog
import com.gmail.maystruks08.hikingfood.utils.extensions.toast
import kotlinx.android.synthetic.main.fragment_create_menu.*
import java.util.*
import javax.inject.Inject
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_create_menu.btnCreateMenuNextStep
import kotlinx.android.synthetic.main.fragment_create_menu.inputLayout
import kotlinx.android.synthetic.main.fragment_create_menu.npPeopleCountValue
import kotlinx.android.synthetic.main.fragment_create_menu.npReceptionCountValue
import kotlinx.android.synthetic.main.fragment_create_menu.npRelaxDayCountValue
import kotlinx.android.synthetic.main.fragment_create_menu.tvDateOfStartMenu

class CreateMenuFragment : BaseFragment(), CreateMenuContract.View {

    @Inject
    lateinit var presenter: CreateMenuContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        App.createMenuComponent?.inject(this)
        return inflater.inflate(R.layout.fragment_create_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)
    }

    override fun builder(): FragmentToolbar {
        val onCLick = View.OnClickListener { presenter.onBackClicked() }
        return FragmentToolbar.Builder()
            .withId(R.id.toolbarCreate)
            .withTitle(R.string.fragment_create_menu_name)
            .withNavigationIcon(R.drawable.ic_arrow_back) { presenter.onBackClicked() }
            .build()
    }

    override fun initViews() {
        btnCreateMenuNextStep.setOnClickListener {
            if (etMenuName.text.isNotEmpty()) {
                presenter.createNewMenuClicked()
            } else {
                context?.toast("Укажите название раскладки!")
            }
        }

        inputLayout.requestFocus()
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

        rgStartReception.adapter = ArrayAdapter(context!!, R.layout.spinner_item, context!!.resources.getTextArray(R.array.menu_start)).apply {
            setDropDownViewResource(R.layout.spinner_drop_down)
        }
        rgStartReception.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.onTimeMenuStartChanged(position)
            }
        })
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

    private fun enableNextStepButton(enable: Boolean) {
        context?.let {
            btnCreateMenuNextStep.background = if (enable) {
                ContextCompat.getDrawable(it, R.drawable.bg_main_btn)
            } else {
                ContextCompat.getDrawable(it, R.drawable.bg_main_inactive_btn)
            }
        }
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}

}

package com.gmail.maystruks08.hikingfood.ui.createmenu

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.gmail.maystruks08.hikingfood.*
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
        return inflater.inflate(R.layout.fragment_create_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.createMenuComponent?.inject(this)
        presenter.bindView(this)
        init()
    }

    override fun configToolbar() {
        controller.configure(
            ToolbarDescriptor.Builder()
                .visibility(true)
                .navigationIcon(R.drawable.ic_arrow_back)
                .title("Create menu")
                .build(),
            activity as ConfigToolbar
        )
    }

    private fun init() {
        if (etMenuName.text != null) {
            btnChangeGrammar.setOnClickListener { presenter.onChangeIngredientPortion() }
        }

        etMenuName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                presenter.onNameMenuChanged(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        spinnerRelaxDayCount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position >= 0) {
                    presenter.onRelaxDayCountChanged(parent.selectedItem.toString().toInt())
                }
            }
        }

        spinnerDayCount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position >= 0) {
                    presenter.onDayCountChanged(parent.selectedItem.toString().toInt())
                }
            }
        }

        spinnerCountOfPeople.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position >= 0) {
                    presenter.onCountOfPeopleChanged(parent.selectedItem.toString().toInt())
                }
            }
        }

        spinnerStartMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position >= 0) {
                    presenter.onTimeMenuStartChanged(parent.selectedItemPosition)
                }
            }
        }

        cvDateOfHike.setOnDateChangeListener { _, _, _, _ ->
            presenter.onDateMenuStartChanged(Date(cvDateOfHike.date))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.end()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {
    }

    override fun showError(t: Throwable) {
    }

    companion object {

        fun getInstance(): CreateMenuFragment =
            CreateMenuFragment()

    }
}

package com.gmail.maystruks08.hikingfood.ui.menu.createproduct

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.core.base.BaseFragment
import com.gmail.maystruks08.hikingfood.utils.extensions.toast
import kotlinx.android.synthetic.main.fragment_create_menu.*
import javax.inject.Inject
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_create_menu.btnCreateMenuNextStep
import kotlinx.android.synthetic.main.fragment_create_menu.inputLayout
import kotlinx.android.synthetic.main.fragment_create_menu.npRelaxDayCountValue
import kotlinx.android.synthetic.main.fragment_create_menu.tvDateOfStartMenu

class CreateProductFragment : BaseFragment(), CreateProductContract.View {

    @Inject
    lateinit var presenter: CreateProductContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        App.createProductComponent?.inject(this)
        return inflater.inflate(R.layout.fragment_create_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)
    }

    override fun builder(): FragmentToolbar {
        return FragmentToolbar.Builder()
            .withId(R.id.toolbar)
            .withTitle(R.string.fragment_create_product)
            .withNavigationIcon(R.drawable.ic_arrow_back) { presenter.onBackClicked() }
            .build()
    }

    override fun initViews() {
        btnCreateMenuNextStep.setOnClickListener {
            if (etMenuName.text.isNotEmpty()) {
                presenter.createNewProductClicked()
            } else {
                context?.toast("Название продукта не указано!")
            }
        }

        inputLayout.requestFocus()
        etMenuName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {


            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        npRelaxDayCountValue.setOnValueChangedListener { picker, _, _ ->
            if (picker.value >= 0) {

            }
        }

        rgStartReception.adapter = ArrayAdapter(context!!, R.layout.spinner_item, context!!.resources.getTextArray(R.array.menu_start)).apply {
            setDropDownViewResource(R.layout.spinner_drop_down)
        }
        rgStartReception.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
        })
        tvDateOfStartMenu.setOnClickListener {
        }
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}

    override fun onDestroyView() {
        presenter.end()
        App.clearCreateProductComponent()
        super.onDestroyView()
    }
}

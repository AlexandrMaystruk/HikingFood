package com.gmail.maystruks08.hikingfood.ui.main.menu.portion

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.hikingfood.*
import kotlinx.android.synthetic.main.fragment_portion_for_one_people.*
import javax.inject.Inject

class ProductPortionForOnePeopleFragment : Fragment(), PortionContract.View {

    @Inject
    lateinit var presenter: PortionContract.Presenter

    @Inject
    lateinit var controller: ToolBarController

    private lateinit var productsPortionAdapter: ProductsPortionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        App.portionComponent?.inject(this)
        return inflater.inflate(R.layout.fragment_portion_for_one_people, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)
        init()
        setAdapter()
    }

    override fun configToolbar() {
        controller.configure(
            ToolbarDescriptor.Builder()
                .visibility(true)
                .navigationIcon(R.drawable.ic_arrow_back)
                .title("Грамовка")
                .build(),
            activity as ConfigToolbar
        )
    }

    private fun setAdapter() {
        productsPortionAdapter = ProductsPortionAdapter(::portionValueChanged)
        allIngredientPortionRecyclerView.layoutManager = LinearLayoutManager(allIngredientPortionRecyclerView.context)
        allIngredientPortionRecyclerView.adapter = productsPortionAdapter
    }

    override fun showProductPortionList(products: List<Product>) {
        productsPortionAdapter.productList = products.toMutableList()
    }

    private fun portionValueChanged(newValue: Int, productId: Int) {
        presenter.onPortionValueChanged(newValue, productId)
    }

    private fun init() {
        productSearchView.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    presenter.onSearchQueryChanged(s.toString())
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
    }

    override fun onDestroyView() {
        presenter.end()
        allIngredientPortionRecyclerView.adapter = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        App.clearPortionComponent()
        super.onDestroy()
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}

    companion object {

        fun getInstance(): ProductPortionForOnePeopleFragment = ProductPortionForOnePeopleFragment()

    }
}

package com.gmail.maystruks08.hikingfood.ui.portion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.hikingfood.*
import kotlinx.android.synthetic.main.fragment_dose_for_one_people.*
import javax.inject.Inject

class ProductPortionForOnePeopleFragment : Fragment(), PortionContract.View {

    @Inject
    lateinit var presenter: PortionContract.Presenter

    @Inject
    lateinit var controller: ToolBarController

    private lateinit var productsPortionAdapter: ProductsPortionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_dose_for_one_people, container, false)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.doseComponent?.inject(this)
        presenter.bindView(this)
        init()
        setAdapter()
    }

    private fun setAdapter() {
        productsPortionAdapter = ProductsPortionAdapter(::portionValueChanged)
        allIngredientPortionRecyclerView.layoutManager =
            LinearLayoutManager(allIngredientPortionRecyclerView.context)
        allIngredientPortionRecyclerView.adapter = productsPortionAdapter
    }

    override fun showProductPortionList(products: List<Product>) {
        productsPortionAdapter.productList = products.toMutableList()
    }

    private fun portionValueChanged(newValue: Int, productName: String) {
        presenter.onPortionValueChanged(newValue, productName)
    }

    private fun init() {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {
    }

    override fun showError(t: Throwable) {
    }

    companion object {

        fun getInstance(): ProductPortionForOnePeopleFragment =
            ProductPortionForOnePeopleFragment()

    }
}

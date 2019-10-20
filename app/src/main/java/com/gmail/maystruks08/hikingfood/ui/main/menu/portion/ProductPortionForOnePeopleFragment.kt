package com.gmail.maystruks08.hikingfood.ui.main.menu.portion

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.hikingfood.*
import kotlinx.android.synthetic.main.fragment_portions.*
import javax.inject.Inject

class ProductPortionForOnePeopleFragment : Fragment(), PortionContract.View {

    @Inject
    lateinit var presenter: PortionContract.Presenter

    @Inject
    lateinit var controller: ToolBarController

    private lateinit var productsPortionAdapter: ProductsPortionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        App.portionComponent?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_portions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)
        initViews()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        initSearch(menu, inflater)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initViews() {
        setAdapter()
        btnCreateMenuNextStep.setOnClickListener {
            presenter.onNexStepClicked()
        }
    }

    private fun initSearch(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchView = menu.findItem(R.id.action_search)?.actionView as? SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.onSearchQueryChanged(newText)
                return false
            }
        })
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

    override fun onDestroyView() {
        allIngredientPortionRecyclerView.adapter = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        presenter.end()
        App.clearPortionComponent()
        super.onDestroy()
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}

}

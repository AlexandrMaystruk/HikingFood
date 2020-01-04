package com.gmail.maystruks08.hikingfood.ui.menu.portion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.maystruks08.hikingfood.App
import com.gmail.maystruks08.hikingfood.FragmentToolbar
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.core.base.BaseFragment
import com.gmail.maystruks08.hikingfood.ui.adapter.*
import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactory
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductPortionView
import kotlinx.android.synthetic.main.fragment_portions.*
import javax.inject.Inject


class ProductPortionForOnePeopleFragment
    : BaseFragment(), PortionContract.View,
    AdapterCallbacks.OnItemChangeListener<ProductPortionView> {

    @Inject
    lateinit var presenter: PortionContract.Presenter

    @Inject
    lateinit var typesFactory: TypesFactory

    private lateinit var productsPortionTestFabricAdapter: FactoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.portionComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_portions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)
    }

    override fun builder(): FragmentToolbar {
        return FragmentToolbar.Builder()
            .withId(R.id.toolbarPortion)
            .withTitle(R.string.fragment_portions_name)
            .withNavigationIcon(R.drawable.ic_arrow_back) { presenter.onBackClicked() }
            .withMenu(R.menu.menu_search)
            .withMenuSearch { presenter.onSearchQueryChanged(it) }
            .build()
    }

    override fun initViews() {
        setAdapter()
        btnCreateMenuNextStep.setOnClickListener {
            presenter.onNexStepClicked()
        }
    }

    private fun setAdapter() {
        productsPortionTestFabricAdapter = FactoryAdapter(typesFactory, onItemChangeListener = this)
        allIngredientPortionRecyclerView.layoutManager = LinearLayoutManager(allIngredientPortionRecyclerView.context)
        allIngredientPortionRecyclerView.adapter = productsPortionTestFabricAdapter
    }

    override fun showProductPortionList(products: List<ProductPortionView>) {
        productsPortionTestFabricAdapter.items = products.toMutableList()
    }

    override fun onItemChanged(changedItem: ProductPortionView) {
        presenter.onPortionValueChanged(changedItem.portionValue, changedItem.id)
    }

    override fun onDestroyView() {
        allIngredientPortionRecyclerView.adapter = null
        presenter.end()
        App.clearPortionComponent()
        super.onDestroyView()
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}

}

package com.gmail.maystruks08.hikingfood.ui.main.menu.portion

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.core.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_portions.*
import javax.inject.Inject

class ProductPortionForOnePeopleFragment : BaseFragment(), PortionContract.View {

    @Inject
    lateinit var presenter: PortionContract.Presenter

    private lateinit var productsPortionAdapter: ProductsPortionAdapter

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
        productsPortionAdapter = ProductsPortionAdapter(::portionValueChanged)
        allIngredientPortionRecyclerView.layoutManager =
            LinearLayoutManager(allIngredientPortionRecyclerView.context)
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

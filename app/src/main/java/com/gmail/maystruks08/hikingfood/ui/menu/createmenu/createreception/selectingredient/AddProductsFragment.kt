package com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception.selectingredient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.maystruks08.hikingfood.App
import com.gmail.maystruks08.hikingfood.FragmentToolbar
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.core.base.BaseFragment
import com.gmail.maystruks08.hikingfood.ui.adapter.AdapterCallbacks
import com.gmail.maystruks08.hikingfood.ui.adapter.FactoryAdapter
import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactory
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductViewSelectable
import com.gmail.maystruks08.hikingfood.utils.extensions.argument
import kotlinx.android.synthetic.main.fragment_select_products.*
import javax.inject.Inject

class AddProductsFragment : BaseFragment(), AddProductContract.View,
    AdapterCallbacks.OnClickListener<ProductViewSelectable> {

    @Inject
    lateinit var presenter: AddProductContract.Presenter

    @Inject
    lateinit var typesFactory: TypesFactory

    private var onProductSelected: ((List<ProductView>, Boolean) -> Unit?)? = null

    private lateinit var productAdapter: FactoryAdapter

    private var isStaticProducts: Boolean by argument()

    private var productViews: List<ProductView> by argument()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        App.addProductComponent?.inject(this)
        return inflater.inflate(R.layout.fragment_select_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this, productViews)
    }

    override fun builder(): FragmentToolbar =
        FragmentToolbar.Builder()
            .withId(R.id.toolbar)
            .withTitle(R.string.fragment_add_products_to_meal)
            .withNavigationIcon(R.drawable.ic_arrow_back) { addAddSelectedProducts() }
            .build()

    override fun initViews() {
        productAdapter = FactoryAdapter(typesFactory, onClickListener = this)
        selectProductsRecyclerView.layoutManager = LinearLayoutManager(context)
        selectProductsRecyclerView.adapter = productAdapter
        btnAddSelectedIngredients?.setOnClickListener { addAddSelectedProducts() }
        btnDismissDialog.setOnClickListener { presenter.onBackClicked() }
    }

    private fun addAddSelectedProducts() {
        val newProducts = productAdapter.getItems<List<ProductViewSelectable>>()
            .filter { it.productView.isSelected }
        onProductSelected?.invoke(newProducts.map { it.productView }, isStaticProducts)
        presenter.onBackClicked()
    }

    override fun showProducts(products: List<ProductViewSelectable>) {
        productAdapter.items = products.toMutableList()
    }

    override fun onClicked(item: ProductViewSelectable) {
        presenter.onProductClicked(item)
    }

    override fun showProductUpdated(updatedProduct: ProductViewSelectable) {
        productAdapter.updateItem(updatedProduct)
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}

    override fun onDestroyView() {
        App.clearAddProductComponent()
        selectProductsRecyclerView.adapter = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        onProductSelected = null
        super.onDestroy()
    }

    companion object {

        fun getInstance(productViews: List<ProductView>, isStatic: Boolean, listener: (List<ProductView>, Boolean) -> Unit): AddProductsFragment =
            AddProductsFragment().apply {
                this.onProductSelected = listener
                this.productViews = productViews
                this.isStaticProducts = isStatic
            }
    }
}
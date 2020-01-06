package com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.core.base.BaseFragment
import com.gmail.maystruks08.hikingfood.ui.adapter.FactoryAdapter
import com.gmail.maystruks08.hikingfood.ui.adapter.AdapterCallbacks
import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactory
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception.selectingredient.SelectProductsDialog
import com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception.selectingredient.SelectNewProductsListener
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductView
import com.gmail.maystruks08.hikingfood.utils.SwipeActionHelper
import kotlinx.android.synthetic.main.fragment_create_food_reception.*
import javax.inject.Inject

class CreateFoodReceptionFragment : BaseFragment(), CreateFoodReceptionContract.View,
    SelectNewProductsListener {

    @Inject
    lateinit var presenter: CreateFoodReceptionContract.Presenter

    @Inject
    lateinit var typesFactory: TypesFactory

    private lateinit var adapterLoopProducts: FactoryAdapter

    private lateinit var adapterStaticProducts: FactoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.createReceptionComponent?.inject(this)
        return inflater.inflate(R.layout.fragment_create_food_reception, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)
    }

    override fun builder(): FragmentToolbar {
        return FragmentToolbar.Builder()
            .withId(R.id.toolbarCreateReception)
            .withTitle(R.string.fragment_create_food_reception_name)
            .withNavigationIcon(R.drawable.ic_arrow_back) { presenter.onBackClicked() }
            .build()
    }

    override fun showStepProgressView(stepCount: Int, startFrom: TypeOfMeal) {
        vStepProgress.setStepsCount(stepCount, startFrom.type)
        vStepProgress.onStepSelected = {
            presenter.onStepSelected(it)
        }
    }

    override fun initViews() {
        setStaticProductAdapter()
        setLoopProductAdapter()
        initStaticCardSwipe()
        initVariableCardSwipe()

        btnCreateFoodReceiptNextStep.setOnClickListener {
            presenter.onFoodReceptionCreationComplete(
                adapterStaticProducts.items.map { it as ProductView },
                adapterLoopProducts.items.map { it as ProductView }
            )
        }

        fabNewDefaultProduct.setOnClickListener {
            presenter.onAddStaticProductClicked()
        }

        fabAddLoopProducts.setOnClickListener {
            presenter.onAddLoopProductClicked()
        }
    }

    private fun setStaticProductAdapter() {
        adapterStaticProducts = FactoryAdapter(typesFactory, onClickListener = object : AdapterCallbacks.OnClickListener<ProductView> {
            override fun onClicked(item: ProductView) {
                presenter.onStaticProductClicked(item)
            }
        })
        staticProductsRecyclerView.layoutManager = LinearLayoutManager(context)
        staticProductsRecyclerView.adapter = adapterStaticProducts
    }

    private fun setLoopProductAdapter() {
        adapterLoopProducts = FactoryAdapter(typesFactory, onClickListener= object : AdapterCallbacks.OnClickListener<ProductView> {
            override fun onClicked(item: ProductView) {
                presenter.onLoopProductClicked(item)
            }
        })
        loopProductRecyclerView.layoutManager = LinearLayoutManager(context)
        loopProductRecyclerView.adapter = adapterLoopProducts
    }

    private fun initStaticCardSwipe() {
        context?.let {
            val swipeHelper = object : SwipeActionHelper(it) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    if (direction == ItemTouchHelper.LEFT) {
                        presenter.onDeleteStaticProductClicked(position, adapterStaticProducts.items[position] as ProductView)
                    }
                }
            }
            ItemTouchHelper(swipeHelper).attachToRecyclerView(staticProductsRecyclerView)
        }
    }

    private fun initVariableCardSwipe() {
        context?.let {
            val swipeHelper = object : SwipeActionHelper(it) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    if (direction == ItemTouchHelper.LEFT) {
                        presenter.onDeleteLoopProductClicked(position, adapterLoopProducts.items[position] as ProductView)
                    }
                }
            }
            ItemTouchHelper(swipeHelper).attachToRecyclerView(loopProductRecyclerView)
        }
    }


    override fun showStaticProducts(products: List<ProductView>) {
        adapterStaticProducts.items = products.toMutableList()
    }

    override fun showLoopProducts(products: List<ProductView>) {
        adapterLoopProducts.items = products.toMutableList()
    }

    override fun showSelectProductFragment(products: List<ProductView>, isStaticProducts: Boolean) {
        SelectProductsDialog.getInstance(products, isStaticProducts)
            .show(childFragmentManager, Screens.SELECT_PRODUCTS_DIALOG)
    }

    override fun markCurrentStepAsComplete() {
        val isFinished = vStepProgress.nextStep(true)
        if (isFinished) {
            presenter.onProgressFinished()
        } else {
            val currentStep = vStepProgress.getCurrentStep()
            presenter.onStepSelected(currentStep)
        }
    }

    override fun showFinishButton() {
        btnCreateFoodReceiptNextStep.text = "Завершить"
        btnCreateFoodReceiptNextStep.setOnClickListener {
            presenter.onFinishClicked()
        }
    }

    override fun showStaticProductInserted(product: ProductView, position: Int?) {
        adapterStaticProducts.insertItem(product, position)
    }

    override fun showStaticProductRemoved(position: Int) {
        adapterStaticProducts.removeItem(position)
    }

    override fun onStaticProductsSelected(products: List<ProductView>) {
        presenter.onAddStaticProducts(products)
    }

    override fun onLoopProductsSelected(products: List<ProductView>) {
        presenter.onAddLoopProducts(products)
    }

    override fun showLoopProductInserted(product: ProductView, position: Int?) {
        adapterLoopProducts.insertItem(product, position)
    }

    override fun showLoopProductUpdated(updatedProduct: ProductView) {
        adapterLoopProducts.updateItem(updatedProduct)
    }

    override fun showStaticProductUpdated(updatedProduct: ProductView) {
        adapterStaticProducts.updateItem(updatedProduct)
    }

    override fun showLoopProductRemoved(position: Int) {
        adapterLoopProducts.removeItem(position)
    }

    override fun showLoopProductsRemoved(startPosition: Int, count: Int) {
        adapterLoopProducts.removeItems(startPosition, count)
    }

    override fun showStaticProductsRemoved(startPosition: Int, count: Int) {
        adapterStaticProducts.removeItems(startPosition, count)
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}

    override fun onDestroyView() {
        presenter.end()
        staticProductsRecyclerView.adapter = null
        loopProductRecyclerView.adapter = null
        App.clearCreateReceptionComponent()
        super.onDestroyView()
    }
}

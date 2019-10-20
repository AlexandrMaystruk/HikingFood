package com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu.createreception

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.main.menu.ProductAdapter
import com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu.createreception.selectingredient.SelectProductsDialog
import com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu.createreception.selectingredient.SelectLoopProductsListener
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView
import com.gmail.maystruks08.hikingfood.utils.SwipeActionHelper
import kotlinx.android.synthetic.main.fragment_create_food_reception.*
import javax.inject.Inject

class CreateFoodReceptionFragment : Fragment(),
    CreateFoodReceptionContract.View,
    SelectLoopProductsListener {

    @Inject
    lateinit var presenter: CreateFoodReceptionContract.Presenter

    @Inject
    lateinit var controller: ToolBarController

    private lateinit var adapterLoopProducts: ProductAdapter

    private lateinit var adapterStaticProducts: ProductAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        App.createReceptionComponent?.inject(this)
        return inflater.inflate(R.layout.fragment_create_food_reception, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)
        init()
    }

    override fun configToolbar() {
        controller.configure(
            ToolbarDescriptor.Builder()
                .visibility(true)
                .navigationIcon(R.drawable.ic_arrow_back)
                .title("Создание приемов пищи")
                .build(),
            activity as ConfigToolbar
        )
    }

    private fun init() {
        setStaticProductAdapter()
        setLoopProductAdapter()
        initStaticCardSwipe()
        initVariableCardSwipe()
        presenter.initFragment()

        vStepProgress.onStepSelected = {
            presenter.onStepSelected(it)
        }

        btnCreateFoodReceiptNextStep.setOnClickListener {
            presenter.onFoodReceptionCreationComplete(
                adapterStaticProducts.productList,
                adapterLoopProducts.productList
            )
        }

        fabAddLoopProducts.setOnClickListener {
            presenter.onAddVariableProductClicked()
        }
    }

    private fun setStaticProductAdapter() {
        adapterStaticProducts =
            ProductAdapter {
                adapterItemOnLongClicked(it)
            }
        staticProductsRecyclerView.layoutManager = LinearLayoutManager(context)
        staticProductsRecyclerView.adapter = adapterStaticProducts
    }

    private fun setLoopProductAdapter() {
        adapterLoopProducts = ProductAdapter {
                adapterItemOnLongClicked(it)
            }
        loopProductRecyclerView.layoutManager = LinearLayoutManager(context)
        loopProductRecyclerView.adapter = adapterLoopProducts
    }

    private fun initStaticCardSwipe() {
        context?.let {
            val swipeHelper = object : SwipeActionHelper(it) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    if (direction == ItemTouchHelper.LEFT) {
                        presenter.onDeleteStaticProductClicked(
                            position,
                            adapterStaticProducts.productList[position]
                        )
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
                        presenter.onDeleteVariableProductClicked(
                            position,
                            adapterLoopProducts.productList[position]
                        )
                    }
                }
            }
            ItemTouchHelper(swipeHelper).attachToRecyclerView(loopProductRecyclerView)
        }
    }


    override fun showStaticProducts(products: List<ProductView>) {
        adapterStaticProducts.productList = products.toMutableList()
    }

    override fun showLoopProducts(products: List<ProductView>) {
        adapterLoopProducts.productList = products.toMutableList()
    }

    override fun showSelectProductFragment(products: List<ProductView>) {
        SelectProductsDialog.getInstance(products)
            .show(childFragmentManager, Screens.SELECT_PRODUCTS_DIALOG)
    }

    private fun adapterItemOnLongClicked(product: ProductView) {

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
        btnCreateFoodReceiptNextStep.setBackgroundColor(Color.GREEN)
        btnCreateFoodReceiptNextStep.text = "Завершить"
        btnCreateFoodReceiptNextStep.setOnClickListener {
            presenter.onFinishClicked()
        }
    }

    override fun showStaticProductInserted(product: ProductView) {
        adapterStaticProducts.productList.add(product)
        adapterStaticProducts.notifyItemInserted(adapterStaticProducts.productList.indexOf(product))
    }

    override fun showStaticProductRemoved(position: Int) {
        adapterStaticProducts.productList.removeAt(position)
        adapterStaticProducts.notifyItemRemoved(position)
        adapterStaticProducts.notifyItemChanged(position, adapterStaticProducts.itemCount)
    }


    override fun onLoopProductsSelected(products: List<ProductView>) {
        presenter.onVariableProductsSelected(products)
    }

    override fun showVariableProductInserted(product: ProductView) {
        adapterLoopProducts.productList.add(product)
        adapterLoopProducts.notifyItemInserted(adapterLoopProducts.productList.indexOf(product))
    }

    override fun showVariableProductRemoved(position: Int) {
        adapterLoopProducts.productList.removeAt(position)
        adapterLoopProducts.notifyItemRemoved(position)
        adapterLoopProducts.notifyItemRangeChanged(position, adapterLoopProducts.itemCount)
    }

    override fun onDestroyView() {
        presenter.end()
        staticProductsRecyclerView.adapter = null
        loopProductRecyclerView.adapter = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        App.clearCreateReceptionComponent()
        super.onDestroy()
    }
    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}

}

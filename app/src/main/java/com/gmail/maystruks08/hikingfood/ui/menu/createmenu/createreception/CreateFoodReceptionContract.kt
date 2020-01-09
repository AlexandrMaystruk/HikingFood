package com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception

import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductView

interface CreateFoodReceptionContract {

    interface View : IView {

        fun markCurrentStepAsComplete()

        fun showFinishButton()

        fun showSelectProductFragment(products: List<ProductView>, isStaticProducts: Boolean)

        fun showStaticProducts(products: List<ProductView>)
        fun showLoopProducts(products: List<ProductView>)

        fun showStaticProductInserted(product: ProductView, position: Int? = null)
        fun showLoopProductInserted(product: ProductView, position: Int? = null)

        fun showStaticProductUpdated(updatedProduct: ProductView)
        fun showLoopProductUpdated(updatedProduct: ProductView)

        fun showStaticProductRemoved(position: Int)
        fun showLoopProductRemoved(position: Int)

        fun showStaticProductsRemoved(startPosition: Int, count: Int)
        fun showLoopProductsRemoved(startPosition: Int, count: Int)

        fun showStepProgressView(stepCount: Int, startFrom: TypeOfMeal)
    }


    interface Presenter : IPresenter<View> {

        fun onAddStaticProductClicked()

        fun onAddLoopProductClicked()

        fun onFoodReceptionCreationComplete(staticProductList: List<ProductView>, loopProductList: List<ProductView>)

        fun onStepSelected(step: Int)

        fun onProgressFinished()

        fun onFinishClicked()

        fun onStaticProductClicked(productView: ProductView)

        fun onLoopProductClicked(productView: ProductView)

        fun onAddStaticProducts(products: List<ProductView>)

        fun onAddLoopProducts(products: List<ProductView>)

        fun onDeleteStaticProductClicked(position: Int, productView: ProductView)

        fun onDeleteLoopProductClicked(position: Int, productView: ProductView)

    }
}

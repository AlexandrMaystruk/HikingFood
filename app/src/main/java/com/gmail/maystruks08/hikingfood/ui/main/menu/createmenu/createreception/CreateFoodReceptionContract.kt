package com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu.createreception

import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView

interface CreateFoodReceptionContract {

    interface View : IView {

        fun markCurrentStepAsComplete()

        fun showFinishButton()

        fun showSelectProductFragment(products: List<ProductView>, isStaticProducts: Boolean)

        fun showStaticProducts(products: List<ProductView>)

        fun showLoopProducts(products: List<ProductView>)

        fun showStaticProductInserted(product: ProductView)

        fun showLoopProductInserted(product: ProductView)

        fun showStaticProductRemoved(position: Int)

        fun showVariableProductRemoved(position: Int)

        fun showStepProgressView(stepCount: Int, startFrom: TypeOfMeal)
    }


    interface Presenter : IPresenter<View> {

        fun initFragment()

        fun onAddStaticProductClicked()

        fun onAddLoopProductClicked()

        fun onFoodReceptionCreationComplete(staticProductList: List<ProductView>, loopProductList: List<ProductView>)

        fun onStepSelected(step: Int)

        fun onProgressFinished()

        fun onFinishClicked()

        fun onStaticProductsSelected(products: List<ProductView>)

        fun onLoopProductsSelected(products: List<ProductView>)

        fun onDeleteStaticProductClicked(position: Int, productView: ProductView)

        fun onDeleteVariableProductClicked(position: Int, productView: ProductView)

    }
}

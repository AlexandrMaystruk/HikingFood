package com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu.createreception

import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView

interface CreateFoodReceptionContract {

    interface View : IView {

        fun markCurrentStepAsComplete()

        fun showFinishButton()

        fun showSelectProductFragment(products: List<ProductView>)

        fun showStaticProducts(products: List<ProductView>)

        fun showLoopProducts(products: List<ProductView>)

        fun showStaticProductInserted(product: ProductView)

        fun showVariableProductInserted(product: ProductView)

        fun showStaticProductRemoved(position: Int)

        fun showVariableProductRemoved(position: Int)
    }


    interface Presenter : IPresenter<View> {

        fun initFragment()

        fun onAddVariableProductClicked()

        fun onFoodReceptionCreationComplete(staticProductList: List<ProductView>, loopProductList: List<ProductView>)

        fun onStepSelected(step: Int)

        fun onProgressFinished()

        fun onFinishClicked()

        fun onVariableProductsSelected(products: List<ProductView>)

        fun onDeleteStaticProductClicked(position: Int, productView: ProductView)

        fun onDeleteVariableProductClicked(position: Int, productView: ProductView)

    }
}

package com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu.createreception

import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView

interface CreateFoodReceptionContract {

    interface View : IView {

        fun showStaticProducts(products: List<ProductView>)

        fun showLoopProducts(products: List<ProductView>)

        fun markCurrentStepAsComplete()

        fun showFinishButton()

        fun showSelectProductFragment(products: List<ProductView>)

        fun showLoopProductInserted(product: ProductView)

        fun showLoopProductRemoved(position: Int)
    }


    interface Presenter : IPresenter<View> {

        fun initFragment()

        fun onAddLoopProductClicked()


        fun onFoodReceptionCreationComplete(staticProductList: List<ProductView>, loopProductList: List<ProductView>)

        fun onStepSelected(step: Int)

        fun onProgressFinished()

        fun onFinishClicked()

        fun onLoopProductsSelected(products: List<ProductView>)

        fun onDeleteLoopProductClicked(position: Int, productView: ProductView)


    }
}

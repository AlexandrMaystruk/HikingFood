package com.gmail.maystruks08.hikingfood.ui.createmenu.createreception

import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView

interface CreateFoodReceptionContract {

    interface View : IView {

        fun showDefaultMenuIngredient(products: List<ProductView>)

        fun showVariableMenuIngredient(products: List<ProductView>)

        fun markCurrentStepAsComplete()

        fun showFinishButton()

        fun showSelectIngredientFragment(products: List<ProductView>)
    }


    interface Presenter : IPresenter<View> {

        fun initFragment()

        fun onAddVariableProductClicked()



        fun onFoodReceptionCreationComplete(defProductList: List<ProductView>, mutableProductList: List<ProductView>)

        fun onStepSelected(step: Int)

        fun onProgressFinished()

        fun onFinishClicked()

        fun onVariableProductsSelected(products: List<ProductView>)




    }
}

package com.gmail.maystruks08.hikingfood.ui.createmenu.createreception

import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.domain.interactor.createmenu.createbreakfast.CreateReceptionInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers.DefaultIngredientViewMapper
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class CreateFoodReceptionPresenter @Inject constructor(
    private val router: Router, private val interactor: CreateReceptionInteractor,
    private val defaultProductViewMapper: DefaultIngredientViewMapper
) :
    CreateFoodReceptionContract.Presenter, BasePresenter<CreateFoodReceptionContract.View>() {

    var typeOfMeal: TypeOfMeal = TypeOfMeal.BREAKFAST

    override fun initFragment() {
        view?.showLoading()
        compositeDisposable.add(
            interactor.getAllDefaultProducts(typeOfMeal)
                .subscribe({ meal ->
                    view?.hideLoading()
                    view?.showDefaultMenuIngredient(meal.defProducts.map {
                        defaultProductViewMapper.fromProduct(it)
                    })

                    view?.showVariableMenuIngredient(meal.loopProducts.map {
                        defaultProductViewMapper.fromProduct(it)
                    })
                }, {
                    view?.hideLoading()
                    it.printStackTrace()
                })
        )
    }

    override fun onAddVariableProductClicked() {
        view?.showLoading()
        compositeDisposable.add(
            interactor.getDefaultVariableProducts(typeOfMeal)
                .subscribe({ list ->
                    view?.hideLoading()
                    view?.showSelectIngredientFragment(
                        list.map { defaultProductViewMapper.fromProduct(it) })
                }, {
                    view?.hideLoading()
                    it.printStackTrace()
                })
        )
    }

    override fun onStepSelected(step: Int) {
        typeOfMeal = TypeOfMeal.fromValue(step)
        initFragment()
    }

    override fun onFoodReceptionCreationComplete(
        defProductList: List<ProductView>,
        mutableProductList: List<ProductView>
    ) {
        compositeDisposable.add(
            interactor.onFoodReceptionCreationComplete(
                typeOfMeal,
                defProductList.mapNotNull { interactor.getProductById(it.id) },
                mutableProductList.mapNotNull { interactor.getProductById(it.id) }).subscribe(
                {
                    view?.markCurrentStepAsComplete()
                    typeOfMeal = TypeOfMeal.getNextValue(typeOfMeal.type)
                    initFragment()
                },
                {
                    it.printStackTrace()
                })
        )
    }

    override fun onVariableProductsSelected(products: List<ProductView>) {
        view?.showVariableMenuIngredient(products)
        //TODO save to storage and recalculate menu
    }

    override fun onProgressFinished() {
        view?.showFinishButton()
    }


    override fun onFinishClicked() {
        compositeDisposable.add(
            interactor.onFinishCreateReception().subscribe(
                {
                    router.newRootScreen(Screens.AllMenuScreen())
                },
                {
                    it.printStackTrace()
                })
        )
    }
}

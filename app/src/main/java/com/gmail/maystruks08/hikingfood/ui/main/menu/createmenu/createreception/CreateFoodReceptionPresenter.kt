package com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu.createreception

import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.domain.interactor.createmenu.createbreakfast.CreateReceptionInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers.ProductViewMapper
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class CreateFoodReceptionPresenter @Inject constructor(
    private val router: Router, private val interactor: CreateReceptionInteractor,
    private val productViewMapper: ProductViewMapper
) :
    CreateFoodReceptionContract.Presenter, BasePresenter<CreateFoodReceptionContract.View>() {

    private var typeOfMeal: TypeOfMeal = TypeOfMeal.BREAKFAST

    override fun initFragment() {
        view?.showLoading()
        compositeDisposable.add(
            interactor.getAllDefaultProducts(typeOfMeal)
                .subscribe({ meal ->
                    view?.hideLoading()
                    view?.showStaticProducts(productViewMapper.fromProducts(meal.defProducts))
                    view?.showLoopProducts(productViewMapper.fromProducts(meal.loopProducts))
                }, {
                    view?.hideLoading()
                    it.printStackTrace()
                })
        )
    }

    override fun onAddVariableProductClicked() {
        view?.showLoading()
        compositeDisposable.add(
            interactor.getDefaultLoopProducts(typeOfMeal)
                .subscribe({ list ->
                    view?.hideLoading()
                    view?.showSelectProductFragment(productViewMapper.fromProducts(list))
                }, {
                    view?.hideLoading()
                    it.printStackTrace()
                })
        )
    }

    override fun onDeleteStaticProductClicked(position: Int, productView: ProductView) {
        compositeDisposable.add(
            interactor.removeStaticProduct(typeOfMeal, productView.id)
                .subscribe(
                    {
                        view?.showStaticProductRemoved(position)
                    }, {
                        it.printStackTrace()
                    })
        )
    }

    override fun onDeleteVariableProductClicked(position: Int, productView: ProductView) {
        compositeDisposable.add(
            interactor.removeLoopProduct(typeOfMeal, productView.id)
                .subscribe(
                    {
                        view?.showVariableProductRemoved(position)
                    }, {
                        it.printStackTrace()
                    })
        )
    }

    override fun onStepSelected(step: Int) {
        typeOfMeal = TypeOfMeal.fromValue(step)
        initFragment()
    }

    override fun onFoodReceptionCreationComplete(
        staticProductList: List<ProductView>,
        loopProductList: List<ProductView>
    ) {
        compositeDisposable.add(
            interactor.onFoodReceptionCreationComplete(
                typeOfMeal,
                staticProductList.mapNotNull {
                    if (it.isChild) {
                        return@mapNotNull null
                    }
                    interactor.getProductById(it.id)
                },
                loopProductList.mapNotNull {
                    if (it.isChild) {
                        return@mapNotNull null
                    }
                    interactor.getProductById(it.id)

                }).subscribe(
                {
                    view?.markCurrentStepAsComplete()
                },
                {
                    it.printStackTrace()
                })
        )
    }

    override fun onVariableProductsSelected(products: List<ProductView>) {
        compositeDisposable.add(
            interactor.onLoopProductsAdded(typeOfMeal, products.map { it.id }).subscribe({
                products.forEach {
                    view?.showVariableProductInserted(it)
                }
            }, {
                it.printStackTrace()
            })
        )
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

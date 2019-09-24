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

    var typeOfMeal: TypeOfMeal = TypeOfMeal.BREAKFAST

    override fun initFragment() {
        view?.showLoading()
        compositeDisposable.add(
            interactor.getAllDefaultProducts(typeOfMeal)
                .subscribe({ meal ->
                    view?.hideLoading()
                    view?.showStaticProducts(meal.defProducts.map { productViewMapper.fromProduct(it) })
                    view?.showLoopProducts(meal.loopProducts.map { productViewMapper.fromProduct(it) })
                }, {
                    view?.hideLoading()
                    it.printStackTrace()
                })
        )
    }

    override fun onAddLoopProductClicked() {
        view?.showLoading()
        compositeDisposable.add(
            interactor.getDefaultLoopProducts(typeOfMeal)
                .subscribe({ list ->
                    view?.hideLoading()
                    view?.showSelectProductFragment(list.map { productViewMapper.fromProduct(it) })
                }, {
                    view?.hideLoading()
                    it.printStackTrace()
                })
        )
    }

    override fun onDeleteLoopProductClicked(position: Int, productView: ProductView) {
        view?.showLoopProductRemoved(position)
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
                staticProductList.mapNotNull { interactor.getProductById(it.id) },
                loopProductList.mapNotNull { interactor.getProductById(it.id) }).subscribe(
                {
                    view?.markCurrentStepAsComplete()
                },
                {
                    it.printStackTrace()
                })
        )
    }

    override fun onLoopProductsSelected(products: List<ProductView>) {
        compositeDisposable.add(
            interactor.onLoopProductsAdded(typeOfMeal, products.map { it.id }).subscribe({
                products.forEach {
                    view?.showLoopProductInserted(it)
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

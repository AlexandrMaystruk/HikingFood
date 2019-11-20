package com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu.createreception

import com.gmail.maystruks08.domain.entity.ProductSet
import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.domain.interactor.createmenu.createbreakfast.CreateReceptionInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers.ProductViewMapper
import javax.inject.Inject

class CreateFoodReceptionPresenter @Inject constructor(
    private val interactor: CreateReceptionInteractor,
    private val productViewMapper: ProductViewMapper
) : CreateFoodReceptionContract.Presenter, BasePresenter<CreateFoodReceptionContract.View>() {

    private var initConfig: CreateReceptionInteractor.Config? = null
    private var typeOfMeal: TypeOfMeal = TypeOfMeal.BREAKFAST
    private var shift = 0
    private var receiptCount = 3
    private var staticProducts = mutableListOf<ProductView>()
    private var loopProducts = mutableListOf<ProductView>()

    override fun bindView(view: CreateFoodReceptionContract.View) {
        super.bindView(view)
        compositeDisposable.add(interactor.getInitConfig()
            .flatMap {
                initConfig = it
                receiptCount = if (it.countOfReceipt > 3) 3 else it.countOfReceipt
                interactor.getAllDefaultProducts(typeOfMeal)
            }
            .doOnSubscribe { view.showLoading() }
            .doAfterTerminate { view.hideLoading() }
            .subscribe({ meal ->
                staticProducts = productViewMapper.fromProducts(meal.defProducts)
                loopProducts = productViewMapper.fromProducts(meal.loopProducts)
                view.run {
                    showStepProgressView(receiptCount, typeOfMeal)
                    showStaticProducts(staticProducts)
                    showLoopProducts(loopProducts)
                }
            }, {
                it.printStackTrace()
            })
        )
    }

    override fun initFragment() {
        compositeDisposable.add(
            interactor.getAllDefaultProducts(typeOfMeal)
                .doOnSubscribe { view?.showLoading() }
                .doAfterTerminate { view?.hideLoading() }
                .subscribe({ meal ->
                    staticProducts = productViewMapper.fromProducts(meal.defProducts)
                    loopProducts = productViewMapper.fromProducts(meal.loopProducts)
                    view?.run {
                        showStaticProducts(staticProducts)
                        showLoopProducts(loopProducts)
                    }
                }, {
                    it.printStackTrace()
                })
        )
    }

    override fun onAddStaticProductClicked() {
        compositeDisposable.add(
            interactor.getDefaultStaticProducts()
                .doOnSubscribe { view?.showLoading() }
                .doAfterTerminate { view?.hideLoading() }
                .subscribe({ list ->
                    val newProducts =
                        productViewMapper.fromProducts(list).mapNotNull { productView ->
                            if (staticProducts.find { it.id == productView.id } != null) {
                                null
                            } else {
                                productView.apply { isSelected = false }
                            }
                        }
                    view?.showSelectProductFragment(newProducts, true)
                }, {
                    it.printStackTrace()
                })
        )
    }

    override fun onAddLoopProductClicked() {
        compositeDisposable.add(
            interactor.getDefaultLoopProducts()
                .doOnSubscribe { view?.showLoading() }
                .doAfterTerminate { view?.hideLoading() }
                .subscribe({ list ->
                    val newProducts =
                        productViewMapper.fromProducts(list).mapNotNull { productView ->
                            if (loopProducts.find { it.id == productView.id } != null) {
                                null
                            } else {
                                productView.apply { isSelected = false }
                            }
                        }
                    view?.showSelectProductFragment(newProducts, false)
                }, {
                    it.printStackTrace()
                })
        )
    }

    override fun onDeleteStaticProductClicked(position: Int, productView: ProductView) {
        compositeDisposable.add(
            interactor.removeStaticProduct(typeOfMeal, productView.id)
                .doOnSubscribe { view?.showLoading() }
                .doAfterTerminate { view?.hideLoading() }
                .subscribe(
                    {
                        view?.showStaticProductRemoved(position)
                        interactor.getProductById(productView.id)?.let {
                            if (it is ProductSet && productView.isSelected) {
                                repeat(it.products.size) { view?.showStaticProductRemoved(position) }
                            }
                        }
                    }, {
                        it.printStackTrace()
                    })
        )
    }

    override fun onDeleteVariableProductClicked(position: Int, productView: ProductView) {
        compositeDisposable.add(
            interactor.removeLoopProduct(typeOfMeal, productView.id)
                .doOnSubscribe { view?.showLoading() }
                .doAfterTerminate { view?.hideLoading() }
                .subscribe(
                    {
                        view?.showVariableProductRemoved(position)
                        interactor.getProductById(productView.id)?.let {
                            if (it is ProductSet && productView.isSelected) {
                                repeat(it.products.size) { view?.showVariableProductRemoved(position) }
                            }
                        }
                    }, {
                        it.printStackTrace()
                    })
        )
    }

    override fun onStepSelected(step: Int) {
        typeOfMeal = TypeOfMeal.fromValue(step, shift)
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

    override fun onStaticProductsSelected(products: List<ProductView>) {
        compositeDisposable.add(
            interactor.onStaticProductsAdded(typeOfMeal, products.map { it.id }).subscribe({
                products.forEach {
                    val productView = it.apply { isSelected = false }
                    staticProducts.add(productView)
                    view?.showStaticProductInserted(productView)
                }
            }, {
                it.printStackTrace()
            })
        )
    }

    override fun onLoopProductsSelected(products: List<ProductView>) {
        compositeDisposable.add(
            interactor.onLoopProductsAdded(typeOfMeal, products.map { it.id }).subscribe({
                products.forEach {
                    val productView = it.apply { isSelected = false }
                    loopProducts.add(productView)
                    view?.showLoopProductInserted(productView)
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

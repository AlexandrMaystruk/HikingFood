package com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception

import android.util.Log
import com.gmail.maystruks08.domain.entity.ProductSet
import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.domain.interactor.createmenu.createreception.CreateReceptionInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.SetProductView
import com.gmail.maystruks08.hikingfood.ui.adapter.toProductView
import com.gmail.maystruks08.hikingfood.ui.adapter.toProductViewList
import javax.inject.Inject

class CreateFoodReceptionPresenter @Inject constructor(
    private val interactor: CreateReceptionInteractor
) : CreateFoodReceptionContract.Presenter, BasePresenter<CreateFoodReceptionContract.View>() {

    private lateinit var config: CreateReceptionInteractor.Config
    private var shift = 0
    private val receptionsInDay = 3
    private var staticProducts = mutableListOf<ProductView>()
    private var loopProducts = mutableListOf<ProductView>()

    override fun bindView(view: CreateFoodReceptionContract.View) {
        super.bindView(view)
        compositeDisposable.add(interactor.getInitConfig()
            .flatMap {
                config = it
                shift = it.currentMeal.type
                interactor.getAllDefaultProducts(config.currentMeal)
            }
            .doOnSubscribe { view.showLoading() }
            .doAfterTerminate { view.hideLoading() }
            .subscribe({ meal ->
                staticProducts = meal.defProducts.toProductViewList().toMutableList()
                loopProducts = meal.loopProducts.toProductViewList().toMutableList()
                view.run {
                    val stepCount = if (config.countOfReceipt > receptionsInDay) receptionsInDay else config.countOfReceipt
                    showStepProgressView(stepCount, config.currentMeal)
                    showStaticProducts(staticProducts)
                    showLoopProducts(loopProducts)
                }
            }, { it.printStackTrace() })
        )
    }

    override fun onStepSelected(step: Int) {
        config.currentMeal = TypeOfMeal.fromValue(step, shift)
        initFragment()
    }

    private fun initFragment() {
        compositeDisposable.add(
            interactor.getAllDefaultProducts(config.currentMeal)
                .doOnSubscribe { view?.showLoading() }
                .doAfterTerminate { view?.hideLoading() }
                .subscribe({ meal ->
                    staticProducts = meal.defProducts.toProductViewList().toMutableList()
                    loopProducts = meal.loopProducts.toProductViewList().toMutableList()
                    view?.run {
                        showStaticProducts(staticProducts)
                        showLoopProducts(loopProducts)
                    }
                }, { it.printStackTrace() })
        )
    }

    override fun onStaticProductClicked(productView: ProductView) {
        if (productView is SetProductView) {
            val position = staticProducts.indexOfFirst { it.id == productView.id }
            val productSet = (staticProducts[position] as SetProductView)
            val isProductSelected = productSet.isSelected
            val updatedProduct = productSet.apply { isSelected = !isProductSelected }
            staticProducts[position] = updatedProduct
            view?.showStaticProductUpdated(updatedProduct)
            (interactor.getProductById(productView.id) as? ProductSet)?.products.let { products ->
                products?.forEach {
                    if (isProductSelected) {
                        staticProducts.removeAt(position + 1)
                        view?.showStaticProductRemoved(position + 1)
                    } else {
                        staticProducts.add(position + 1, it.toProductView(productSet.id))
                        view?.showStaticProductInserted(it.toProductView(productSet.id), position + 1)
                    }
                }
            }
        }
    }

    override fun onLoopProductClicked(productView: ProductView) {
        if (productView is SetProductView) {
            val position = loopProducts.indexOfFirst { it.id == productView.id }
            val productSet = (loopProducts[position] as SetProductView)
            val isProductSelected = productSet.isSelected
            val updatedProduct = productSet.apply { isSelected = !isProductSelected }
            loopProducts[position] = updatedProduct
            view?.showLoopProductUpdated(updatedProduct)
            (interactor.getProductById(productView.id) as? ProductSet)?.products?.let { products ->
                products.forEach {
                    if (isProductSelected) {
                        loopProducts.removeAt(position + 1)
                        view?.showLoopProductRemoved(position + 1)
                    } else {
                        loopProducts.add(position + 1, it.toProductView(productSet.id))
                        view?.showLoopProductInserted(it.toProductView(productSet.id), position + 1)
                    }
                }
            }
        }
    }

    override fun onDeleteStaticProductClicked(position: Int, productView: ProductView) {
        compositeDisposable.add(
            interactor.removeStaticProduct(typeOfMeal = config.currentMeal, productId = productView.id, parentId = productView.parentId)
                .doOnSubscribe { view?.showLoading() }
                .doAfterTerminate { view?.hideLoading() }
                .subscribe({
                    var count = 0
                    if (productView is SetProductView) {
                        staticProducts.removeAll {
                            if (it.parentId == productView.id || it.id == productView.id) {
                                count++
                                Log.d("TAGG", "Removed static ${it.name} ${it.parentId}")
                                return@removeAll true
                            }
                            return@removeAll false
                        }
                        view?.showLoopProductsRemoved(position, count)
                    } else {
                        staticProducts.removeAll {
                            if ((it.id == productView.id && it.parentId == productView.parentId)) {
                                val index = staticProducts.indexOf(it)
                                view?.showStaticProductRemoved(index)
                                Log.d("TAGG", "Removed static ${it.name} ${it.parentId}")
                                return@removeAll true
                            }
                            return@removeAll false
                        }
                    }
                }, { it.printStackTrace() })
        )
    }

    override fun onDeleteLoopProductClicked(position: Int, productView: ProductView) {
        compositeDisposable.add(
            interactor.removeLoopProduct(typeOfMeal = config.currentMeal, productId = productView.id, parentId = productView.parentId)
                .doOnSubscribe { view?.showLoading() }
                .doAfterTerminate { view?.hideLoading() }
                .subscribe({
                    var count = 0
                    if(productView is SetProductView){
                        loopProducts.removeAll {
                            if (it.parentId == productView.id || it.id == productView.id) {
                                count++
                                Log.d("TAGG", "Removed loop ${it.name} ${it.parentId}")
                                return@removeAll true
                            }
                            return@removeAll false
                        }
                        view?.showLoopProductsRemoved(position, count)
                    } else {
                        loopProducts.removeAll {
                            if ((it.id == productView.id && it.parentId == productView.parentId)) {
                                val index = loopProducts.indexOf(it)
                                view?.showLoopProductRemoved(index)
                                Log.d("TAGG", "Removed loop$ ${it.name} ${it.parentId}")
                                return@removeAll true
                            }
                            return@removeAll false
                        }
                    }
                }, { it.printStackTrace() })
        )
    }


    override fun onAddStaticProductClicked() {
        compositeDisposable.add(
            interactor.getDefaultStaticProducts()
                .doOnSubscribe { view?.showLoading() }
                .doAfterTerminate { view?.hideLoading() }
                .subscribe({ list ->
                    val newProducts = list.toProductViewList().mapNotNull { productView ->
                        if (staticProducts.find { it.id == productView.id } != null) {
                            null
                        } else {
                            productView
                        }
                    }
                    router.navigateTo(Screens.AddProductsScreen(newProducts, true, ::onProductsSelected))
                }, { it.printStackTrace() })
        )
    }

    override fun onAddLoopProductClicked() {
        compositeDisposable.add(
            interactor.getDefaultLoopProducts()
                .doOnSubscribe { view?.showLoading() }
                .doAfterTerminate { view?.hideLoading() }
                .subscribe({ list ->
                    val newProducts = list.toProductViewList().mapNotNull { productView ->
                        if (loopProducts.find { it.id == productView.id } != null) {
                            null
                        } else {
                            productView
                        }
                    }
                    router.navigateTo(Screens.AddProductsScreen(newProducts, false, ::onProductsSelected))
                }, { it.printStackTrace() })
        )
    }

    private fun onProductsSelected(products: List<ProductView>, isDefProducts: Boolean){
        if(isDefProducts){
            onAddStaticProducts(products)
        } else {
            onAddLoopProducts(products)
        }
    }

    private fun onAddStaticProducts(products: List<ProductView>) {
        compositeDisposable.add(
            interactor.onStaticProductsAdded(
                config.currentMeal,
                products.map { it.id }).subscribe({
                products.forEach { productView ->
                    staticProducts.add(productView)
                    view?.showStaticProductInserted(productView)
                }
            }, { it.printStackTrace() })
        )
    }

    private fun onAddLoopProducts(products: List<ProductView>) {
        compositeDisposable.add(
            interactor.onLoopProductsAdded(
                config.currentMeal,
                products.map { it.id }).subscribe({
                products.forEach { productView ->
                    loopProducts.add(productView)
                    view?.showLoopProductInserted(productView)
                }
            }, { it.printStackTrace() })
        )
    }

    override fun onFoodReceptionCreationComplete(staticProductList: List<ProductView>, loopProductList: List<ProductView>) {
        compositeDisposable.add(
            interactor.onFoodReceptionCreationComplete(
                config.currentMeal,
                staticProductList.mapNotNull {
                    if (it.parentId != null) {
                        return@mapNotNull null
                    }
                    interactor.getProductById(it.id)
                },
                loopProductList.mapNotNull {
                    if (it.parentId != null) {
                        return@mapNotNull null
                    }
                    interactor.getProductById(it.id)

                }).subscribe({ view?.markCurrentStepAsComplete() }, { it.printStackTrace() })
        )
    }

    override fun onProgressFinished() {
        view?.showFinishButton()
    }

    override fun onFinishClicked() {
        compositeDisposable.add(
            interactor.onFinishCreateReception().subscribe({
                router.newRootScreen(Screens.AllMenuScreen())
            }, { it.printStackTrace() })
        )
    }
}

package com.gmail.maystruks08.domain.interactor.createmenu.createbreakfast

import com.gmail.maystruks08.domain.entity.FoodMeal
import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.domain.entity.ProductSet
import com.gmail.maystruks08.domain.entity.TypeOfMeal
import io.reactivex.Completable
import io.reactivex.Single

interface CreateReceptionInteractor {

    fun getAllDefaultProducts(typeOfMeal: TypeOfMeal): Single<FoodMeal>

    fun getDefaultStaticProducts(): Single<List<Product>>

    fun getDefaultLoopProducts(): Single<List<Product>>

    fun getProductById(productId: Int): Product?

    fun getSoupSets(typeOfMeal: TypeOfMeal): Single<List<ProductSet>>

    fun onFoodReceptionCreationComplete(mealType: TypeOfMeal, defaultProductList: List<Product>, variableProductList: List<Product>): Completable

    fun onFinishCreateReception(): Completable

    /**This is list of product ids */
    fun onStaticProductsAdded(typeOfMeal: TypeOfMeal, productIds: List<Int>): Completable

    fun onLoopProductsAdded(typeOfMeal: TypeOfMeal, productIds: List<Int>): Completable

    fun removeLoopProduct(typeOfMeal: TypeOfMeal, productId: Int): Completable

    fun removeStaticProduct(typeOfMeal: TypeOfMeal, productId: Int): Completable

    fun getInitConfig(): Single<Config>

    class Config(val countOfReceipt: Int, var startFrom: TypeOfMeal)
}
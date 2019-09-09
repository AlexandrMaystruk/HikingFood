package com.gmail.maystruks08.domain.interactor.createmenu.createbreakfast

import com.gmail.maystruks08.domain.entity.FoodMeal
import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.domain.entity.SoupSet
import com.gmail.maystruks08.domain.entity.TypeOfMeal
import io.reactivex.Completable
import io.reactivex.Single

interface CreateReceptionInteractor {

    fun getAllDefaultProducts(typeOfMeal: TypeOfMeal): Single<FoodMeal>

    fun getDefaultVariableProducts(typeOfMeal: TypeOfMeal): Single<List<Product>>

    fun getProductById(productId: Int): Product?

    fun getSoupSets(typeOfMeal: TypeOfMeal): Single<List<SoupSet>>

    fun onFoodReceptionCreationComplete(mealType: TypeOfMeal, defaultProductList: List<Product>, variableProductList: List<Product>): Completable

    fun onFinishCreateReception(): Completable
}
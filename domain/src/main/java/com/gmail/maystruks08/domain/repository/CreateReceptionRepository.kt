package com.gmail.maystruks08.domain.repository

import com.gmail.maystruks08.domain.entity.*
import io.reactivex.Completable
import io.reactivex.Single

interface CreateReceptionRepository {

    fun getStartInquirerInfo(): Single<StartInquirerInfo>

    fun getDefaultMealProducts(typeOfMeal: TypeOfMeal): Single<FoodMeal>

    fun getDefaultVariableMealProducts(typeOfMeal: TypeOfMeal): Single<List<Product>>

    fun removeLoopProduct(typeOfMeal: TypeOfMeal, productId: Int): Completable

    fun removeStaticProduct(typeOfMeal: TypeOfMeal, productId: Int): Completable

    fun getSoupSets(): Single<List<ProductSet>>

    fun saveFoodMeal(typeOfMeal: TypeOfMeal, foodMeal: FoodMeal): Completable

    fun getProductById(id: Int): Product?

    fun saveMenu(menu: Menu): Completable
}
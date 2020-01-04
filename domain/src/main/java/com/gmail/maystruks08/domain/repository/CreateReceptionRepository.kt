package com.gmail.maystruks08.domain.repository

import com.gmail.maystruks08.domain.entity.*
import io.reactivex.Completable
import io.reactivex.Single

interface CreateReceptionRepository {

    fun getStartInquirerInfo(): Single<StartInquirerInfo>

    fun getDefaultMealProducts(typeOfMeal: TypeOfMeal): Single<FoodMeal>

    fun getDefaultStaticProducts(): Single<List<Product>>

    fun getDefaultLoopProducts(): Single<List<Product>>

    fun removeLoopProduct(typeOfMeal: TypeOfMeal, productId: Long, parentId: Long?): Completable

    fun removeStaticProduct(typeOfMeal: TypeOfMeal, productId: Long,parentId: Long?): Completable

    fun getSoupSets(): Single<List<ProductSet>>

    fun saveFoodMeal(typeOfMeal: TypeOfMeal, foodMeal: FoodMeal): Completable

    fun getProductById(productId: Long): Product?

    fun saveMenu(menu: Menu): Completable
}
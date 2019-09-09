package com.gmail.maystruks08.domain.repository

import com.gmail.maystruks08.domain.entity.*
import io.reactivex.Completable
import io.reactivex.Single

interface CreateMenuRepository {

    //return default values
    fun getDefaultProductPortions(): Single<List<Product>>

    fun getDefaultSoupSet(): Single<List<SoupSet>>

    fun getDefaultFoodMeals(): Single<Map<TypeOfMeal, FoodMeal>>

    //work with StartInquirerData
    fun getStartInquirerData(): Single<StartInquirerInfo>

    fun saveStartInquirerData(startInquirerInfo: StartInquirerInfo): Completable

    fun clearStartInquirerData(): Completable

}
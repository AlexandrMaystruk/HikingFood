package com.gmail.maystruks08.domain.repository

import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.domain.entity.StartInquirerInfo
import io.reactivex.Completable
import io.reactivex.Single

interface CreateMenuRepository {

    fun getDefaultIngredientPortions(): Single<List<Product>>

    fun getStartInquirerData(): Single<StartInquirerInfo>

    fun saveStartInquirerData(startInquirerInfo: StartInquirerInfo): Completable

    fun clearStartInquirerData(): Completable

}
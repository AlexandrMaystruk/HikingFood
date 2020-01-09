package com.gmail.maystruks08.domain.interactor.dose

import com.gmail.maystruks08.domain.entity.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductPortionInteractor {

    fun getProducts(): Single<List<Product>>

    fun onPortionValueChanged(newValue: Int, productId: Long): Completable
}
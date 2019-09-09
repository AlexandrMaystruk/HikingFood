package com.gmail.maystruks08.domain.repository

import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.domain.entity.StartInquirerInfo
import io.reactivex.Single

interface ProductPortionRepository {

    fun getStartInquirerInfo(): Single<StartInquirerInfo>

    fun saveStartInquirerInfo(startInquirerInfo: StartInquirerInfo)

    fun getAllProducts(): Single<List<Product>>

}
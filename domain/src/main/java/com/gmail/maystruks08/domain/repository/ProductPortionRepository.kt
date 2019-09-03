package com.gmail.maystruks08.domain.repository

import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.domain.entity.StartInquirerInfo
import io.reactivex.Single

interface ProductPortionRepository {

    fun getAllProducts(): Single<List<Product>>

    fun getStartInquirerInfo(): Single<StartInquirerInfo>

    fun setStartInquirerInfo(startInquirerInfo: StartInquirerInfo)

}
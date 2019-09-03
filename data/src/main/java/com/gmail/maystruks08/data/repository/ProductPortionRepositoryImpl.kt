package com.gmail.maystruks08.data.repository

import com.gmail.maystruks08.data.storage.MenuInfo
import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.domain.entity.StartInquirerInfo
import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.domain.repository.ProductPortionRepository
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class ProductPortionRepositoryImpl @Inject constructor(private val menuInfo: MenuInfo) :
    ProductPortionRepository {


    override fun getStartInquirerInfo(): Single<StartInquirerInfo> {
        return Single.just(menuInfo.startInquirerInfo ?: StartInquirerInfo(
            "No name",
            1,
            1,
            0,
            Date(),
            TypeOfMeal.BREAKFAST,
            menuInfo.defaultProductPortionList.toMutableList()
        ))
    }

    override fun setStartInquirerInfo(startInquirerInfo: StartInquirerInfo) {
        menuInfo.startInquirerInfo = startInquirerInfo
    }

    override fun getAllProducts(): Single<List<Product>> {
        return Single.just(
            menuInfo.startInquirerInfo?.portionForOnePeople ?: menuInfo.defaultProductPortionList
        )
    }
}



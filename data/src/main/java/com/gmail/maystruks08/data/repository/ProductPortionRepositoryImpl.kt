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

    override fun saveStartInquirerInfo(startInquirerInfo: StartInquirerInfo) {
        menuInfo.startInquirerInfo = startInquirerInfo
    }

    override fun getStartInquirerInfo(): Single<StartInquirerInfo> {
        return Single.just(
            menuInfo.startInquirerInfo ?: StartInquirerInfo(
                name = "No name",
                peopleCount = 2,
                numberOfReceptions = 3,
                relaxDayCount = 0,
                dateOfStartMenu = Date(),
                timeOfStartMenu = TypeOfMeal.BREAKFAST,
                products = menuInfo.productPortionList.toMutableList(),
                productSets = menuInfo.soupSetList,
                foodMeals = menuInfo.defaultFoodMeals.toMutableMap()
            )
        )
    }

    override fun getAllProducts(): Single<List<Product>> {
        return Single.just(menuInfo.startInquirerInfo?.products ?: menuInfo.productPortionList)
    }
}



package com.gmail.maystruks08.data.repository

import com.gmail.maystruks08.data.storage.MenuInfo
import com.gmail.maystruks08.domain.entity.*
import com.gmail.maystruks08.domain.repository.CreateReceptionRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CreateReceptionRepositoryImpl @Inject constructor(private val menuInfo: MenuInfo) :
    CreateReceptionRepository {

    override fun getProductById(id: Int): Product? {
        return menuInfo.startInquirerInfo?.getProduct(id)
    }

    override fun getDefaultMealProducts(typeOfMeal: TypeOfMeal): Single<FoodMeal> {
        return Single.just(menuInfo.startInquirerInfo?.foodMeals?.get(typeOfMeal))
    }

    override fun getDefaultVariableMealProducts(typeOfMeal: TypeOfMeal): Single<List<Product>> {
        return Single.just(menuInfo.startInquirerInfo?.foodMeals?.get(typeOfMeal)?.loopProducts.orEmpty())
    }

    override fun getStartInquirerInfo(): Single<StartInquirerInfo> {
        return Single.just(menuInfo.startInquirerInfo!!)
    }

    override fun getSoupSets(): Single<List<ProductSet>> {
        return Single.just(menuInfo.soupSetList)
    }

    override fun saveFoodMeal(typeOfMeal: TypeOfMeal, foodMeal: FoodMeal): Completable {
        return Completable.fromAction {
            menuInfo.startInquirerInfo?.foodMeals?.set(typeOfMeal, foodMeal)
        }
    }

    override fun saveMenu(menu: Menu): Completable {
        //TODO implement with db
        return Completable.fromAction {
            menuInfo.startInquirerInfo = null
            menuInfo.menuList.add(menu)
        }
    }
}



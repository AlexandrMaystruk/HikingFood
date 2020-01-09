package com.gmail.maystruks08.data.repository

import com.gmail.maystruks08.data.storage.MenuInfo
import com.gmail.maystruks08.domain.entity.*
import com.gmail.maystruks08.domain.repository.CreateReceptionRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CreateReceptionRepositoryImpl @Inject constructor(private val menuInfo: MenuInfo) :
    CreateReceptionRepository {

    override fun getProductById(productId: Long, menuId: Long?): Product? {
       if(menuId != null){
           menuInfo.menuList.find { it.id == menuId }?.let {
             return  it.getProductById(productId)
           }
       }
        return menuInfo.startInquirerInfo?.getProduct(productId)
    }

    override fun getDefaultMealProducts(typeOfMeal: TypeOfMeal): Single<FoodMeal> {
        return Single.just(menuInfo.startInquirerInfo?.foodMeals?.get(typeOfMeal))
    }

    override fun getDefaultStaticProducts(): Single<List<Product>> {
        return Single.create { emitter ->
            emitter.onSuccess(mutableListOf<Product>().apply {
                menuInfo.startInquirerInfo?.foodMeals?.values?.forEach {
                    addAll(it.defProducts)
                }
            })
        }
    }

    override fun getDefaultLoopProducts(): Single<List<Product>> {
        return Single.create { emitter ->
            emitter.onSuccess(mutableListOf<Product>().apply {
                menuInfo.startInquirerInfo?.foodMeals?.values?.forEach {
                    addAll(it.loopProducts)
                }
            })
        }
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

    override fun removeLoopProduct(typeOfMeal: TypeOfMeal, productId: Long, parentId: Long?): Completable {
        return Completable.fromAction {
            val loopProducts = menuInfo.startInquirerInfo?.foodMeals?.get(typeOfMeal)?.loopProducts
            loopProducts?.removeAll {
                if(it is ProductSet && parentId == it.id){
                    it.removeProduct(productId)
                }
                it.id == productId
            }
        }
    }

    override fun removeStaticProduct(typeOfMeal: TypeOfMeal, productId: Long, parentId: Long?): Completable {
        return Completable.fromAction {
            val defProducts = menuInfo.startInquirerInfo?.foodMeals?.get(typeOfMeal)?.defProducts
            defProducts?.removeAll {
                if(it is ProductSet && parentId == it.id){
                    it.removeProduct(productId)
                }
                it.id == productId
            }
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



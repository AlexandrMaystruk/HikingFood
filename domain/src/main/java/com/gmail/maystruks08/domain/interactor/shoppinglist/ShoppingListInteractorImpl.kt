package com.gmail.maystruks08.domain.interactor.shoppinglist

import com.gmail.maystruks08.domain.entity.*
import com.gmail.maystruks08.domain.executor.ThreadExecutor
import com.gmail.maystruks08.domain.repository.ShoppingListRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ShoppingListInteractorImpl @Inject constructor(
    private val executor: ThreadExecutor,
    private val repository: ShoppingListRepository,
    private val shoppingListGenerator: ShoppingListGenerator
) : ShoppingListInteractor {

    override fun provideShoppingListGroupByProduct(menuId: Long): Single<List<ShoppingListItem>> {
        return repository.getDataForGeneratePurchaseList(menuId)
            .map {
                shoppingListGenerator.groupShoppingListByProduct(it)
            }
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    override fun providePurchaseListGroupByStoreDepartment(menuId: Long): Single<Map<StoreDepartment, List<ShoppingListItem>>> {
        return repository.getDataForGeneratePurchaseList(menuId)
            .map {
                shoppingListGenerator.groupShoppingListByStoreDepartment(it)
            }
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    override fun providePurchaseListGroupByProductAndStoreDepartment(menuId: Long): Single<Map<StoreDepartment, List<ShoppingListItem>>> {
        return repository.getDataForGeneratePurchaseList(menuId)
            .map {
                shoppingListGenerator.groupShoppingListByProductAndStoreDepartment(it)
            }
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    override fun exportDataToPDF(menuId: Long, menuName: String, type: GroupType): Completable {
        return repository.getDataForGeneratePurchaseList(menuId)
            .flatMapCompletable {
                val fileName = "${menuName}_${type.name}"
                when (type) {
                    GroupType.BY_PRODUCT -> {
                        val dataForExport = shoppingListGenerator.groupShoppingListByProduct(it)
                        return@flatMapCompletable repository.exportDataToPDF(fileName, dataForExport)
                    }
                    GroupType.BY_STORE_DEPARTMENT-> {
                        val dataForExport = shoppingListGenerator.groupShoppingListByStoreDepartment(it)
                        return@flatMapCompletable repository.exportDataToPDF(fileName, dataForExport)
                    }
                    GroupType.BY_PRODUCT_AND_STORE_DEPARTMENT-> {
                        val dataForExport = shoppingListGenerator.groupShoppingListByProductAndStoreDepartment(it)
                        return@flatMapCompletable repository.exportDataToPDF(fileName, dataForExport)
                    }
                }
            }
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }
}

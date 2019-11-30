package com.gmail.maystruks08.domain.interactor.purchaselist

import com.gmail.maystruks08.domain.entity.PurchaseList
import com.gmail.maystruks08.domain.executor.ThreadExecutor
import com.gmail.maystruks08.domain.repository.PurchaseListRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class PurchaseListInteractorImpl @Inject constructor(
    private val executor: ThreadExecutor,
    private val repository: PurchaseListRepository
) : PurchaseListInteractor {

    override fun getPurchaseList(menuId: Long): Single<PurchaseList> {
        return repository.getPurchaseList(menuId)
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    override fun exportDataToPDF(menuName: String, purchaseList: PurchaseList): Completable {
        return repository.exportPurchaseListDataToPDF(menuName, purchaseList)
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }
}

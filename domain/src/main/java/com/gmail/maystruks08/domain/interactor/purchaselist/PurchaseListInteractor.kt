package com.gmail.maystruks08.domain.interactor.purchaselist

import com.gmail.maystruks08.domain.entity.PurchaseList
import io.reactivex.Completable
import io.reactivex.Single

interface PurchaseListInteractor {

    fun exportDataToPDF(menuName: String, purchaseList: PurchaseList): Completable

    fun getPurchaseList(menuId: Long): Single<PurchaseList>

}

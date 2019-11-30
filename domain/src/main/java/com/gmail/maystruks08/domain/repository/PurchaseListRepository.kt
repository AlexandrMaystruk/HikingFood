package com.gmail.maystruks08.domain.repository

import com.gmail.maystruks08.domain.entity.PurchaseList
import io.reactivex.Completable
import io.reactivex.Single

interface PurchaseListRepository {

    fun getPurchaseList(menuId: Long): Single<PurchaseList>

    fun exportPurchaseListDataToPDF(menuName: String, purchaseList: PurchaseList): Completable

}

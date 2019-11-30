package com.gmail.maystruks08.data.repository

import com.gmail.maystruks08.data.PDFHelper
import com.gmail.maystruks08.data.storage.MenuInfo
import com.gmail.maystruks08.domain.entity.PurchaseList
import com.gmail.maystruks08.domain.repository.PurchaseListRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class PurchaseListRepositoryImpl @Inject constructor(private val menuInfo: MenuInfo,
                                                     private val pdfHelper: PDFHelper) :
    PurchaseListRepository {

    override fun getPurchaseList(menuId: Long): Single<PurchaseList> {
        return Single.fromCallable {
            return@fromCallable menuInfo.menuList.find { it.id == menuId }?.purchaseList ?: return@fromCallable
        }
    }

    override fun exportPurchaseListDataToPDF(menuName: String, purchaseList: PurchaseList): Completable {
        return Completable.fromAction { pdfHelper.exportPurchaseList(menuName, purchaseList) }
    }
}


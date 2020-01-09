package com.gmail.maystruks08.data.repository

import com.gmail.maystruks08.data.PDFHelper
import com.gmail.maystruks08.data.storage.MenuInfo
import com.gmail.maystruks08.domain.entity.Day
import com.gmail.maystruks08.domain.entity.ShoppingListItem
import com.gmail.maystruks08.domain.entity.StoreDepartment
import com.gmail.maystruks08.domain.repository.ShoppingListRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ShoppingListRepositoryImpl @Inject constructor(
    private val menuInfo: MenuInfo,
    private val pdfHelper: PDFHelper
) : ShoppingListRepository {

    override fun getDataForGeneratePurchaseList(menuId: Long): Single<List<Day>> {
        return Single.fromCallable {
            return@fromCallable menuInfo.menuList.find { it.id == menuId }?.days
                ?: return@fromCallable
        }
    }

    override fun exportDataToPDF(menuName: String, data: Map<StoreDepartment, List<ShoppingListItem>>): Completable {
        return Completable.fromAction { pdfHelper.exportPurchaseListGroupByStoreDepartment(menuName, data) }
    }

    override fun exportDataToPDF(menuName: String, data: List<ShoppingListItem>): Completable {
        return Completable.fromAction { pdfHelper.exportPurchaseListGroupByProduct(menuName, data) }
    }
}


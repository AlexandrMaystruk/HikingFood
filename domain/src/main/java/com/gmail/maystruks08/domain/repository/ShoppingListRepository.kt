package com.gmail.maystruks08.domain.repository

import com.gmail.maystruks08.domain.entity.Day
import com.gmail.maystruks08.domain.entity.ShoppingListItem
import com.gmail.maystruks08.domain.entity.StoreDepartment
import io.reactivex.Completable
import io.reactivex.Single

interface ShoppingListRepository {

    fun getDataForGeneratePurchaseList(menuId: Long): Single<List<Day>>

    fun exportDataGroupByStoreDepartmentToPDF(menuName: String, data: Map<StoreDepartment, List<ShoppingListItem>>): Completable

    fun exportDataGroupByProductToPDF(menuName: String, data: List<ShoppingListItem>): Completable

}

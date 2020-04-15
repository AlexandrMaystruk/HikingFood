package com.gmail.maystruks08.domain.interactor.shoppinglist

import com.gmail.maystruks08.domain.entity.GroupType
import com.gmail.maystruks08.domain.entity.ShoppingListItem
import com.gmail.maystruks08.domain.entity.StoreDepartment
import io.reactivex.Completable
import io.reactivex.Single

interface ShoppingListInteractor {

    fun exportDataToPDF(menuId: Long, menuName: String, type: GroupType): Completable

    fun provideShoppingListGroupByProduct(menuId: Long): Single<List<ShoppingListItem>>

    fun providePurchaseListGroupByStoreDepartment(menuId: Long): Single<Map<StoreDepartment, List<ShoppingListItem>>>

    fun providePurchaseListGroupByProductAndStoreDepartment(menuId: Long): Single<Map<StoreDepartment, List<ShoppingListItem>>>

}

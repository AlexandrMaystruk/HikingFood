package com.gmail.maystruks08.hikingfood.ui.adapter

import com.gmail.maystruks08.domain.entity.*
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.*

fun Product.toProductPortionView(): ProductPortionView =
    ProductPortionView(
        id = this.id,
        name = this.name,
        portionMin = this.portion.min,
        portionMax = this.portion.max,
        portionValue = this.portion.value
    )

fun List<Product>?.toProductPortionViewList(): List<ProductPortionView> = this?.map { it.toProductPortionView() } ?: listOf()

fun Product.toProductView(parentId: Long? = null): ProductView =
    ProductView(
        id = this.id,
        name = this.name,
        portionForOnePeople = this.portion.value,
        portionForAllPeople = this.portion.portionForAllPeople,
        parentId = parentId
    )


fun ProductSet.toSetProductView(): SetProductView =
    SetProductView(
        id = this.id,
        name = this.name,
        portionForOnePeople = this.portion.value,
        portionForAllPeople = this.portion.portionForAllPeople,
        isSelected = false
    )

fun List<Product>?.toProductViewList(): List<ProductView> {
    return this?.map {
        if (it is ProductSet) {
            it.toSetProductView()
        } else {
            it.toProductView()
        }
    } ?: listOf()

}

fun Menu.toMenuView(): MenuView =
    MenuView(
        this.id,
        this.name,
        this.peopleCount,
        this.numberOfReceptions,
        this.restDayCount,
        this.dateOfStartMenu,
        this.startFrom,
        this.totalWeight
    )

fun Day.toDayView(menuId: Long): DayView {
    return DayView(
        menuId = menuId,
        number = this.number,
        breakfastProducts = this.products[TypeOfMeal.BREAKFAST].toProductViewList().toMutableList(),
        lunchProducts = this.products[TypeOfMeal.LUNCH].toProductViewList().toMutableList(),
        dinnerProducts = this.products[TypeOfMeal.DINNER].toProductViewList().toMutableList(),
        breakfastTotalWeight = this.weightTotalsForOne[TypeOfMeal.BREAKFAST] ?: 0,
        lunchTotalWeight = this.weightTotalsForOne[TypeOfMeal.LUNCH] ?: 0,
        dinnerTotalWeight = this.weightTotalsForOne[TypeOfMeal.DINNER] ?: 0,
        breakfastTotalWeightForAll = this.weightTotalsForAll[TypeOfMeal.BREAKFAST] ?: 0,
        lunchTotalWeightForAll = this.weightTotalsForAll[TypeOfMeal.LUNCH] ?: 0,
        dinnerTotalWeightForAll = this.weightTotalsForAll[TypeOfMeal.DINNER] ?: 0,
        isRelaxDay = this is RelaxDay
    )
}

fun ShoppingListItem.toShoppingListItemView(): ShoppingListItemView =
    ShoppingListItemView(
        this.id,
        this.product.name,
        this.totalWeight,
        this.product.portion.unit.type
    )

fun StoreDepartment.toStoreDepartmentView(): StoreDepartmentView =
    StoreDepartmentView(
        this.ordinal.toLong(),
        this.type
    )

fun List<ShoppingListItem>?.toShoppingListItemViewList(): MutableList<ShoppingListItemView> {
    return this?.map { it.toShoppingListItemView() }?.toMutableList() ?: mutableListOf()
}
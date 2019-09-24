package com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers

import com.gmail.maystruks08.domain.entity.*
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DayView
import javax.inject.Inject

class DayMenuViewMapper @Inject constructor(private val productViewMapper: ProductViewMapper) {

    fun fromMenuDay(menuId: Long, day: Day): DayView =
        DayView(
            menuId = menuId,
            number = day.number,
            breakfastProducts = day.products[TypeOfMeal.BREAKFAST]?.map {
                productViewMapper.fromProduct(it)
            }.orEmpty().toMutableList(),
            lunchProducts = day.products[TypeOfMeal.LUNCH]?.map {
                productViewMapper.fromProduct(it)
            }.orEmpty().toMutableList(),
            dinnerProducts = day.products[TypeOfMeal.DINNER]?.map {
                productViewMapper.fromProduct(it)
            }.orEmpty().toMutableList(),
            breakfastTotalWeight = day.weightTotals[TypeOfMeal.BREAKFAST] ?: 0,
            lunchTotalWeight = day.weightTotals[TypeOfMeal.LUNCH] ?: 0,
            dinnerTotalWeight = day.weightTotals[TypeOfMeal.DINNER] ?: 0
        )
}
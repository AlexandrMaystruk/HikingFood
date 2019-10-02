package com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers

import com.gmail.maystruks08.domain.entity.*
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DayView
import javax.inject.Inject

class DayMenuViewMapper @Inject constructor(private val productViewMapper: ProductViewMapper) {

    fun fromMenuDay(menuId: Long, day: Day): DayView =
        DayView(
            menuId = menuId,
            number = day.number,
            breakfastProducts = productViewMapper.fromProducts(day.products[TypeOfMeal.BREAKFAST].orEmpty()).toMutableList(),
            lunchProducts = productViewMapper.fromProducts(day.products[TypeOfMeal.LUNCH].orEmpty()).toMutableList(),
            dinnerProducts = productViewMapper.fromProducts(day.products[TypeOfMeal.DINNER].orEmpty()).toMutableList(),
            breakfastTotalWeight = day.weightTotalsForOne[TypeOfMeal.BREAKFAST] ?: 0,
            lunchTotalWeight = day.weightTotalsForOne[TypeOfMeal.LUNCH] ?: 0,
            dinnerTotalWeight = day.weightTotalsForOne[TypeOfMeal.DINNER] ?: 0,
            breakfastTotalWeightForAll = day.weightTotalsForAll[TypeOfMeal.BREAKFAST] ?: 0,
            lunchTotalWeightForAll = day.weightTotalsForAll[TypeOfMeal.LUNCH] ?: 0,
            dinnerTotalWeightForAll = day.weightTotalsForAll[TypeOfMeal.DINNER] ?: 0
        )
}
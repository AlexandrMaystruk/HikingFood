package com.gmail.maystruks08.domain

import com.gmail.maystruks08.domain.entity.TypeOfMeal
import org.junit.Test

import org.junit.Assert.*

class TypeOfMealUnitTest {
    @Test
    fun isCorrectGetNextFoodMealType() {
        val currentType= TypeOfMeal.DINNER
        val nextFoodMealType = TypeOfMeal.getNextMeal(currentType)
        val expectedFoodMealType =  TypeOfMeal.BREAKFAST

        assertEquals(nextFoodMealType, expectedFoodMealType)
    }
}

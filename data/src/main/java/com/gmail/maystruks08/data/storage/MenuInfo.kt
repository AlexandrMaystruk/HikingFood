package com.gmail.maystruks08.data.storage

import com.gmail.maystruks08.domain.entity.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuInfo @Inject constructor() {

    val productPortionList = listOf(
        Product(0, "Гречка", Portion(85, 75, 100), listOf(Category.PORRIDGE)),
        Product(1, "Рис", Portion(85, 75, 100), listOf(Category.PORRIDGE)),
        Product(2, "Макароны", Portion(85, 75, 100), listOf(Category.PORRIDGE)),
        Product(3, "Крупы в суп", Portion(20, 20, 20), listOf(Category.PORRIDGE)),
        Product(4, "Соль", Portion(3, 2, 4), listOf(Category.ADDITIONALLY)),
        Product(5, "Сахар", Portion(35, 30, 40), listOf(Category.ADDITIONALLY)),
        Product(6, "Чай", Portion(3, 2, 4), listOf(Category.ADDITIONALLY)),
        Product(7, "Печенье", Portion(40, 35, 50), listOf(Category.SWEET)),
        Product(8, "Колбаса", Portion(50, 50, 50), listOf(Category.DRY_LUNCH)),
        Product(9, "Сыр", Portion(50, 50, 50), listOf(Category.PORRIDGE, Category.DRY_LUNCH, Category.SOUP)),
        Product(10, "Сало", Portion(50, 50, 50), listOf(Category.PORRIDGE)),
        Product(11, "Пашетет", Portion(60, 60, 60), listOf(Category.DRY_LUNCH)),
        Product(12, "Рыбные консервы", Portion(60, 60, 60), listOf(Category.DRY_LUNCH)),
        Product(13, "Мясо(в мокром виде)", Portion(20, 15, 25), listOf(Category.ADDITIONALLY)),
        Product(14, "Сухари/галеты", Portion(25, 20, 30), listOf(Category.ADDITIONALLY)),
        Product(15, "Картошка(в мокром виде)", Portion(20, 15, 25), listOf(Category.SOUP)),
        Product(16, "Капуста(в мокром виде)", Portion(20, 15, 25), listOf(Category.SOUP)),
        Product(17, "Морковка(в мокром виде)", Portion(10, 7, 13), listOf(Category.SOUP)),
        Product(18, "Зелень(в мокром виде)", Portion(2, 2, 4), listOf(Category.SOUP)),
        Product(18, "Лук", Portion(2, 2, 4), listOf(Category.SOUP)),
        Product(19, "Хлеб(в мокром виде)", Portion(200, 180, 220), listOf(Category.ADDITIONALLY)),
        Product(20, "Сладкое", Portion(40, 30, 50), listOf(Category.SWEET)),
        Product(21, "Привальные", Portion(75, 70, 80), listOf(Category.CAMP)),
        Product(22, "Грибы в суп", Portion(20, 20, 20), listOf(Category.SOUP)),
        Product(23, "Заправка борщ", Portion(20, 20, 20), listOf(Category.SOUP)),
        Product(24, "Заправка рассольник", Portion(20, 20, 20), listOf(Category.SOUP)),
        Product(25, "Сосиски в солянку", Portion(20, 20, 20), listOf(Category.SOUP)),
        Product(26, "Заправка харчо", Portion(20, 20, 20), listOf(Category.SOUP)),
        Product(27, "Гороховые брикеты", Portion(20, 20, 20), listOf(Category.SOUP)),
        Product(28, "Чечевица в суп", Portion(20, 20, 20), listOf(Category.SOUP)),
        Product(29, "Гречка в суп", Portion(20, 20, 20), listOf(Category.SOUP)),
        Product(30, "Сыр в суп", Portion(20, 20, 20), listOf(Category.SOUP))
    )

    val soupSetList = listOf(
        ProductSet.create(101, "Борщ", getDefaultSoupIngredients().apply {
            this.add(Product(23, "Заправка борщ", Portion(75, 70, 80), listOf(Category.SOUP)))
        }),
        ProductSet.create(102, "Рассольник", getDefaultSoupIngredients().apply {
            this.add(Product(24, "Заправка рассольник", Portion(75, 70, 80), listOf(Category.SOUP)))
        }),
        ProductSet.create(103, "Солянка", getDefaultSoupIngredients().apply {
            this.add(Product(25, "Сосиски в солянку", Portion(75, 70, 80), listOf(Category.SOUP)))
        }),
        ProductSet.create(104, "Суп харчо", getDefaultSoupIngredients().apply {
            this.add(Product(26, "Заправка харчо", Portion(75, 70, 80), listOf(Category.SOUP)))
        }),
        ProductSet.create(105, "Суп гороховый", getDefaultSoupIngredients().apply {
            this.add(Product(27, "Гороховые брикеты", Portion(75, 70, 80), listOf(Category.SOUP)))
        }),
        ProductSet.create(106, "Суп чечевичный", getDefaultSoupIngredients().apply {
            this.add(Product(28, "Чечевица в суп", Portion(75, 70, 80), listOf(Category.SOUP)))
        }),
        ProductSet.create(107, "Суп гречневый", getDefaultSoupIngredients().apply {
            this.add(Product(29, "Гречка в суп", Portion(20, 20, 20), listOf(Category.SOUP)))
        }),
        ProductSet.create(108, "Сырный суп", getDefaultSoupIngredients().apply {
            this.add(Product(30, "Сыр в суп", Portion(20, 20, 20), listOf(Category.SOUP)))
        }),
        ProductSet.create(109, "Суп грибной", getDefaultSoupIngredients().apply {
            this.add(Product(22, "Грибы в суп", Portion(20, 20, 20), listOf(Category.SOUP)))
            this.add(Product(29, "Гречка в суп", Portion(20, 20, 20), listOf(Category.SOUP)))
        })
    )

    private fun getDefaultSoupIngredients(): MutableList<Product> {
        return mutableListOf(
            Product(15, "Картошка(в мокром виде)", Portion(20, 15, 25), listOf(Category.SOUP)),
            Product(16, "Капуста(в мокром виде)", Portion(20, 15, 25), listOf(Category.SOUP)),
            Product(17, "Морковка(в мокром виде)", Portion(10, 7, 13), listOf(Category.SOUP)),
            Product(18, "Зелень(в мокром виде)", Portion(2, 2, 4), listOf(Category.SOUP)),
            Product(18, "Лук", Portion(2, 2, 4), listOf(Category.SOUP))
        )
    }

    val defaultFoodMeals = mapOf(
        TypeOfMeal.BREAKFAST to FoodMeal(
            mutableListOf(
                Product(4, "Соль", Portion(3, 2, 4), listOf(Category.ADDITIONALLY)),
                Product(5, "Сахар", Portion(35, 30, 40), listOf(Category.ADDITIONALLY)),
                Product(6, "Чай", Portion(3, 2, 4), listOf(Category.ADDITIONALLY)),
                Product(7, "Печенье", Portion(40, 35, 50), listOf(Category.SWEET)),
                Product(9, "Сыр", Portion(50, 50, 50), listOf(Category.PORRIDGE, Category.DRY_LUNCH, Category.SOUP)),
                Product(14, "Сухари/галеты", Portion(25, 20, 30), listOf(Category.ADDITIONALLY)),
                Product(19, "Хлеб(в мокром виде)", Portion(200, 180, 220), listOf(Category.ADDITIONALLY))
            ), mutableListOf(
                Product(0, "Гречка", Portion(85, 75, 100), listOf(Category.PORRIDGE)),
                Product(1, "Рис", Portion(85, 75, 100), listOf(Category.PORRIDGE)),
                Product(2, "Макароны", Portion(85, 75, 100), listOf(Category.PORRIDGE))
            )
        ),
        TypeOfMeal.LUNCH to FoodMeal(
            mutableListOf(
                Product(7, "Печенье", Portion(40, 35, 50), listOf(Category.SWEET)),
                Product(19, "Хлеб(в мокром виде)", Portion(200, 180, 220), listOf(Category.ADDITIONALLY)),
                Product(20, "Сладкое", Portion(40, 30, 50), listOf(Category.SWEET)),
                Product(21, "Привальные", Portion(75, 70, 80), listOf(Category.CAMP))
            ),
            mutableListOf(
                Product(8, "Колбаса", Portion(50, 50, 50), listOf(Category.DRY_LUNCH)),
                Product(10, "Сало", Portion(50, 50, 50), listOf(Category.DRY_LUNCH)),
                Product(11, "Пашетет", Portion(60, 60, 60), listOf(Category.DRY_LUNCH)),
                Product(12, "Рыбные консервы", Portion(60, 60, 60), listOf(Category.DRY_LUNCH)),
                Product(14, "Сухари/галеты", Portion(25, 20, 30), listOf(Category.ADDITIONALLY))
            )
        ),
        TypeOfMeal.DINNER to FoodMeal(
            mutableListOf(
                Product(4, "Соль", Portion(3, 2, 4), listOf(Category.ADDITIONALLY)),
                Product(6, "Чай", Portion(3, 2, 4), listOf(Category.ADDITIONALLY)),
                Product(7, "Печенье", Portion(40, 35, 50), listOf(Category.SWEET)),
                Product(14, "Сухари/галеты", Portion(25, 20, 30), listOf(Category.ADDITIONALLY)),
                Product(19, "Хлеб(в мокром виде)", Portion(200, 180, 220), listOf(Category.ADDITIONALLY))
            ), soupSetList.toMutableList()
        )
    )

    var startInquirerInfo: StartInquirerInfo? = null


    //TODO implement with db
    val menuList: MutableList<Menu> = mutableListOf()

}
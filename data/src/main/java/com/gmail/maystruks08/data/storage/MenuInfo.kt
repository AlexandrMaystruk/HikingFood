package com.gmail.maystruks08.data.storage

import com.gmail.maystruks08.domain.entity.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuInfo @Inject constructor() {

    val productPortionList = listOf(
        Product(0, "Гречка", Portion(85, 75, 100), StoreDepartment.CEREALS),
        Product(1, "Рис", Portion(85, 75, 100), StoreDepartment.CEREALS),
        Product(2, "Макароны", Portion(85, 75, 100), StoreDepartment.CEREALS),
        Product(3, "Крупы в суп", Portion(20, 20, 20), StoreDepartment.CEREALS),
        Product(4, "Соль", Portion(3, 2, 4), StoreDepartment.CEREALS),
        Product(5, "Сахар", Portion(12, 8, 15), StoreDepartment.CEREALS),
        Product(6, "Чай", Portion(3, 2, 4),  StoreDepartment.CEREALS),
        Product(7, "Печенье", Portion(40, 35, 50), StoreDepartment.SWEET),
        Product(8, "Колбаса", Portion(50, 50, 50), StoreDepartment.SAUSAGE),
        Product(9, "Сыр", Portion(50, 40, 60),  StoreDepartment.MILK),
        Product(10, "Сало", Portion(50, 40, 60), StoreDepartment.MEET),
        Product(11, "Пашетет", Portion(50, 40, 60), StoreDepartment.CONSERVE),
        Product(12, "Рыбные консервы", Portion(50, 40, 60), StoreDepartment.CONSERVE),
        Product(13, "Мясо(в мокром виде)", Portion(20, 15, 25), StoreDepartment.MEET),
        Product(14, "Сухари/галеты", Portion(25, 20, 30), StoreDepartment.BREAD),
        Product(15, "Картошка(в мокром виде)", Portion(20, 15, 25), StoreDepartment.VEGETABLES),
        Product(16, "Капуста(в мокром виде)", Portion(20, 15, 25),  StoreDepartment.VEGETABLES),
        Product(17, "Морковка(в мокром виде)", Portion(10, 7, 13), StoreDepartment.VEGETABLES),
        Product(18, "Зелень(в мокром виде)", Portion(2, 2, 4), StoreDepartment.VEGETABLES),
        Product(18, "Лук", Portion(2, 2, 4),  StoreDepartment.VEGETABLES),
        Product(19, "Хлеб(в мокром виде)", Portion(170, 150, 220),  StoreDepartment.BREAD),
        Product(20, "Сладкое", Portion(40, 30, 50), StoreDepartment.SWEET),
        Product(21, "Привальные", Portion(75, 70, 80),  StoreDepartment.SWEET),
        Product(22, "Грибы в суп", Portion(15, 10, 25), StoreDepartment.VEGETABLES),
        Product(23, "Заправка борщ", Portion(15, 10, 25), StoreDepartment.CONSERVE),
        Product(24, "Заправка рассольник", Portion(15, 10, 25), StoreDepartment.CONSERVE),
        Product(25, "Сосиски в солянку", Portion(15, 10, 25), StoreDepartment.SAUSAGE),
        Product(26, "Заправка харчо", Portion(15, 10, 25), StoreDepartment.CONSERVE),
        Product(27, "Гороховые брикеты", Portion(15, 10, 25), StoreDepartment.CONSERVE),
        Product(28, "Чечевица в суп", Portion(15, 10, 25), StoreDepartment.CEREALS),
        Product(29, "Гречка в суп", Portion(15, 10, 25), StoreDepartment.CEREALS),
        Product(30, "Сыр в суп", Portion(15, 10, 25), StoreDepartment.MILK)
    )

    val soupSetList = listOf(
        ProductSet.create(101, "Борщ", getDefaultSoupIngredients().apply {
            this.add(Product(23, "Заправка борщ", Portion(15, 10, 30),  StoreDepartment.CONSERVE))
        }),
        ProductSet.create(102, "Рассольник", getDefaultSoupIngredients().apply {
            this.add(Product(24, "Заправка рассольник", Portion(15, 10, 30), StoreDepartment.CONSERVE))
        }),
        ProductSet.create(103, "Солянка", getDefaultSoupIngredients().apply {
            this.add(Product(25, "Сосиски в солянку", Portion(15, 10, 30), StoreDepartment.CEREALS))
        }),
        ProductSet.create(104, "Суп харчо", getDefaultSoupIngredients().apply {
            this.add(Product(26, "Заправка харчо", Portion(15, 10, 30), StoreDepartment.CONSERVE))
        }),
        ProductSet.create(105, "Суп гороховый", getDefaultSoupIngredients().apply {
            this.add(Product(27, "Гороховые брикеты", Portion(15, 10, 30), StoreDepartment.CONSERVE))
        }),
        ProductSet.create(106, "Суп чечевичный", getDefaultSoupIngredients().apply {
            this.add(Product(28, "Чечевица в суп", Portion(15, 10, 30), StoreDepartment.CEREALS))
        }),
        ProductSet.create(107, "Суп гречневый", getDefaultSoupIngredients().apply {
            this.add(Product(29, "Гречка в суп", Portion(20, 20, 20), StoreDepartment.CEREALS))
        }),
        ProductSet.create(108, "Сырный суп", getDefaultSoupIngredients().apply {
            this.add(Product(30, "Сыр в суп", Portion(20, 20, 20),  StoreDepartment.MILK))
        }),
        ProductSet.create(109, "Суп грибной", getDefaultSoupIngredients().apply {
            this.add(Product(22, "Грибы в суп", Portion(20, 20, 20), StoreDepartment.VEGETABLES))
        })
    )

    private fun getDefaultSoupIngredients(): MutableList<Product> {
        return mutableListOf(
            Product(15, "Картошка(в мокром виде)", Portion(20, 15, 25), StoreDepartment.VEGETABLES),
            Product(16, "Капуста(в мокром виде)", Portion(20, 15, 25),  StoreDepartment.VEGETABLES),
            Product(17, "Морковка(в мокром виде)", Portion(10, 7, 13), StoreDepartment.VEGETABLES),
            Product(18, "Зелень(в мокром виде)", Portion(2, 2, 4), StoreDepartment.VEGETABLES),
            Product(18, "Лук", Portion(2, 2, 4),  StoreDepartment.VEGETABLES)
        )
    }

    val defaultFoodMeals = mapOf(
        TypeOfMeal.BREAKFAST to FoodMeal(
            mutableListOf(
                Product(4, "Соль", Portion(3, 2, 4), StoreDepartment.CEREALS),
                Product(5, "Сахар", Portion(35, 30, 40), StoreDepartment.CEREALS),
                Product(6, "Чай", Portion(3, 2, 4),  StoreDepartment.CEREALS),
                Product(7, "Печенье", Portion(40, 35, 50),  StoreDepartment.CEREALS),
                Product(9, "Сыр", Portion(50, 50, 50),  StoreDepartment.MILK),
                Product(14, "Сухари/галеты", Portion(25, 20, 30), StoreDepartment.BREAD),
                Product(19, "Хлеб(в мокром виде)", Portion(170, 150, 220), StoreDepartment.CEREALS)
            ), mutableListOf(
                Product(0, "Гречка", Portion(85, 75, 100),  StoreDepartment.CEREALS),
                Product(1, "Рис", Portion(85, 75, 100), StoreDepartment.CEREALS),
                Product(2, "Макароны", Portion(85, 75, 100),  StoreDepartment.CEREALS)
            )
        ),
        TypeOfMeal.LUNCH to FoodMeal(
            mutableListOf(
                Product(7, "Печенье", Portion(40, 35, 50),  StoreDepartment.SWEET),
                Product(19, "Хлеб(в мокром виде)", Portion(200, 180, 220), StoreDepartment.VEGETABLES),
                Product(20, "Сладкое", Portion(40, 30, 50),  StoreDepartment.SWEET),
                Product(21, "Привальные", Portion(75, 70, 80), StoreDepartment.SWEET)
            ),
            mutableListOf(
                Product(8, "Колбаса", Portion(50, 40, 60), StoreDepartment.SAUSAGE),
                Product(10, "Сало", Portion(50, 40, 60), StoreDepartment.MEET),
                Product(11, "Пашетет", Portion(50, 40, 60), StoreDepartment.CONSERVE),
                Product(12, "Рыбные консервы", Portion(50, 40, 60),  StoreDepartment.CONSERVE),
                Product(14, "Сухари/галеты", Portion(25, 20, 30), StoreDepartment.CEREALS)
            )
        ),
        TypeOfMeal.DINNER to FoodMeal(
            mutableListOf(
                Product(4, "Соль", Portion(3, 2, 4), StoreDepartment.CEREALS),
                Product(6, "Чай", Portion(3, 2, 4),  StoreDepartment.CEREALS),
                Product(7, "Печенье", Portion(40, 35, 50),  StoreDepartment.CEREALS),
                Product(14, "Сухари/галеты", Portion(25, 20, 30), StoreDepartment.BREAD),
                Product(19, "Хлеб(в мокром виде)", Portion(170, 150, 220), StoreDepartment.BREAD)
            ), soupSetList.toMutableList()
        )
    )

    var startInquirerInfo: StartInquirerInfo? = null


    //TODO implement with db
    val menuList: MutableList<Menu> = mutableListOf()

}
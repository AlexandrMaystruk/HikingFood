package com.gmail.maystruks08.data.storage

import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.domain.entity.StartInquirerInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuInfo @Inject constructor() {

    val defaultProductPortionList = mutableListOf<Product>().apply {
        add(Product(0, "Гречка", 85))
        add(Product(1, "Рис", 85))
        add(Product(2, "Макароны", 85))
        add(Product(3, "Крупы в суп", 20))
        add(Product(4, "Соль", 3))
        add(Product(5, "Сахар", 35))
        add(Product(6, "Чай", 3))
        add(Product(7, "Печенье", 40))
        add(Product(8, "Колбаса", 50))
        add(Product(9, "Сыр", 50))
        add(Product(10, "Сало", 50))
        add(Product(11, "Пашетет", 60))
        add(Product(12, "Рыбные консервы", 60))
        add(Product(13, "Мясо(в мокром виде)", 60))
        add(Product(14, "Сухари/галеты", 60))
        add(Product(15, "Картошка(в мокром виде)", 20))
        add(Product(16, "Капуста(в мокром виде)", 20))
        add(Product(17, "Морковка(в мокром виде)", 10))
        add(Product(18, "Зелень(в мокром виде)", 2))
        add(Product(19, "Хлеб(в мокром виде)", 200))
        add(Product(20, "Сладкое на обед", 75))
        add(Product(21, "Привальные", 40))
    }.toList()

    var startInquirerInfo: StartInquirerInfo? = null

}
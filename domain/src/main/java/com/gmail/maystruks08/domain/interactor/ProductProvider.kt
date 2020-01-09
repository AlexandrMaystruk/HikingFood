package com.gmail.maystruks08.domain.interactor

import com.gmail.maystruks08.domain.entity.Product

interface ProductProvider {

    fun getProductById(productId: Long, menuId: Long? = null): Product?

}
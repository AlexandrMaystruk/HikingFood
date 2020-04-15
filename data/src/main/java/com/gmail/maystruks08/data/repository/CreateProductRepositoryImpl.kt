package com.gmail.maystruks08.data.repository

import com.gmail.maystruks08.data.storage.MenuInfo
import com.gmail.maystruks08.domain.repository.CreateProductRepository
import javax.inject.Inject

class CreateProductRepositoryImpl @Inject constructor(private val menuInfo: MenuInfo) :
    CreateProductRepository {


}



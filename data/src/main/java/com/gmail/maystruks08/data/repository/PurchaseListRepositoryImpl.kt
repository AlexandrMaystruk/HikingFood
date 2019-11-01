package com.gmail.maystruks08.data.repository

import com.gmail.maystruks08.data.storage.MenuInfo
import com.gmail.maystruks08.domain.repository.PurchaseListRepository
import javax.inject.Inject

class PurchaseListRepositoryImpl @Inject constructor(private val menuInfo: MenuInfo) : PurchaseListRepository

package com.gmail.maystruks08.domain.repository

import com.gmail.maystruks08.domain.entity.Menu
import io.reactivex.Completable
import io.reactivex.Single

interface MenuRepository {

    fun getMenu(menuId: Long): Single<Menu>

    fun exportMenuToPDF(menu: Menu): Completable
}
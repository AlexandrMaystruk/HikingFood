package com.gmail.maystruks08.domain.repository

import com.gmail.maystruks08.domain.entity.Menu
import io.reactivex.Completable
import io.reactivex.Single

interface AllMenuRepository {

    fun getAllMenuList(): Single<List<Menu>>

    fun removeMenu(menu: Menu): Completable
}
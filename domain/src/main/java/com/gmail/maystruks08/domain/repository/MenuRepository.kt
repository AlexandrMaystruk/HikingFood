package com.gmail.maystruks08.domain.repository

import com.gmail.maystruks08.domain.entity.Day
import io.reactivex.Single

interface MenuRepository {

    fun getMenuDays(menuId: Long): Single<List<Day>>
}
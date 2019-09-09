package com.gmail.maystruks08.domain.interactor.menu

import com.gmail.maystruks08.domain.entity.Day
import io.reactivex.Single

interface MenuInteractor {

    fun getMenuDays(menuId: Long): Single<List<Day>>
}
package com.gmail.maystruks08.domain.interactor.menu

import com.gmail.maystruks08.domain.entity.Menu
import io.reactivex.Single

interface MenuInteractor {

    fun getMenu(menuId: Long): Single<Menu>
}
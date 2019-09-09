package com.gmail.maystruks08.data.repository

import com.gmail.maystruks08.data.storage.MenuInfo
import com.gmail.maystruks08.domain.entity.Day
import com.gmail.maystruks08.domain.exceptions.MenuNotFoundException
import com.gmail.maystruks08.domain.repository.MenuRepository
import io.reactivex.Single
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(private val menuInfo: MenuInfo) : MenuRepository {

    override fun getMenuDays(menuId: Long): Single<List<Day>> {
        return Single.create {
            val menu = menuInfo.menuList.find { it.id == menuId }
            if (menu != null) {
                it.onSuccess(menu.menu)
            } else {
                it.onError(MenuNotFoundException())
            }
        }
    }
}



package com.gmail.maystruks08.data.repository

import com.gmail.maystruks08.data.storage.MenuInfo
import com.gmail.maystruks08.domain.entity.Menu
import com.gmail.maystruks08.domain.repository.AllMenuRepository
import io.reactivex.Completable
import io.reactivex.Single
import maystruks08.gmail.com.data.room.dao.MenuDAO
import javax.inject.Inject

class AllMenuRepositoryImpl @Inject constructor(val menuInfo: MenuInfo, val menuDAO: MenuDAO) : AllMenuRepository {

    override fun getAllMenuList(): Single<List<Menu>> {
        return Single.just(menuInfo.menuList)
    }

    override fun removeMenu(menuId: Long): Completable {
        return Completable.fromAction {
            menuInfo.menuList.removeAll { it.id == menuId }
        }
    }
}



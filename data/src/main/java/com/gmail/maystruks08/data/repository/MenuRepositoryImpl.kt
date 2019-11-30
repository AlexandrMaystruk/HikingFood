package com.gmail.maystruks08.data.repository

import com.gmail.maystruks08.data.PDFHelper
import com.gmail.maystruks08.data.storage.MenuInfo
import com.gmail.maystruks08.domain.entity.Menu
import com.gmail.maystruks08.domain.exceptions.MenuNotFoundException
import com.gmail.maystruks08.domain.repository.MenuRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val menuInfo: MenuInfo,
    private val pdfHelper: PDFHelper
) : MenuRepository {

    override fun getMenu(menuId: Long): Single<Menu> {
        return Single.create {
            val menu = menuInfo.menuList.find { it.id == menuId }
            if (menu != null) {
                it.onSuccess(menu)
            } else {
                it.onError(MenuNotFoundException())
            }
        }
    }

    override fun exportMenuToPDF(menu: Menu): Completable {
        return Completable.fromAction { pdfHelper.exportMenu(menu) }
    }
}



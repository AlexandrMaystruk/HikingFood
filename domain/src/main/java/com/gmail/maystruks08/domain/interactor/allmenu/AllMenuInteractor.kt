package com.gmail.maystruks08.domain.interactor.allmenu

import com.gmail.maystruks08.domain.entity.Menu
import io.reactivex.Completable
import io.reactivex.Single

interface AllMenuInteractor {

    fun provideAllMenuList(): Single<List<Menu>>

    fun removeMenu(menuId: Long): Completable

}
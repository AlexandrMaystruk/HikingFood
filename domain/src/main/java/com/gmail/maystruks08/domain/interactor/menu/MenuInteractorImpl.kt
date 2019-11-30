package com.gmail.maystruks08.domain.interactor.menu

import com.gmail.maystruks08.domain.entity.Menu
import com.gmail.maystruks08.domain.executor.ThreadExecutor
import com.gmail.maystruks08.domain.repository.MenuRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MenuInteractorImpl @Inject constructor(
    private val executor: ThreadExecutor,
    private val repository: MenuRepository
) : MenuInteractor {

    override fun getMenu(menuId: Long): Single<Menu> {
        return repository.getMenu(menuId)
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    override fun exportMenuDataToPDF(menu: Menu): Completable {
        return repository.exportMenuToPDF(menu)
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }
}

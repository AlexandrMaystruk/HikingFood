package com.gmail.maystruks08.domain.interactor.allmenu

import com.gmail.maystruks08.domain.entity.Menu
import com.gmail.maystruks08.domain.executor.ThreadExecutor
import com.gmail.maystruks08.domain.repository.AllMenuRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AllMenuInteractorImpl @Inject constructor(
    private val executor: ThreadExecutor,
    private val repository: AllMenuRepository
) : AllMenuInteractor {

    override fun provideAllMenuList(): Single<List<Menu>> {
        return repository.getAllMenuList()
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    override fun removeMenu(menuId: Long): Completable {
        return repository.removeMenu(menuId)
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }
}
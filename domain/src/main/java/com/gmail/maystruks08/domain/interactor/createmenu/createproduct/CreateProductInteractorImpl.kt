package com.gmail.maystruks08.domain.interactor.createmenu.createproduct

import com.gmail.maystruks08.domain.executor.ThreadExecutor
import com.gmail.maystruks08.domain.repository.CreateProductRepository
import javax.inject.Inject

class CreateProductInteractorImpl @Inject constructor(
    private val executor: ThreadExecutor,
    val repository: CreateProductRepository
) : CreateProductInteractor {

}
package com.gmail.maystruks08.domain.interactor.dose

import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.domain.executor.ThreadExecutor
import com.gmail.maystruks08.domain.repository.ProductPortionRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ProductPortionInteractorImpl @Inject constructor(
    private val executor: ThreadExecutor,
    private val repository: ProductPortionRepository
) : ProductPortionInteractor {

    override fun getProducts(): Single<List<Product>> {
        return repository.getAllProducts()
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    override fun onPortionValueChanged(newValue: Int, productId: Long): Completable {
        return repository.getStartInquirerInfo().flatMapCompletable { startInquirerInfo ->
            Completable.fromAction {
                startInquirerInfo.changePortionValue(newValue, productId)
                repository.saveStartInquirerInfo(startInquirerInfo)
            }
                .subscribeOn(executor.mainExecutor)
                .observeOn(executor.postExecutor)
        }
    }
}
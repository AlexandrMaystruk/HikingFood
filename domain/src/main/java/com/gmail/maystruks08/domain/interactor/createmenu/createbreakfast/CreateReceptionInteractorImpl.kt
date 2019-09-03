package com.gmail.maystruks08.domain.interactor.createmenu.createbreakfast

import com.gmail.maystruks08.domain.entity.Ingredient
import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.domain.entity.Unit
import com.gmail.maystruks08.domain.executor.ThreadExecutor
import com.gmail.maystruks08.domain.repository.CreateReceptionRepository
import io.reactivex.Single
import javax.inject.Inject

class CreateReceptionInteractorImpl @Inject constructor(
    private val executor: ThreadExecutor,
    private val repository: CreateReceptionRepository
) : CreateReceptionInteractor {

    override fun getDefaultIngredients(): Single<List<Ingredient>> {
        return repository.getStartInquirerInfo().flatMap { info ->
            Single.just(
                info.portionForOnePeople.map {
                    Ingredient(
                        Product(
                            it.id,
                            it.name,
                            it.portionForOnePeople
                        ),
                        it.portionForOnePeople * info.peopleCount,
                        Unit.GRAM
                    )
                }
            )
        }
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }
}
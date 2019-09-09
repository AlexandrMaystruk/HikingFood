package com.gmail.maystruks08.domain.interactor.createmenu.createbreakfast

import com.gmail.maystruks08.domain.entity.*
import com.gmail.maystruks08.domain.exceptions.FieldNotFillException
import com.gmail.maystruks08.domain.executor.ThreadExecutor
import com.gmail.maystruks08.domain.repository.CreateReceptionRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CreateReceptionInteractorImpl @Inject constructor(
    private val executor: ThreadExecutor,
    private val repository: CreateReceptionRepository
) : CreateReceptionInteractor {

    override fun getProductById(productId: Int): Product? {
        return repository.getProductById(productId)
    }

    override fun getAllDefaultProducts(typeOfMeal: TypeOfMeal): Single<FoodMeal> {
        return repository.getDefaultMealProducts(typeOfMeal)
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    override fun getDefaultVariableProducts(typeOfMeal: TypeOfMeal): Single<List<Product>> {
        return repository.getDefaultVariableMealProducts(typeOfMeal).flatMap { list ->
            return@flatMap Single.create<List<Product>> { emitter ->
                val startInquirerInfo = repository.getStartInquirerInfo().blockingGet()
                return@create emitter.onSuccess(
                    list.map {
                        it.apply { this.calculatePortionForAllPeople(startInquirerInfo.peopleCount) }
                    })
            }
        }
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    override fun getSoupSets(typeOfMeal: TypeOfMeal): Single<List<SoupSet>> {
        return repository.getSoupSets()
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    override fun onFoodReceptionCreationComplete(
        mealType: TypeOfMeal,
        defaultProductList: List<Product>,
        variableProductList: List<Product>
    ): Completable {
        return if (defaultProductList.isNotEmpty() && variableProductList.isNotEmpty()) {
            repository.getStartInquirerInfo().flatMapCompletable {
                val foodMeal =
                    FoodMeal(
                        defaultProductList.toMutableList(),
                        variableProductList.toMutableList())
                        .apply { this.calculatePortionForAllPeople(it.peopleCount) }
                repository.saveFoodMeal(mealType, foodMeal)
            }
        } else {
            Completable.error(FieldNotFillException())
        }.subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    override fun onFinishCreateReception(): Completable {
        return repository.getStartInquirerInfo().flatMapCompletable {

           repository.saveMenu( Menu.create(it))

        }

    }
}
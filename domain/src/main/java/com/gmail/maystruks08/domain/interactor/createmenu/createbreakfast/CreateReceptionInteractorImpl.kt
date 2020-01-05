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

    override fun getInitConfig(): Single<CreateReceptionInteractor.Config> {
        return repository.getStartInquirerInfo().map {
            CreateReceptionInteractor.Config(it.numberOfReceptions, it.timeOfStartMenu)
        }
    }

    override fun getProductById(productId: Long, menuId: Long?): Product? {
        return repository.getProductById(productId, menuId)
    }

    override fun getAllDefaultProducts(typeOfMeal: TypeOfMeal): Single<FoodMeal> {
        return repository.getDefaultMealProducts(typeOfMeal)
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    override fun getDefaultStaticProducts(): Single<List<Product>> {
        return repository.getDefaultStaticProducts().flatMap { list ->
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

    override fun getDefaultLoopProducts(): Single<List<Product>> {
        return repository.getDefaultLoopProducts().flatMap { list ->
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

    override fun getSoupSets(typeOfMeal: TypeOfMeal): Single<List<ProductSet>> {
        return repository.getSoupSets()
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    override fun onStaticProductsAdded(typeOfMeal: TypeOfMeal, productIds: List<Long>): Completable {
        return Completable.fromAction {
            val startInfo = repository.getStartInquirerInfo().blockingGet()
            val products = productIds.mapNotNull { id ->
                repository.getProductById(id)?.apply { this.calculatePortionForAllPeople(startInfo.peopleCount) }
            }
            startInfo.foodMeals[typeOfMeal]?.defProducts?.addAll(products)
        }
    }

    override fun onLoopProductsAdded(typeOfMeal: TypeOfMeal, productIds: List<Long>): Completable {
        return Completable.fromAction {
            val startInfo = repository.getStartInquirerInfo().blockingGet()
            val products = productIds.mapNotNull { id ->
                repository.getProductById(id)?.apply { this.calculatePortionForAllPeople(startInfo.peopleCount) }
            }
            startInfo.foodMeals[typeOfMeal]?.loopProducts?.addAll(products)
        }
    }

    override fun removeLoopProduct(typeOfMeal: TypeOfMeal, productId: Long, parentId: Long? ): Completable {
        return repository.removeLoopProduct(typeOfMeal, productId, parentId)
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)

    }

    override fun removeStaticProduct(typeOfMeal: TypeOfMeal, productId: Long,  parentId: Long?): Completable {
        return repository.removeStaticProduct(typeOfMeal, productId, parentId)
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
                val foodMeal = FoodMeal(
                    defaultProductList.toMutableList(),
                    variableProductList.toMutableList()
                ).apply { this.calculatePortionForAllPeople(it.peopleCount) }
                repository.saveFoodMeal(mealType, foodMeal)
            }
        } else {
            Completable.error(FieldNotFillException())
        }
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    override fun onFinishCreateReception(): Completable {
        return repository.getStartInquirerInfo().flatMapCompletable {
            repository.saveMenu(Menu.generateMenu(it))
        }
    }
}
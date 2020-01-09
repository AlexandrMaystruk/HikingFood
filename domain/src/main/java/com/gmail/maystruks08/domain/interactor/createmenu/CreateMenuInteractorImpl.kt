package com.gmail.maystruks08.domain.interactor.createmenu

import com.gmail.maystruks08.domain.entity.StartInquirerInfo
import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.domain.exceptions.HasNotStartInquirerInfoException
import com.gmail.maystruks08.domain.executor.ThreadExecutor
import com.gmail.maystruks08.domain.repository.CreateMenuRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class CreateMenuInteractorImpl @Inject constructor(
    private val executor: ThreadExecutor,
    val repository: CreateMenuRepository) :
    CreateMenuInteractor {

    override fun getInitInfo(): Single<CreateMenuInteractor.Config> {
        return repository.getStartInquirerData().map {
            CreateMenuInteractor.Config(
                it.name,
                it.peopleCount,
                it.numberOfReceptions,
                it.relaxDayCount,
                it.dateOfStartMenu,
                it.timeOfStartMenu
            )
        }.onErrorResumeNext {
            Single.create<CreateMenuInteractor.Config> { emitter ->
                if (it is HasNotStartInquirerInfoException) {
                    emitter.onSuccess(
                        CreateMenuInteractor.Config(
                            "",
                            4,
                            3,
                            0,
                            Date(),
                            TypeOfMeal.BREAKFAST
                        )
                    )
                } else emitter.onError(it)
            }
        }
    }

    override fun saveStartInquirerData(initialConfig: CreateMenuInteractor.Config): Completable {
        return repository.getStartInquirerData()
            .flatMapCompletable {
                val updatedStartInquirerData = it.apply {
                    this.name = initialConfig.name
                    this.numberOfReceptions = initialConfig.receptionCount
                    this.relaxDayCount = initialConfig.relaxDayCount
                    this.peopleCount = initialConfig.peopleCount
                    this.timeOfStartMenu = initialConfig.timeOfStartMenu
                    this.dateOfStartMenu = initialConfig.dateOfStartMenu
                }
                updatedStartInquirerData.updatePortionValue()
                repository.saveStartInquirerData(updatedStartInquirerData)
            }
            .onErrorResumeNext { throwable ->
                if (throwable is HasNotStartInquirerInfoException) {
                    val defaultProducts = repository.getDefaultProductPortions().blockingGet()
                    val defaultFoodMeals = repository.getDefaultFoodMeals().blockingGet()
                    val defaultSoupSets = repository.getDefaultSoupSet().blockingGet()
                    repository.saveStartInquirerData(
                        StartInquirerInfo(
                            name = initialConfig.name,
                            numberOfReceptions = initialConfig.receptionCount,
                            relaxDayCount = initialConfig.relaxDayCount,
                            peopleCount = initialConfig.peopleCount,
                            dateOfStartMenu = initialConfig.dateOfStartMenu,
                            timeOfStartMenu = initialConfig.timeOfStartMenu,
                            products = defaultProducts.toMutableList(),
                            productSets = defaultSoupSets.toMutableList(),
                            foodMeals = defaultFoodMeals.toMutableMap()
                        ).apply {
                            this.updatePortionValue()
                        }
                    )
                } else {
                    Completable.error(throwable)
                }
            }
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    override fun clearStartInquirerData(): Completable {
        return repository.clearStartInquirerData()
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)

    }
}
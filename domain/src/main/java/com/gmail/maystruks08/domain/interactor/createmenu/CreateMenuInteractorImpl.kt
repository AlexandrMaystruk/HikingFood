package com.gmail.maystruks08.domain.interactor.createmenu

import com.gmail.maystruks08.domain.entity.StartInquirerInfo
import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.domain.exceptions.HasNotStartInquirerInfoException
import com.gmail.maystruks08.domain.executor.ThreadExecutor
import com.gmail.maystruks08.domain.repository.CreateMenuRepository
import io.reactivex.Completable
import java.util.*
import javax.inject.Inject

class CreateMenuInteractorImpl @Inject constructor(private val executor: ThreadExecutor, val repository: CreateMenuRepository) :
    CreateMenuInteractor {

    override fun saveStartInquirerData(
        name: String, dayCount: Int, relaxDayCount: Int,
        peopleCount: Int, timeOfStartMenu: Int, dateOfStartMenu: Date
    ): Completable {
        return repository.getStartInquirerData()
            .flatMapCompletable {
                val updatedStartInquirerData = it.apply {
                    this.name = name
                    this.dayCount = dayCount
                    this.relaxDayCount = relaxDayCount
                    this.peopleCount = peopleCount
                    this.timeOfStartMenu = TypeOfMeal.fromValue(timeOfStartMenu)
                }
                repository.saveStartInquirerData(updatedStartInquirerData)
            }
            .onErrorResumeNext { throwable ->
                if (throwable is HasNotStartInquirerInfoException) {
                    repository.getDefaultIngredientPortions()
                        .flatMapCompletable {
                            repository.saveStartInquirerData(
                                StartInquirerInfo(
                                    name,
                                    dayCount,
                                    relaxDayCount,
                                    peopleCount,
                                    dateOfStartMenu,
                                    TypeOfMeal.fromValue(timeOfStartMenu),
                                    it.toMutableList()
                                )
                            )
                        }
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
package com.gmail.maystruks08.domain.interactor.createmenu

import com.gmail.maystruks08.domain.entity.TypeOfMeal
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

interface CreateMenuInteractor {

    fun getInitInfo(): Single<Config>

    fun saveStartInquirerData(initialConfig: Config): Completable

    fun clearStartInquirerData(): Completable

    class Config(
        var name: String,
        var peopleCount: Int,
        var receptionCount: Int,
        var relaxDayCount: Int,
        var dateOfStartMenu: Date,
        var timeOfStartMenu: TypeOfMeal
    )
}
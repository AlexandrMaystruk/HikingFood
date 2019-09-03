package com.gmail.maystruks08.domain.interactor.createmenu

import io.reactivex.Completable
import java.util.*

interface CreateMenuInteractor {

    fun saveStartInquirerData(name: String, dayCount: Int, relaxDayCount: Int, peopleCount: Int,
                              timeOfStartMenu: Int, dateOfStartMenu: Date): Completable

    fun clearStartInquirerData(): Completable
}
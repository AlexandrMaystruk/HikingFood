package com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu

import android.util.Log
import com.gmail.maystruks08.domain.CalendarHelper
import com.gmail.maystruks08.domain.interactor.createmenu.CreateMenuInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import java.util.*
import javax.inject.Inject

class CreateMenuPresenter @Inject constructor(
    private val interactor: CreateMenuInteractor,
    private val calendarHelper: CalendarHelper
) : CreateMenuContract.Presenter, BasePresenter<CreateMenuContract.View>() {

    private var name: String = ""
    private var dayCount: Int = 1
    private var relaxDayCount: Int = 0
    private var peopleCount: Int = 1
    private var timeOfStartMenu: Int = 0
    private var dateOfStartMenu: Date = Date()

    override fun onNameMenuChanged(menuName: String) {
        this.name = menuName
    }

    override fun onRelaxDayCountChanged(relaxDayCount: Int) {
        this.relaxDayCount = relaxDayCount
    }

    override fun onDayCountChanged(dayCount: Int) {
        this.dayCount = dayCount
    }

    override fun onCountOfPeopleChanged(countOfPeople: Int) {
        this.peopleCount = countOfPeople
    }

    override fun onTimeMenuStartChanged(time: Int) {
        this.timeOfStartMenu = time
    }

    override fun onDateMenuStartChanged(date: Date) {
        this.dateOfStartMenu = date
        view?.showDateOfStartReception(calendarHelper.format(date, CalendarHelper.DATE_FORMAT))
    }

    override fun onIngredientPortionClicked() {
        compositeDisposable.add(
            interactor.saveStartInquirerData(name, dayCount, relaxDayCount, peopleCount, timeOfStartMenu, dateOfStartMenu)
                .subscribe({
                    Log.d(CreateMenuPresenter::class.java.name, "Save start inquirer data success, navigateTo(Screens.ChangeIngredientPortionScreen")
                    router.navigateTo(Screens.ChangeIngredientPortionScreen())
                }, {
                    view?.showError(it)
                    it.printStackTrace()
                })
        )
    }

    override fun createNewMenuClicked() {
        compositeDisposable.add(
            interactor.saveStartInquirerData(name, dayCount, relaxDayCount, peopleCount, timeOfStartMenu, dateOfStartMenu)
                .subscribe({
                    Log.d(CreateMenuPresenter::class.java.name, "Save start inquirer data success")
                    router.navigateTo(Screens.ChangeIngredientPortionScreen())
                }, {
                    view?.showError(it)
                    it.printStackTrace()
                })
        )
    }

    override fun onSetDateStartMenuClicked() {
        view?.showCalendarDialog()
    }

    override fun end() {
        compositeDisposable.add(
            interactor.clearStartInquirerData().subscribe({
                Log.d(CreateMenuPresenter::class.java.name, "Clear start inquirer data success")
            }, {
                it.printStackTrace()
            })
        )
    }
}

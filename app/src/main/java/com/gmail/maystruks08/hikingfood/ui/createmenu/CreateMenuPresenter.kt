package com.gmail.maystruks08.hikingfood.ui.createmenu

import android.util.Log
import com.gmail.maystruks08.domain.interactor.createmenu.CreateMenuInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import ru.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

class CreateMenuPresenter @Inject constructor(
    private val router: Router,
    private val interactor: CreateMenuInteractor
) :
    CreateMenuContract.Presenter, BasePresenter<CreateMenuContract.View>() {

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
    }

    override fun onChangeIngredientPortion() {
        interactor.saveStartInquirerData(name, dayCount, relaxDayCount, peopleCount, timeOfStartMenu, dateOfStartMenu)
        router.navigateTo(Screens.ChangeIngredientPortionScreen())
    }

    override fun createNewMenuClicked() {
        interactor.saveStartInquirerData(name, dayCount, relaxDayCount, peopleCount, timeOfStartMenu, dateOfStartMenu)
        router.navigateTo(Screens.CreateReceptionScreen())
    }

    override fun end() {
        compositeDisposable.add(
            interactor.clearStartInquirerData().subscribe({
                Log.d(CreateMenuPresenter::class.java.name,"Clear start inquirer data success" )
                super.end()
            }, {
                it.printStackTrace()
                super.end()
            })
        )
    }

}

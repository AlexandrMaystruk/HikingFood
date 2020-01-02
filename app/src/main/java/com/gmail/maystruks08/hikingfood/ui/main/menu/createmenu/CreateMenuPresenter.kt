package com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu

import android.util.Log
import com.gmail.maystruks08.domain.CalendarHelper
import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.domain.interactor.createmenu.CreateMenuInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import java.util.*
import javax.inject.Inject

class CreateMenuPresenter @Inject constructor(
    private val interactor: CreateMenuInteractor,
    private val calendarHelper: CalendarHelper
) : CreateMenuContract.Presenter, BasePresenter<CreateMenuContract.View>() {

    private var initConfig: CreateMenuInteractor.Config? = null

    override fun bindView(view: CreateMenuContract.View) {
        super.bindView(view)
        compositeDisposable.add(
            interactor.getInitInfo().subscribe({
                initConfig = it
                view.showInitInfo(it)
            }, {
                it.printStackTrace()
            })
        )
    }

    override fun onNameMenuChanged(menuName: String) {
        initConfig?.name = menuName
    }

    override fun onRelaxDayCountChanged(relaxDayCount: Int) {
        initConfig?.relaxDayCount = relaxDayCount
    }

    override fun onReceptionCountCountChanged(receptionCount: Int) {
        initConfig?.receptionCount = receptionCount
    }

    override fun onCountOfPeopleChanged(countOfPeople: Int) {
        initConfig?.peopleCount = countOfPeople
    }

    override fun onTimeMenuStartChanged(time: Int) {
        initConfig?.timeOfStartMenu = TypeOfMeal.fromValue(time)
    }

    override fun onDateMenuStartChanged(date: Date) {
        initConfig?.dateOfStartMenu = date
        view?.showDateOfStartReception(calendarHelper.format(date, CalendarHelper.DATE_FORMAT))
    }

    override fun onIngredientPortionClicked() {
        compositeDisposable.add(
            interactor.saveStartInquirerData(initConfig!!)
                .subscribe({
                    Log.d(
                        CreateMenuPresenter::class.java.name,
                        "Save start inquirer data success, navigateTo(Screens.ChangeIngredientPortionScreen"
                    )
                    router.navigateTo(Screens.ChangeIngredientPortionScreen())
                }, {
                    view?.showError(it)
                    it.printStackTrace()
                })
        )
    }

    override fun createNewMenuClicked() {
        compositeDisposable.add(
            interactor.saveStartInquirerData(initConfig!!)
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

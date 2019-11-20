package com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu

import com.gmail.maystruks08.domain.interactor.createmenu.CreateMenuInteractor
import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import java.util.*

interface CreateMenuContract {

    interface View : IView {

        fun showInitInfo(config: CreateMenuInteractor.Config)

        fun showCalendarDialog()

        fun showDateOfStartReception(date: String)

    }

    interface Presenter : IPresenter<View> {

        fun createNewMenuClicked()

        fun onNameMenuChanged(menuName: String)

        fun onRelaxDayCountChanged(relaxDayCount: Int)

        fun onReceptionCountCountChanged(receptionCount: Int)

        fun onCountOfPeopleChanged(countOfPeople: Int)

        fun onTimeMenuStartChanged(time: Int)

        fun onDateMenuStartChanged(date: Date)

        fun onIngredientPortionClicked()

        fun onSetDateStartMenuClicked()
    }
}

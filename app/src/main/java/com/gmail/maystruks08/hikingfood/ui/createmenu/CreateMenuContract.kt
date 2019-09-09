package com.gmail.maystruks08.hikingfood.ui.createmenu

import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import java.util.*

interface CreateMenuContract {

    interface View : IView

    interface Presenter : IPresenter<View> {

        fun createNewMenuClicked()

        fun onNameMenuChanged(menuName: String)

        fun onRelaxDayCountChanged(relaxDayCount: Int)

        fun onDayCountChanged(dayCount: Int)

        fun onCountOfPeopleChanged(countOfPeople: Int)

        fun onTimeMenuStartChanged(time: Int)

        fun onDateMenuStartChanged(date: Date)

        fun onIngredientPortionClicked()
    }
}

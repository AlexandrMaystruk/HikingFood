package com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers

import com.gmail.maystruks08.domain.entity.Menu
import com.gmail.maystruks08.hikingfood.ui.viewmodel.MenuView
import javax.inject.Inject

class MenuViewMapper @Inject constructor() {

    fun fromMenu(menu: Menu): MenuView =
        MenuView(
            menu.id,
            menu.name,
            menu.peopleCount,
            menu.numberOfReceptions,
            menu.restDayCount,
            menu.dateOfStartMenu,
            menu.startFrom
        )
}
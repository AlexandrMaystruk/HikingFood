package com.gmail.maystruks08.hikingfood.core.navigation

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Screen

open class AppScreen : Screen() {

    open fun getFragment(): Fragment? {
        return null
    }
}

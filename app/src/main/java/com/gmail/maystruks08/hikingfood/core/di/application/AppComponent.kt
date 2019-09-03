package com.gmail.maystruks08.hikingfood.core.di.application

import com.gmail.maystruks08.hikingfood.core.di.application.createmenu.CreateMenuComponent
import com.gmail.maystruks08.hikingfood.core.di.application.createmenu.createreception.CreateReceptionComponent
import com.gmail.maystruks08.hikingfood.core.di.application.dose.DoseMenuComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.AllMenuComponent
import com.gmail.maystruks08.hikingfood.core.di.application.menu.MenuComponent
import dagger.Component
import com.gmail.maystruks08.hikingfood.ui.main.MainActivity
import javax.inject.Singleton

@Component (modules = [AndroidModule::class, DataAccessModule::class, NavigationModule::class])
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun allMenuComponent(): AllMenuComponent

    fun menuComponent(): MenuComponent

    fun createMenuComponent(): CreateMenuComponent

    fun createReceptionComponent(): CreateReceptionComponent

    fun doseComponent(): DoseMenuComponent

}
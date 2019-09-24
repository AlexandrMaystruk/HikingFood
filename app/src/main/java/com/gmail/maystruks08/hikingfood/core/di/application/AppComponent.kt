package com.gmail.maystruks08.hikingfood.core.di.application

import com.gmail.maystruks08.hikingfood.core.di.application.main.AllMenuComponent
import dagger.Component
import com.gmail.maystruks08.hikingfood.ui.main.MainActivity
import javax.inject.Singleton

@Component (modules = [AndroidModule::class, DataAccessModule::class, NavigationModule::class])
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun allMenuComponent(): AllMenuComponent
}
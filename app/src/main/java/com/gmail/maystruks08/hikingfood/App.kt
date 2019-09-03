package com.gmail.maystruks08.hikingfood

import android.app.Application
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import com.gmail.maystruks08.hikingfood.core.di.application.AndroidModule
import com.gmail.maystruks08.hikingfood.core.di.application.AppComponent
import com.gmail.maystruks08.hikingfood.core.di.application.DaggerAppComponent
import com.gmail.maystruks08.hikingfood.core.di.application.createmenu.CreateMenuComponent
import com.gmail.maystruks08.hikingfood.core.di.application.createmenu.createreception.CreateReceptionComponent
import com.gmail.maystruks08.hikingfood.core.di.application.dose.DoseMenuComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.AllMenuComponent
import com.gmail.maystruks08.hikingfood.core.di.application.menu.MenuComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent

        var menuComponent: MenuComponent? = null
            get() {
                if (field == null)
                    field = appComponent.menuComponent()
                return field
            }

        var createMenuComponent: CreateMenuComponent? = null
            get() {
                if (field == null)
                    field = appComponent.createMenuComponent()
                return field
            }


        var createReceptionComponent: CreateReceptionComponent? = null
            get() {
                if (field == null)
                    field = appComponent.createReceptionComponent()
                return field
            }

        var doseComponent: DoseMenuComponent? = null
            get() {
                if (field == null)
                    field = appComponent.doseComponent()
                return field
            }

        var allMenuComponent: AllMenuComponent? = null
            get() {
                if (field == null)
                    field = appComponent.allMenuComponent()
                return field
            }

        fun clearMenuComponent() {
            menuComponent = null
        }

        fun clearCreateMenuComponent() {
            createMenuComponent = null
        }

        fun clearDoseComponent() {
            doseComponent = null
        }

        fun clearAllMenuComponent() {
            allMenuComponent = null
        }

    }


    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .androidModule(AndroidModule(this))
            .build()

        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException) {
                System.err.println(e.message)
            } else {
                Thread.currentThread().also { thread ->
                    thread.uncaughtExceptionHandler.uncaughtException(thread, e)
                }
            }
        }
    }
}
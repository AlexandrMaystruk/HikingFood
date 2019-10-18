package com.gmail.maystruks08.hikingfood

import android.app.Application
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import com.gmail.maystruks08.hikingfood.core.di.application.AndroidModule
import com.gmail.maystruks08.hikingfood.core.di.application.AppComponent
import com.gmail.maystruks08.hikingfood.core.di.application.DaggerAppComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.menu.createmenu.CreateMenuComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.menu.createmenu.createreception.CreateReceptionComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.menu.portion.ProductPortionComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.AllMenuComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.menu.MenuComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.menu.day.DayComponent

class App : Application() {

    companion object {

        lateinit var appComponent: AppComponent

        var allMenuComponent: AllMenuComponent? = null
            get() {
                if (field == null)
                    field = appComponent.allMenuComponent()
                return field
            }

        var menuComponent: MenuComponent? = null
            get() {
                if (field == null)
                    field = allMenuComponent?.menuComponent()
                return field
            }

        var dayComponent: DayComponent? = null
            get() {
                if (field == null)
                    field = menuComponent?.dayComponent()
                return field
            }

        var createMenuComponent: CreateMenuComponent? = null
            get() {
                if (field == null)
                    field = menuComponent?.createMenuComponent()
                return field
            }

        var portionComponent: ProductPortionComponent? = null
            get() {
                if (field == null)
                    field = menuComponent?.portionComponent()
                return field
            }


        var createReceptionComponent: CreateReceptionComponent? = null
            get() {
                if (field == null)
                    field = createMenuComponent?.createReceptionComponent()
                return field
            }


        fun clearMenuComponent() {
            menuComponent = null
        }

        fun clearCreateMenuComponent() {
            createMenuComponent = null
        }

        fun clearPortionComponent() {
            portionComponent = null
        }

        fun clearAllMenuComponent() {
            allMenuComponent = null
        }

        fun clearDayComponent() {
            dayComponent = null
        }

        fun clearCreateReceptionComponent() {
            createReceptionComponent = null
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
package com.gmail.maystruks08.hikingfood

import android.app.Application
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import com.gmail.maystruks08.hikingfood.core.di.application.AndroidModule
import com.gmail.maystruks08.hikingfood.core.di.application.AppComponent
import com.gmail.maystruks08.hikingfood.core.di.application.DaggerAppComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.allmenu.AllMenuComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.createmenu.CreateMenuComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.createproduct.CreateProductComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.createreception.CreateReceptionComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.createreception.add.AddProductComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.portion.ProductPortionComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.menu.MenuComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.day.DayComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.purchase.ShoppingListComponent

class App : Application() {

    companion object {

        lateinit var appComponent: AppComponent

        var allMenuComponent: AllMenuComponent? = null
            get() {
                if (field == null)
                    field = appComponent.allMenuComponent()
                return field
            }

        var createMenuComponent: CreateMenuComponent? = null
            get() {
                if (field == null)
                    field = allMenuComponent?.createMenuComponent()
                return field
            }

        var createProductComponent: CreateProductComponent? = null
            get() {
                if (field == null)
                    field = allMenuComponent?.createProductComponent()
                return field
            }

        var menuComponent: MenuComponent? = null
            get() {
                if (field == null)
                    field = allMenuComponent?.menuComponent()
                return field
            }

        var portionComponent: ProductPortionComponent? = null
            get() {
                if (field == null)
                    field = allMenuComponent?.portionComponent()
                return field
            }

        var shoppingListComponent: ShoppingListComponent? = null
            get() {
                if (field == null)
                    field = allMenuComponent?.purchaseListComponent()
                return field
            }

        var dayComponent: DayComponent? = null
            get() {
                if (field == null)
                    field = allMenuComponent?.dayComponent()
                return field
            }

        var createReceptionComponent: CreateReceptionComponent? = null
            get() {
                if (field == null)
                    field = createMenuComponent?.createReceptionComponent()
                return field
            }

        var addProductComponent: AddProductComponent? = null
            get() {
                if (field == null)
                    field = createReceptionComponent?.addProductComponent()
                return field
            }


        fun clearMenuComponent() {
            menuComponent = null
        }

        fun clearCreateProductComponent() {
            createProductComponent = null
        }

        fun clearCreateMenuComponent() {
            createMenuComponent = null
        }

        fun clearPortionComponent() {
            portionComponent = null
        }

        fun clearPurchaseListComponent() {
            shoppingListComponent = null
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

        fun clearAddProductComponent() {
            addProductComponent = null
        }
    }


    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .androidModule(AndroidModule(this))
            .build()
        appComponent.inject(this)

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
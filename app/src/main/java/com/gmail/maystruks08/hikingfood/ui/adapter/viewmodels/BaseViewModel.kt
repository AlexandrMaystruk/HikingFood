package com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels

import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactory

abstract class BaseViewModel(val id: Long) {

    abstract fun type(typesFactory: TypesFactory): Int

}
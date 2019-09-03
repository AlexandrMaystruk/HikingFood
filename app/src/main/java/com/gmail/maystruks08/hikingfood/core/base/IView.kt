package com.gmail.maystruks08.hikingfood.core.base

interface IView {

    fun configToolbar()

    fun showLoading ()

    fun hideLoading ()

    fun showError (t: Throwable)

}
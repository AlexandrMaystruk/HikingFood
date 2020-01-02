package com.gmail.maystruks08.hikingfood.core.base

interface IView {

    fun showLoading ()

    fun hideLoading ()

    fun showError (t: Throwable)

}
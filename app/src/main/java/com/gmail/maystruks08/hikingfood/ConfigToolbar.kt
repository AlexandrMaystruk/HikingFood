package com.gmail.maystruks08.hikingfood

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

interface ConfigToolbar {

    fun enableToolbar()

    fun disableToolbar()

    fun enableOverlay()

    fun disableOverlay()

    fun setBackgroundColor(@ColorRes color: Int)

    fun setBackground(icon: Int)

    fun setNavigationIcon(@DrawableRes icon: Int)

    fun removeNavigationIcon()

    fun setToolbarTitle(title: String)

    fun enableBottomBar()

    fun disableBottomBar()
}
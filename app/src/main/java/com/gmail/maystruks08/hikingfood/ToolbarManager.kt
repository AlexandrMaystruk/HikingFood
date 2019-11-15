package com.gmail.maystruks08.hikingfood

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class ToolbarManager constructor(
    private var builder: FragmentToolbar,
    private var container: View
) {
    private var fragmentToolbar: Toolbar? = null

    fun prepareToolbar() {
        if (builder.resId != FragmentToolbar.NO_TOOLBAR) {
            fragmentToolbar = container.findViewById(builder.resId) as Toolbar

            if (builder.title != -1) {
                fragmentToolbar?.setTitle(builder.title)
                fragmentToolbar?.setTitleTextColor(ContextCompat.getColor(container.context, R.color.colorText))
            }

            if(builder.navigationIcon != -1){
                fragmentToolbar?.setNavigationIcon(builder.navigationIcon)
                builder.navigationIconClickListener?.let {
                    fragmentToolbar?.setNavigationOnClickListener(it)
                }
            }

            if (builder.menuId != -1) {
                fragmentToolbar?.inflateMenu(builder.menuId)
            }

            if (builder.menuItems.isNotEmpty() && builder.menuClicks.isNotEmpty()){
                val menu = fragmentToolbar?.menu
                for ((index, menuItemId) in builder.menuItems.withIndex()) {
                    menu?.findItem(menuItemId)?.setOnMenuItemClickListener(builder.menuClicks[index])
                }
            }
        }
    }

    fun changeToolbarTitle(title: String){
        fragmentToolbar?.title = title
    }
}
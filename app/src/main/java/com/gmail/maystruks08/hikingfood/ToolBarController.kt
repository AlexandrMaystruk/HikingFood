package com.gmail.maystruks08.hikingfood

class ToolBarController {

    fun configure(toolbarDescriptor: ToolbarDescriptor, configToolbar: ConfigToolbar) {
        if (toolbarDescriptor.visible) {
            configToolbar.enableToolbar()
        } else {
            configToolbar.disableToolbar()
        }

        if (toolbarDescriptor.collapse) {
            configToolbar.enableOverlay()
        } else {
            configToolbar.disableOverlay()
        }

        if (toolbarDescriptor.title != null) {
            configToolbar.setToolbarTitle(toolbarDescriptor.title)
        }


        if (toolbarDescriptor.color != null){
            configToolbar.setBackgroundColor(toolbarDescriptor.color)
        }

        if (toolbarDescriptor.navigationIcon != null) {
            configToolbar.setNavigationIcon(toolbarDescriptor.navigationIcon)
        } else {
            configToolbar.removeNavigationIcon()
        }

        if (toolbarDescriptor.menu != null) {
            configToolbar.setOptionMenu(toolbarDescriptor.menu)
        }
    }

    fun changeToolbarTitle(title: String, configToolbar: ConfigToolbar){
        configToolbar.setToolbarTitle(title)
    }

    fun addMenuClickListener(configToolbar: ConfigToolbar, menuClickListener: (Int) -> Unit){
        configToolbar.setOptionMenuClickListener(menuClickListener)
    }
}
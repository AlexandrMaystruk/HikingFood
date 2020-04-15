package com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createsets

import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView

interface CreateSetContract {

    /**
     * Нужен для групировки продуктов в наборы.
     * Например макароны всегда идут в паре с сыром и кетчупом, борщ со сметаной =)
     * Для этого выбираем продукт из списка и добавляем к нему обязательные продукты
     */

    interface View : IView

    interface Presenter : IPresenter<View>
}

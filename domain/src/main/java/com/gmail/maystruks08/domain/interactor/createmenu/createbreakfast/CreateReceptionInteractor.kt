package com.gmail.maystruks08.domain.interactor.createmenu.createbreakfast

import com.gmail.maystruks08.domain.entity.Ingredient
import io.reactivex.Single

interface CreateReceptionInteractor {

    fun getDefaultIngredients(): Single<List<Ingredient>>
}
package com.gmail.maystruks08.hikingfood.ui.createmenu.createreception

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DefaultIngredientView
import kotlinx.android.synthetic.main.fragment_create_food_reception.*
import javax.inject.Inject

class CreateFoodReceptionFragment : Fragment(), CreateFoodReceptionContract.View {

    @Inject
    lateinit var presenter: CreateFoodReceptionContract.Presenter

    @Inject
    lateinit var controller: ToolBarController

    lateinit var adapter: IngredientForFoodReceptionAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_create_food_reception, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.createReceptionComponent?.inject(this)
        presenter.bindView(this)
        init()
    }

    override fun configToolbar() {
        controller.configure(
            ToolbarDescriptor.Builder()
                .visibility(true)
                .navigationIcon(R.drawable.ic_arrow_back)
                .title("Создать приемы пищи")
                .build(),
            activity as ConfigToolbar
        )
    }

    private fun init() {
        setAdapter()
        presenter.initFragment(TypeOfMeal.BREAKFAST)
    }

    private fun setAdapter() {
        adapter = IngredientForFoodReceptionAdapter { ingredientPortionItemClicked(it) }
        ingredientOfFoodReceptionRecyclerView.layoutManager =
            LinearLayoutManager(ingredientOfFoodReceptionRecyclerView.context)
        ingredientOfFoodReceptionRecyclerView.adapter = adapter
    }


    override fun showDefaultMenuIngredient(ingredients: List<DefaultIngredientView>) {
        adapter.ingredientList = ingredients.toMutableList()
    }

    private fun ingredientPortionItemClicked(ingredient: DefaultIngredientView) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {
    }

    override fun showError(t: Throwable) {
    }

    companion object {

        fun getInstance(): CreateFoodReceptionFragment =
            CreateFoodReceptionFragment()

    }
}

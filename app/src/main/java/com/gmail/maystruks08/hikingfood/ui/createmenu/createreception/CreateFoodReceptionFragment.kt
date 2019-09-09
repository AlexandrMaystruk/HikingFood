package com.gmail.maystruks08.hikingfood.ui.createmenu.createreception

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.createmenu.createreception.selectingredient.SelectIngredientDialog
import com.gmail.maystruks08.hikingfood.ui.createmenu.createreception.selectingredient.SelectIngredientListener
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView
import kotlinx.android.synthetic.main.fragment_create_food_reception.*
import javax.inject.Inject

class CreateFoodReceptionFragment : Fragment(), CreateFoodReceptionContract.View, SelectIngredientListener {

    @Inject
    lateinit var presenter: CreateFoodReceptionContract.Presenter

    @Inject
    lateinit var controller: ToolBarController

    private lateinit var adapterVariableProduct: VariableProductAdapter

    private lateinit var adapterDefProduct: DefaultProductAdapter


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
        setDefAdapter()
        setVariableAdapter()
        presenter.initFragment()

        vStepProgress.onStepSelected = {
            presenter.onStepSelected(it)
        }

        btnCreateFoodReceiptNextStep.setOnClickListener {
            presenter.onFoodReceptionCreationComplete(adapterDefProduct.getSelectedItems(), adapterVariableProduct.productList)
        }

        fabAddVariableProduct.setOnClickListener {
            presenter.onAddVariableProductClicked()
        }
    }

    private fun setDefAdapter() {
        adapterDefProduct = DefaultProductAdapter { adapterItemOnLongClicked(it) }
        ingredientOfFoodReceptionRecyclerView.layoutManager = LinearLayoutManager(context)
        ingredientOfFoodReceptionRecyclerView.adapter = adapterDefProduct
    }

    private fun setVariableAdapter() {
        adapterVariableProduct = VariableProductAdapter { adapterItemOnLongClicked(it) }
        mutableIngredientOfFoodReceptionRecyclerView.layoutManager = LinearLayoutManager(context)
        mutableIngredientOfFoodReceptionRecyclerView.adapter = adapterVariableProduct
    }


    override fun showDefaultMenuIngredient(products: List<ProductView>) {
        adapterDefProduct.productList = products.toMutableList()
    }

    override fun showVariableMenuIngredient(products: List<ProductView>) {
        adapterVariableProduct.productList = products.toMutableList()
    }

    override fun showSelectIngredientFragment(products: List<ProductView>) {
         SelectIngredientDialog.getInstance(products).show(childFragmentManager, Screens.SELECT_INGREDIENT_DIALOG)
    }

    private fun adapterItemOnLongClicked(product: ProductView) {

    }

    override fun markCurrentStepAsComplete() {
        vStepProgress.nextStep(true)
        if(vStepProgress.isProgressFinished()){
            presenter.onProgressFinished()
        }
    }

    override fun showFinishButton() {
        btnCreateFoodReceiptNextStep.setBackgroundColor(Color.GREEN)
        btnCreateFoodReceiptNextStep.text = "Завершить"
        btnCreateFoodReceiptNextStep.setOnClickListener {
            presenter.onFinishClicked()
        }
    }

    override fun onIngredientsSelected(products: List<ProductView>) {
        presenter.onVariableProductsSelected(products)
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

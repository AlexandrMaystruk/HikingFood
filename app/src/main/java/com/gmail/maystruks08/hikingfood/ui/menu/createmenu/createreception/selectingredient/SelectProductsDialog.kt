package com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception.selectingredient

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.ui.adapter.FactoryAdapter
import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactoryImpl
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductView
import com.gmail.maystruks08.hikingfood.utils.extensions.argument
import kotlinx.android.synthetic.main.dialog_fragment_select_ingredient.*

class SelectProductsDialog : DialogFragment() {

    private var listener: SelectNewProductsListener? = null

    private lateinit var adapterDefProduct: FactoryAdapter

    private var isStaticProducts: Boolean by argument()

    private var productViews: List<ProductView> by argument()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_fragment_select_ingredient, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        adapterDefProduct.items = productViews.toMutableList()
    }

    private fun initView() {
        btnAddSelectedIngredients?.setOnClickListener {
            if (isStaticProducts) {
//              listener?.onStaticProductsSelected(adapterDefProduct.getSelectedItems())
            } else {
//              listener?.onLoopProductsSelected(adapterDefProduct.getSelectedItems())
            }
            this.dismiss()
        }

        btnDismissDialog.setOnClickListener { this.dismiss() }

        val typesFactory = TypesFactoryImpl() //Implement instance by dagger
        adapterDefProduct = FactoryAdapter(typesFactory)
        selectIngrediengRecyclerView.layoutManager = LinearLayoutManager(context)
        selectIngrediengRecyclerView.adapter = adapterDefProduct
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment is SelectNewProductsListener) {
            listener = parentFragment as SelectNewProductsListener
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        selectIngrediengRecyclerView.adapter = null
    }

    override fun onDestroy() {
        super.onDestroy()
        listener = null
    }

    companion object {

        fun getInstance(productViews: List<ProductView>, isStatic: Boolean): SelectProductsDialog =
            SelectProductsDialog().apply {
                this.productViews = productViews
                this.isStaticProducts = isStatic
            }
    }
}
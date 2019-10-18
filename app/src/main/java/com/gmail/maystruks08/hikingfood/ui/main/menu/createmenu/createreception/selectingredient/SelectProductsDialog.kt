package com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu.createreception.selectingredient

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.ui.main.menu.ProductAdapter
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView
import com.gmail.maystruks08.hikingfood.utils.extensions.toArrayList
import kotlinx.android.synthetic.main.dialog_fragment_select_ingredient.*

class SelectProductsDialog : DialogFragment() {

    private var listenerLoop: SelectLoopProductsListener? = null

    private lateinit var adapterDefProduct: ProductAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_fragment_select_ingredient, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        arguments?.getParcelableArrayList<ProductView>(INGREDIENTS)?.let {
            adapterDefProduct.productList = it.toMutableList()
        }
    }

    private fun initView() {
        btnAddSelectedIngredients?.setOnClickListener {
            listenerLoop?.onLoopProductsSelected( adapterDefProduct.productList)
            this.dismiss()
        }

        btnDismissDialog.setOnClickListener { this.dismiss() }

        adapterDefProduct = ProductAdapter {}
        selectIngrediengRecyclerView.layoutManager = LinearLayoutManager(context)
        selectIngrediengRecyclerView.adapter = adapterDefProduct
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment is SelectLoopProductsListener) {
            listenerLoop = parentFragment as SelectLoopProductsListener
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        selectIngrediengRecyclerView.adapter = null
    }

    override fun onDestroy() {
        super.onDestroy()
        listenerLoop = null
    }

    companion object {

        const val INGREDIENTS = "SelectDateDialog"

        fun getInstance(productViews: List<ProductView>): SelectProductsDialog =
            SelectProductsDialog().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(INGREDIENTS, productViews.toArrayList())
                }
            }
    }
}
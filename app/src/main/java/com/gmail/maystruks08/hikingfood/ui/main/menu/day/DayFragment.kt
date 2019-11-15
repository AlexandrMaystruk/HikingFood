package com.gmail.maystruks08.hikingfood.ui.main.menu.day

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.ui.main.menu.ProductAdapter
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DayView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView
import kotlinx.android.synthetic.main.fragment_day.*

class DayFragment : Fragment() {

    private lateinit var breakfastAdapter: ProductAdapter
    private lateinit var lunchAdapter: ProductAdapter
    private lateinit var dinnerAdapter: ProductAdapter
    private lateinit var dayView: DayView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<DayView>(DAY)?.let { dayView = it }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDayView(dayView)
    }

    private fun showDayView(dayView: DayView) {
        dayView.let {
            view.run {
                showBreakfastCard(it.breakfastProducts.isNotEmpty())
                showLunchCard(it.lunchProducts.isNotEmpty())
                showDinnerCard(it.dinnerProducts.isNotEmpty())
            }

            if (it.breakfastProducts.isNotEmpty()) {
                showBreakfastProducts(
                    (it.number).toString(),
                    "Вес на 1 ч: ${it.breakfastTotalWeight}",
                    "Всего: ${it.breakfastTotalWeightForAll}",
                    it.breakfastProducts
                )
            }

            if (it.lunchProducts.isNotEmpty()) {
                showLunchProducts(
                    (it.number).toString(),
                    "Вес на 1 ч: ${it.lunchTotalWeight}",
                    "Всего: ${it.lunchTotalWeightForAll}",
                    it.lunchProducts
                )
            }

            if (it.dinnerProducts.isNotEmpty()) {
                showDinnerProducts(
                    (it.number).toString(),
                    "Вес на 1 ч: ${it.dinnerTotalWeight}",
                    "Всего: ${it.dinnerTotalWeightForAll}",
                    it.dinnerProducts
                )
            }
        }
    }

    private fun showBreakfastProducts(
        number: String,
        totalWeight: String,
        totalWeightForAll: String,
        products: MutableList<ProductView>
    ) {
        tvDayBreakfastValue.text = number
        tvBreakfastReceiptSumWeightForOne.text = totalWeight
        tvBreakfastReceiptSumWeightForAll.text = totalWeightForAll

        breakfastAdapter = ProductAdapter { itemClicked(it) }
        rvBreakfast.layoutManager = LinearLayoutManager(context)
        rvBreakfast.adapter = breakfastAdapter
        breakfastAdapter.productList = products
    }

    private fun showLunchProducts(
        number: String,
        totalWeight: String,
        totalWeightForAll: String,
        products: MutableList<ProductView>
    ) {
        tvDayLunchValue.text = number
        tvLunchReceiptSumWeightForOne.text = totalWeight
        tvLunchReceiptSumWeightForAll.text = totalWeightForAll

        lunchAdapter = ProductAdapter { itemClicked(it) }
        rvLunch.layoutManager = LinearLayoutManager(context)
        rvLunch.adapter = lunchAdapter
        lunchAdapter.productList = products
    }

    private fun showDinnerProducts(
        number: String,
        totalWeight: String,
        totalWeightForAll: String,
        products: MutableList<ProductView>
    ) {
        tvDayDinnerValue.text = number
        tvDinnerReceiptSumWeightForOne.text = totalWeight
        tvDinnerReceiptSumWeightForAll.text = totalWeightForAll

        dinnerAdapter = ProductAdapter { itemClicked(it) }
        rvDinner.layoutManager = LinearLayoutManager(context)
        rvDinner.adapter = dinnerAdapter
        dinnerAdapter.productList = products
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rvBreakfast.adapter = null
        rvLunch.adapter = null
        rvDinner.adapter = null
    }

    private fun itemClicked(productView: ProductView) {}

    private fun showBreakfastCard(enable: Boolean) {
        cardBreakfast.visibility = if (enable) View.VISIBLE else View.GONE
    }

    private fun showLunchCard(enable: Boolean) {
        cardLunch.visibility = if (enable) View.VISIBLE else View.GONE
    }

    private fun showDinnerCard(enable: Boolean) {
        cardDinner.visibility = if (enable) View.VISIBLE else View.GONE
    }

    companion object {

        private const val DAY = "day"

        fun getInstance(day: DayView): DayFragment =
            DayFragment().apply {
                arguments = Bundle().apply { putParcelable(DAY, day) }
            }
    }
}


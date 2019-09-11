package com.gmail.maystruks08.hikingfood.ui.menu.day

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DayView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView
import kotlinx.android.synthetic.main.fragment_day.*
import javax.inject.Inject

class DayFragment : Fragment(), DayContract.View {

    @Inject
    lateinit var controller: ToolBarController

    private lateinit var breakfastAdapter: ProductAdapter
    private lateinit var lunchAdapter: ProductAdapter
    private lateinit var dinnerAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun configToolbar() {
        controller.configure(
            ToolbarDescriptor.Builder()
                .visibility(true)
                .navigationIcon(R.drawable.ic_arrow_back)
                .title("День ${arguments?.getInt(DAY_NUMBER)}")
                .build(),
            activity as ConfigToolbar
        )
    }

    private fun init() {
        arguments?.getParcelable<DayView>(DAY)?.let {
            setBreakfastAdapter(it.breakfastProducts)
            setLunchAdapter(it.lunchProducts)
            setDinnerAdapter(it.dinnerProducts)

            tvDayBreakfastValue.text = (it.number + 1).toString()
            tvDayLunchValue.text = (it.number + 1).toString()
            tvDayDinnerValue.text = (it.number + 1).toString()

            tvBreakfastReceiptSumWeightValue.text = it.breakfastTotalWeight.toString()
            tvLunchSumWeightValue.text = it.lunchTotalWeight.toString()
            tvDinnerReceiptSumWeightValue.text = it.dinnerTotalWeight.toString()
        }
    }

    private fun setBreakfastAdapter(products: MutableList<ProductView>) {
        breakfastAdapter = ProductAdapter { itemClicked(it) }
        rvBreakfast.layoutManager = LinearLayoutManager(context)
        rvBreakfast.adapter = breakfastAdapter
        breakfastAdapter.productList = products
    }

    private fun setLunchAdapter(products: MutableList<ProductView>) {
        lunchAdapter = ProductAdapter { itemClicked(it) }
        rvLunch.layoutManager = LinearLayoutManager(context)
        rvLunch.adapter = lunchAdapter
        lunchAdapter.productList = products
    }

    private fun setDinnerAdapter(products: MutableList<ProductView>) {
        dinnerAdapter = ProductAdapter { itemClicked(it) }
        rvDinner.layoutManager = LinearLayoutManager(context)
        rvDinner.adapter = dinnerAdapter
        dinnerAdapter.productList = products
    }

    private fun itemClicked(productView: ProductView) {

    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(t: Throwable) {
    }

    companion object {

        private const val DAY = "day"

        private const val DAY_NUMBER = "dayNumber"

        fun getInstance(day: DayView): DayFragment =
            DayFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DAY, day)
                    putInt(DAY_NUMBER, day.number)
                }
            }
    }
}


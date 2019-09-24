package com.gmail.maystruks08.hikingfood.ui.main.menu.day

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

    @Inject
    lateinit var presenter: DayContract.Presenter

    private lateinit var breakfastAdapter: ProductAdapter
    private lateinit var lunchAdapter: ProductAdapter
    private lateinit var dinnerAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.dayComponent?.inject(this)
        return inflater.inflate(R.layout.fragment_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this, arguments?.getParcelable<DayView>(DAY))
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

    override fun showBreakfastProducts(
        number: String,
        totalWeight: String,
        products: MutableList<ProductView>
    ) {
        tvDayBreakfastValue.text = number
        tvBreakfastReceiptSumWeightValue.text = totalWeight

        breakfastAdapter = ProductAdapter { itemClicked(it) }
        rvBreakfast.layoutManager = LinearLayoutManager(context)
        rvBreakfast.adapter = breakfastAdapter
        breakfastAdapter.productList = products
    }

    override fun showLunchProducts(
        number: String,
        totalWeight: String,
        products: MutableList<ProductView>
    ) {
        tvDayLunchValue.text = number
        tvLunchSumWeightValue.text = totalWeight

        lunchAdapter = ProductAdapter { itemClicked(it) }
        rvLunch.layoutManager = LinearLayoutManager(context)
        rvLunch.adapter = lunchAdapter
        lunchAdapter.productList = products
    }

    override fun showDinnerProducts(
        number: String,
        totalWeight: String,
        products: MutableList<ProductView>
    ) {
        tvDayDinnerValue.text = number
        tvDinnerReceiptSumWeightValue.text = totalWeight

        dinnerAdapter = ProductAdapter { itemClicked(it) }
        rvDinner.layoutManager = LinearLayoutManager(context)
        rvDinner.adapter = dinnerAdapter
        dinnerAdapter.productList = products
    }

    private fun itemClicked(productView: ProductView) {}

    override fun showBreakfastCard(enable: Boolean) {
        cardBreakfast.visibility = if (enable) View.VISIBLE else View.GONE
    }

    override fun showLunchCard(enable: Boolean) {
        cardLunch.visibility = if (enable) View.VISIBLE else View.GONE

    }

    override fun showDinnerCard(enable: Boolean) {
        cardDinner.visibility = if (enable) View.VISIBLE else View.GONE
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}

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


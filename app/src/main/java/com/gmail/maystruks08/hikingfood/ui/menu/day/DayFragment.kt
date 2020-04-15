package com.gmail.maystruks08.hikingfood.ui.menu.day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.core.base.BaseFragment
import com.gmail.maystruks08.hikingfood.ui.adapter.AdapterCallbacks
import com.gmail.maystruks08.hikingfood.ui.adapter.FactoryAdapter
import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactory
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.DayView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductView
import com.gmail.maystruks08.hikingfood.utils.extensions.argument
import kotlinx.android.synthetic.main.fragment_day.*
import javax.inject.Inject

class DayFragment : BaseFragment(), DayContract.View {

    @Inject
    lateinit var presenter: DayContract.Presenter

    @Inject
    lateinit var typesFactory: TypesFactory

    private lateinit var breakfastAdapter: FactoryAdapter
    private lateinit var lunchAdapter: FactoryAdapter
    private lateinit var dinnerAdapter: FactoryAdapter

    private var dayView: DayView by argument()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        App.dayComponent?.inject(this)
        presenter.bindView(this, dayView)
        return inflater.inflate(R.layout.fragment_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initFragment()
    }

    override fun builder(): FragmentToolbar = FragmentToolbar.Builder().build()

    override fun initViews() {
        breakfastAdapter = FactoryAdapter(typesFactory, onClickListener = object : AdapterCallbacks.OnClickListener<ProductView> {
            override fun onClicked(item: ProductView) {
                presenter.onProductClicked(item, TypeOfMeal.BREAKFAST)
            }
        })
        lunchAdapter = FactoryAdapter(typesFactory, onClickListener = object : AdapterCallbacks.OnClickListener<ProductView> {
            override fun onClicked(item: ProductView) {
                presenter.onProductClicked(item, TypeOfMeal.LUNCH)
            }
        })
        dinnerAdapter = FactoryAdapter(typesFactory,onClickListener = object : AdapterCallbacks.OnClickListener<ProductView> {
            override fun onClicked(item: ProductView) {
                presenter.onProductClicked(item, TypeOfMeal.DINNER)
            }
        })
        rvBreakfast.layoutManager = LinearLayoutManager(context)
        rvBreakfast.adapter = breakfastAdapter
        rvLunch.layoutManager = LinearLayoutManager(context)
        rvLunch.adapter = lunchAdapter
        rvDinner.layoutManager = LinearLayoutManager(context)
        rvDinner.adapter = dinnerAdapter
    }

    override fun showDayMeal(showBreakfast: Boolean, showLunch: Boolean, showDinner: Boolean){
        cardBreakfast.visibility = if (showBreakfast) View.VISIBLE else View.GONE
        cardLunch.visibility = if (showLunch) View.VISIBLE else View.GONE
        cardDinner.visibility = if (showDinner) View.VISIBLE else View.GONE
    }

    override fun showBreakfastProducts(number: String, totalWeight: String, totalWeightForAll: String, products: MutableList<ProductView>) {
        val dayBreakfastStr = "${tvDayBreakfast.text} $number"
        tvDayBreakfast.text = dayBreakfastStr
        tvBreakfastReceiptSumWeightForOne.text = totalWeight
        tvBreakfastReceiptSumWeightForAll.text = totalWeightForAll
        breakfastAdapter.items = products.toMutableList()
    }

    override fun showLunchProducts(number: String, totalWeight: String, totalWeightForAll: String, products: MutableList<ProductView>) {
        val dayLunchStr = "${tvDayLunch.text} $number"
        tvDayLunch.text = dayLunchStr
        tvLunchReceiptSumWeightForOne.text = totalWeight
        tvLunchReceiptSumWeightForAll.text = totalWeightForAll
        lunchAdapter.items = products.toMutableList()
    }

    override fun showDinnerProducts(number: String, totalWeight: String, totalWeightForAll: String, products: MutableList<ProductView>) {
        val dayDinnerStr = "${tvDayDinner.text} $number"
        tvDayDinner.text = dayDinnerStr
        tvDinnerReceiptSumWeightForOne.text = totalWeight
        tvDinnerReceiptSumWeightForAll.text = totalWeightForAll
        dinnerAdapter.items = products.toMutableList()
    }

    override fun showProductInserted(product: ProductView, typeOfMeal: TypeOfMeal, position: Int?) {
        when(typeOfMeal){
            TypeOfMeal.BREAKFAST -> breakfastAdapter.insertItem(product, position)
            TypeOfMeal.LUNCH -> lunchAdapter.insertItem(product, position)
            else -> dinnerAdapter.insertItem(product, position)
        }
    }

    override fun showProductUpdated(updatedProduct: ProductView, typeOfMeal: TypeOfMeal) {
        when(typeOfMeal){
            TypeOfMeal.BREAKFAST -> breakfastAdapter.updateItem(updatedProduct)
            TypeOfMeal.LUNCH -> lunchAdapter.updateItem(updatedProduct)
            else -> dinnerAdapter.updateItem(updatedProduct)
        }
    }

    override fun showProductRemoved(position: Int, typeOfMeal: TypeOfMeal) {
        when(typeOfMeal){
            TypeOfMeal.BREAKFAST -> breakfastAdapter.removeItem(position)
            TypeOfMeal.LUNCH -> lunchAdapter.removeItem(position)
            else -> dinnerAdapter.removeItem(position)
        }
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}

    override fun onDestroyView() {
        super.onDestroyView()
        rvBreakfast.adapter = null
        rvLunch.adapter = null
        rvDinner.adapter = null
        presenter.end()
        App.clearDayComponent()
    }

    companion object {

        fun getInstance(day: DayView): DayFragment = DayFragment().apply { this.dayView = day }
    }
}


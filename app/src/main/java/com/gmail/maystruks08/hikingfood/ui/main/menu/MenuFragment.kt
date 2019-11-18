package com.gmail.maystruks08.hikingfood.ui.main.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.core.base.BaseFragment
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DayView
import com.gmail.maystruks08.hikingfood.utils.GridSpacingItemDecoration
 import kotlinx.android.synthetic.main.fragment_menu.*
import javax.inject.Inject

class MenuFragment : BaseFragment(), MenuContract.View {

    @Inject
    lateinit var presenter: MenuContract.Presenter

    private lateinit var daysAdapter: DayAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.menuComponent?.inject(this)
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)
    }
    override fun builder(): FragmentToolbar {
        return FragmentToolbar.Builder()
            .withId(R.id.toolbarMenu)
            .withTitle( R.string.fragment_menu_name)
            .withNavigationIcon(R.drawable.ic_arrow_back) { presenter.onBackClicked() }
            .build()
    }

    override fun initViews() {
        daysAdapter = DayAdapter { dayItemClicked(it) }
        daysRecyclerView.layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.dayPageItemsCount))
        daysRecyclerView.adapter = daysAdapter
        daysRecyclerView.addItemDecoration(
            GridSpacingItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.margin_xs
                ), resources.getInteger(R.integer.dayPageItemsCount)
            )
        )
        arguments?.getLong(MENU_ID)?.let { presenter.initFragment(it) }

        btnGetPurchaseList.setOnClickListener {
            presenter.onShowPurchaseList()
        }
    }

    private fun dayItemClicked(day: DayView) {
        presenter.dayItemClicked(day)
    }

    override fun showFoodDays(days: List<DayView>) {
        daysAdapter.dayList = days.toMutableList()
    }

    override fun onDestroyView() {
        daysRecyclerView.adapter = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        presenter.end()
        App.clearMenuComponent()
        super.onDestroy()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(t: Throwable) {
    }

    companion object {

        private const val MENU_ID = "menuId"

        fun getInstance(menuId: Long): MenuFragment =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putLong(MENU_ID, menuId)
                }
            }
    }
}


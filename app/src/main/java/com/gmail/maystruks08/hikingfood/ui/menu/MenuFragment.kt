package com.gmail.maystruks08.hikingfood.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.core.base.BaseFragment
import com.gmail.maystruks08.hikingfood.ui.adapter.FactoryAdapter
import com.gmail.maystruks08.hikingfood.ui.adapter.AdapterCallbacks
import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactory
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.DayView
import com.gmail.maystruks08.hikingfood.utils.GridSpacingItemDecoration
import com.gmail.maystruks08.hikingfood.utils.extensions.argument
import com.gmail.maystruks08.hikingfood.utils.extensions.toast
import kotlinx.android.synthetic.main.fragment_menu.*
import javax.inject.Inject

class MenuFragment : BaseFragment(), MenuContract.View, AdapterCallbacks.OnClickListener<DayView> {

    @Inject
    lateinit var presenter: MenuContract.Presenter

    @Inject
    lateinit var typesFactory: TypesFactory

    private lateinit var daysAdapter: FactoryAdapter

    private var menuId: Long by argument()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        App.menuComponent?.inject(this)
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)
    }
    override fun builder(): FragmentToolbar {
        val menuItemClick = MenuItem.OnMenuItemClickListener {
            presenter.onSaveMenuToPDF()
            true
        }
        return FragmentToolbar.Builder()
            .withId(R.id.toolbarMenu)
            .withTitle( R.string.fragment_menu_name)
            .withNavigationIcon(R.drawable.ic_arrow_back) { presenter.onBackClicked() }
            .withMenu(R.menu.menu_food_menu)
            .withMenuItems(listOf(R.id.action_save_menu_to_pdf), listOf(menuItemClick))
            .build()
    }

    override fun initViews() {
        daysAdapter = FactoryAdapter(typesFactory, onClickListener = this)
        daysRecyclerView.layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.dayPageItemsCount))
        daysRecyclerView.adapter = daysAdapter
        daysRecyclerView.addItemDecoration(GridSpacingItemDecoration(resources.getDimensionPixelSize(
                    R.dimen.margin_xs
                ), resources.getInteger(R.integer.dayPageItemsCount)))
        presenter.initFragment(menuId)

        btnGetPurchaseList.setOnClickListener {
            presenter.onShowShoppingList()
        }
    }

    override fun onClicked(item: DayView) {
        presenter.dayItemClicked(item)
    }

    override fun showFoodDays(days: List<DayView>) {
        daysAdapter.items = days.toMutableList()
    }

    override fun showMessage(message: String) {
        context?.toast(message)
    }

    override fun onDestroyView() {
        daysRecyclerView.adapter = null
        presenter.end()
        App.clearMenuComponent()
        super.onDestroyView()
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}

    companion object {

        fun getInstance(menuId: Long): MenuFragment = MenuFragment().apply { this.menuId = menuId }
    }
}


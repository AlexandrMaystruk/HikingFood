package com.gmail.maystruks08.hikingfood.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.gmail.maystruks08.hikingfood.App
import com.gmail.maystruks08.hikingfood.FragmentToolbar
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.core.base.BaseFragment
import com.gmail.maystruks08.hikingfood.ui.adapter.AdapterCallbacks
import com.gmail.maystruks08.hikingfood.ui.adapter.FactoryAdapter
import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactory
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.DayView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.MenuView
import com.gmail.maystruks08.hikingfood.utils.GridSpacingItemDecoration
import com.gmail.maystruks08.hikingfood.utils.extensions.argument
import com.gmail.maystruks08.hikingfood.utils.extensions.gone
import com.gmail.maystruks08.hikingfood.utils.extensions.toast
import com.gmail.maystruks08.hikingfood.utils.extensions.visible
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.layout_menu.*
import kotlinx.android.synthetic.main.layout_menu.view.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.abs


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
            .withId(R.id.toolbar)
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

        appBarLayout.addOnOffsetChangedListener(OnOffsetChangedListener { _, verticalOffset ->
            if (abs(verticalOffset) > 200) {
                beginTransition()
                appBarMenuLayout.gone()
            } else {
                beginTransition()
                appBarMenuLayout.visible()
            }
        })
        presenter.initFragment(menuId)

        btnGetPurchaseList.setOnClickListener {
            presenter.onShowShoppingList()
        }
    }

    override fun showMenuInfo(menuView: MenuView) {
        tvMenuName.text = menuView.name
        tvCountOfPeopleValue.text = menuView.peopleCount.toString()
        tvMenuDateStartValue.text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(menuView.dateOfStartMenu)
        tvRelaxDayCountValue.text = menuView.relaxDayCount.toString()
        tvStartMenuValue.text = menuView.startFrom.title.toLowerCase()
        val totalWeightMenuValueStr = "${menuView.totalWeight / 1000} кг"
        tvTotalWeightMenuValue.text = totalWeightMenuValueStr
        tvCountOfReceptionsValue.text = menuView.countOfReceptions.toString()
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

    private fun beginTransition() {
        val transition = TransitionSet()
            .setOrdering(TransitionSet.ORDERING_TOGETHER)
            .addTransition(Fade(Fade.IN))
            .addTransition(ChangeBounds())
        TransitionManager.beginDelayedTransition(collapsedFrameLayout, transition)
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


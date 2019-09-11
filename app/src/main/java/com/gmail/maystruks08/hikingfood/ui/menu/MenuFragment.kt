package com.gmail.maystruks08.hikingfood.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DayView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.MenuView
import kotlinx.android.synthetic.main.fragment_menu.*
import javax.inject.Inject

class MenuFragment : Fragment(), MenuContract.View {

    @Inject
    lateinit var presenter: MenuContract.Presenter

    @Inject
    lateinit var controller: ToolBarController

    private lateinit var daysAdapter: DayAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.menuComponent?.inject(this)
        presenter.bindView(this)
        init()
    }

    override fun configToolbar() {
        controller.configure(
            ToolbarDescriptor.Builder()
                .visibility(true)
                .navigationIcon(R.drawable.ic_arrow_back)
                .title("Выбранная раскладка")
                .build(),
            activity as ConfigToolbar
        )
    }

    private fun init() {
        setAdapter()
        arguments?.getLong(MENU_ID)?.let { presenter.initFragment(it) }
    }

    private fun setAdapter() {
        daysAdapter = DayAdapter { dayItemClicked(it) }
        daysRecyclerView.layoutManager = LinearLayoutManager(context)
        daysRecyclerView.adapter = daysAdapter
    }

    private fun dayItemClicked(day: DayView) {
        presenter.dayItemClicked(day)
    }

    override fun showFoodDays(days: List<DayView>) {
        daysAdapter.dayList = days.toMutableList()
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


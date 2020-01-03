package com.gmail.maystruks08.hikingfood.ui.main

import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.ui.viewmodels.MenuView
import com.gmail.maystruks08.hikingfood.utils.SwipeActionHelper
import kotlinx.android.synthetic.main.fragment_all_menu_list.*
import javax.inject.Inject
import android.view.*
import com.gmail.maystruks08.hikingfood.core.base.BaseFragment
import com.gmail.maystruks08.hikingfood.utils.extensions.setVisibilityIfNeed

class AllMenuFragment : BaseFragment(), AllMenuContract.View {

    @Inject
    lateinit var presenter: AllMenuContract.Presenter

    private lateinit var allMenuAdapter: AllMenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.allMenuComponent?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_all_menu_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)
    }

    override fun builder(): FragmentToolbar {
        return FragmentToolbar.Builder()
            .withId(R.id.toolbar)
            .withTitle(R.string.all_menu_fragment_name)
            .withMenu(R.menu.menu_search)
            .withMenuSearch { presenter.onSearchQueryChanged(it) }
            .build()
    }

    override fun initViews() {
        allMenuAdapter = AllMenuAdapter { menuItemClicked(it) }
        allMenuRecyclerView.layoutManager = LinearLayoutManager(allMenuRecyclerView.context)
        allMenuRecyclerView.adapter = allMenuAdapter
        context?.let {
            val swipeHelper = object : SwipeActionHelper(it) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    if (direction == ItemTouchHelper.LEFT) {
                        presenter.onDeleteMenuClicked(position, allMenuAdapter.menuList[position])
                    }
                }
            }
            ItemTouchHelper(swipeHelper).attachToRecyclerView(allMenuRecyclerView)
        }
        btnCreateNewMenu.setOnClickListener {
            presenter.createNewMenuClicked()
        }
    }

    override fun showAllMenuList(allMenuList: List<MenuView>) {
        allMenuAdapter.menuList = allMenuList.toMutableList()
    }

    override fun showMenuInserted(position: Int, menuView: MenuView) {
        allMenuAdapter.menuList.add(position, menuView)
        allMenuAdapter.notifyItemInserted(position)
    }

    override fun showMenuRemoved(position: Int) {
        allMenuAdapter.removeItem(position)
    }

    override fun updateItem(position: Int) {
        allMenuAdapter.notifyItemChanged(position)
    }

    private fun menuItemClicked(menu: MenuView) {
        presenter.onMenuItemClicked(menu)
    }

    override fun showNoData(enable: Boolean) {
        if (enable) {
            viewNoData.setVisibilityIfNeed(View.VISIBLE)
        } else {
            viewNoData.setVisibilityIfNeed(View.GONE)
        }
    }

    override fun onDestroyView() {
        allMenuRecyclerView.adapter = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        App.clearAllMenuComponent()
        super.onDestroy()
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}

}

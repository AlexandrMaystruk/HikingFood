package com.gmail.maystruks08.hikingfood.ui

import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.MenuView
import com.gmail.maystruks08.hikingfood.utils.SwipeActionHelper
import kotlinx.android.synthetic.main.fragment_all_menu_list.*
import javax.inject.Inject
import android.view.*
import com.gmail.maystruks08.hikingfood.core.base.BaseFragment
import com.gmail.maystruks08.hikingfood.ui.adapter.FactoryAdapter
import com.gmail.maystruks08.hikingfood.ui.adapter.AdapterCallbacks
import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactory
import com.gmail.maystruks08.hikingfood.utils.extensions.setVisibilityIfNeed

class AllMenuFragment : BaseFragment(), AllMenuContract.View, AdapterCallbacks.OnClickListener<MenuView> {

    @Inject
    lateinit var presenter: AllMenuContract.Presenter

    @Inject
    lateinit var typeFactory: TypesFactory

    private lateinit var allMenuAdapter: FactoryAdapter

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
        allMenuAdapter = FactoryAdapter(typeFactory, onClickListener = this)
        allMenuRecyclerView.layoutManager = LinearLayoutManager(allMenuRecyclerView.context)
        allMenuRecyclerView.adapter = allMenuAdapter
        context?.let {
            val swipeHelper = object : SwipeActionHelper(it) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    if (direction == ItemTouchHelper.LEFT) {
                        presenter.onDeleteMenuClicked(position, allMenuAdapter.items[position].id)
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
        allMenuAdapter.items = allMenuList.toMutableList()
    }

    override fun showMenuInserted(position: Int, menuView: MenuView) {
        allMenuAdapter.items.add(position, menuView)
        allMenuAdapter.notifyItemInserted(position)
    }

    override fun showMenuRemoved(position: Int) {
        allMenuAdapter.removeItem(position)
    }

    override fun updateItem(position: Int) {
        allMenuAdapter.notifyItemChanged(position)
    }

    override fun onClicked(item: MenuView) {
        presenter.onMenuItemClicked(item)
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
        App.clearAllMenuComponent()
        super.onDestroyView()
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}

}

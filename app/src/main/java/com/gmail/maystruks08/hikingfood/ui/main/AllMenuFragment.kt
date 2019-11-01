package com.gmail.maystruks08.hikingfood.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.ui.viewmodel.MenuView
import com.gmail.maystruks08.hikingfood.utils.SwipeActionHelper
import kotlinx.android.synthetic.main.fragment_all_menu_list.*
import javax.inject.Inject
import android.view.*
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.layout_no_data.*

class AllMenuFragment : Fragment(), AllMenuContract.View {

    @Inject
    lateinit var presenter: AllMenuContract.Presenter

    @Inject
    lateinit var controller: ToolBarController

    private lateinit var allMenuAdapter: AllMenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        App.allMenuComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_all_menu_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)
        init()
        initCardSwipe()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        initSearch(menu, inflater)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initSearch(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchView = menu.findItem(R.id.action_search)?.actionView as? SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.onSearchQueryChanged(newText)
                return false
            }
        })
    }

    override fun configToolbar() {
        controller.configure(
            ToolbarDescriptor.Builder()
                .visibility(true)
                .title("Раскладка")
                .build(),
            activity as ConfigToolbar
        )
    }

    private fun init() {
        setAdapter()
        btnCreateNewMenu.setOnClickListener {
            presenter.createNewMenuClicked()
        }
    }

    private fun setAdapter() {
        allMenuAdapter = AllMenuAdapter { menuItemClicked(it) }
        allMenuRecyclerView.layoutManager = LinearLayoutManager(allMenuRecyclerView.context)
        allMenuRecyclerView.adapter = allMenuAdapter
    }

    private fun initCardSwipe() {
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
            if (ivSadCat.visibility == View.GONE || tvNoData.visibility == View.GONE) {
                ivSadCat.visibility = View.VISIBLE
                tvNoData.visibility = View.VISIBLE
            }
        } else {
            if (ivSadCat.visibility == View.VISIBLE || tvNoData.visibility == View.VISIBLE) {
                ivSadCat.visibility = View.GONE
                tvNoData.visibility = View.GONE
            }
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

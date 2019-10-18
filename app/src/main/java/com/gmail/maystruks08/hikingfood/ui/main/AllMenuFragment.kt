package com.gmail.maystruks08.hikingfood.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.ui.viewmodel.MenuView
import com.gmail.maystruks08.hikingfood.utils.SwipeActionHelper
import kotlinx.android.synthetic.main.fragment_all_menu_list.*
import javax.inject.Inject

class AllMenuFragment : Fragment(), AllMenuContract.View {

    @Inject
    lateinit var presenter: AllMenuContract.Presenter

    @Inject
    lateinit var controller: ToolBarController

    private lateinit var allMenuAdapter: AllMenuAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        App.allMenuComponent?.inject(this)
        return inflater.inflate(R.layout.fragment_all_menu_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)
        init()
        initCardSwipe()
    }

    override fun configToolbar() {
        controller.configure(
            ToolbarDescriptor.Builder()
                .visibility(false)
                .build(),
            activity as ConfigToolbar
        )
    }

    private fun init() {
        menuSearchView.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    presenter.onSearchQueryChanged(s.toString())
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

        setAdapter()

        fabCreateNewMenu.setOnClickListener {
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

    override fun onDestroyView() {
        allMenuRecyclerView.adapter = null
        super.onDestroyView()
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}

    companion object {

        fun getInstance(): AllMenuFragment = AllMenuFragment()
    }
}

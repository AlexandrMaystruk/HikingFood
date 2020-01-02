package com.gmail.maystruks08.hikingfood.ui.main.menu.shopping

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.core.base.BaseFragment
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ShoppingListItemView
import com.gmail.maystruks08.hikingfood.utils.extensions.toast
import kotlinx.android.synthetic.main.fragment_shopping_list.*
import javax.inject.Inject

class ShoppingListFragment : BaseFragment(), ShoppingListContract.View {

    @Inject
    lateinit var presenter: ShoppingListContract.Presenter

    private lateinit var adapter: ShoppingListItemAdapter

    private lateinit var menuName: String
    private var menuId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            menuName = getString(MENU_NAME_FOR_SHOPPING_LIST) ?: "No name"
            menuId = getLong(MENU_ID_FOR_SHOPPING_LIST)
        }
        App.shoppingListComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_shopping_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this@ShoppingListFragment, menuId)
    }

    override fun builder(): FragmentToolbar {
        val menuItemClick = MenuItem.OnMenuItemClickListener {
            presenter.onSaveShoppingListToPDF(menuId, menuName)
            true
        }
        return FragmentToolbar.Builder()
            .withId(R.id.toolbarPurchaseList)
            .withTitle(R.string.fragment_purchase_list_name)
            .withNavigationIcon(R.drawable.ic_arrow_back) { presenter.onBackClicked() }
            .withMenu(R.menu.menu_purchase_list)
            .withMenuItems(listOf(R.id.action_save_to_pdf), listOf(menuItemClick))
            .build()
    }

    override fun initViews() {
        adapter = ShoppingListItemAdapter(::onItemClicked)
        shoppingListItemsRecyclerView.layoutManager =
            LinearLayoutManager(shoppingListItemsRecyclerView.context)
        shoppingListItemsRecyclerView.adapter = adapter
    }

    private fun onItemClicked(itemView: ShoppingListItemView) {}

    override fun showShoppingList(items: List<ShoppingListItemView>) {
        adapter.shoppingListItems = items.toMutableList()
    }

    override fun showMessage(message: String) {
        context?.toast(message)
    }

    override fun onDestroyView() {
        shoppingListItemsRecyclerView.adapter = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        presenter.end()
        App.clearPurchaseListComponent()
        super.onDestroy()
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}


    companion object {

        private const val MENU_ID_FOR_SHOPPING_LIST = "MENU_ID_FOR_SHOPPING_LIST"

        private const val MENU_NAME_FOR_SHOPPING_LIST = "MENU_NAME_FOR_SHOPPING_LIST"

        fun getInstance(menuId: Long, menuName: String): ShoppingListFragment =
            ShoppingListFragment().apply {
                arguments = Bundle().apply {
                    putLong(MENU_ID_FOR_SHOPPING_LIST, menuId)
                    putString(MENU_NAME_FOR_SHOPPING_LIST, menuName)
                }
            }
    }
}

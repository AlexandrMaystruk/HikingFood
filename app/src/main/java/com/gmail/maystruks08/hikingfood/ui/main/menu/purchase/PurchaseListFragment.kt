package com.gmail.maystruks08.hikingfood.ui.main.menu.purchase

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.core.base.BaseFragment
import com.gmail.maystruks08.hikingfood.ui.viewmodel.PurchaseListItemView
import com.gmail.maystruks08.hikingfood.utils.extensions.toast
import kotlinx.android.synthetic.main.fragment_purchase_list.*
import javax.inject.Inject

class PurchaseListFragment : BaseFragment(), PurchaseListContract.View {

    @Inject
    lateinit var presenter: PurchaseListContract.Presenter

    private lateinit var adapter: PurchaseListItemAdapter

    private lateinit var menuName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.purchaseListComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_purchase_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            presenter.bindView(this@PurchaseListFragment, getLong(MENU_ID_FOR_PURCHASE_LIST))
            menuName = getString(MENU_NAME_FOR_PURCHASE_LIST) ?: "No name"
        }
    }

    override fun builder(): FragmentToolbar {
        val menuItemClick = MenuItem.OnMenuItemClickListener {
            presenter.onSavePurchaseListToPDF(menuName)
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
        adapter = PurchaseListItemAdapter(::onItemClicked)
        purchaseListItemsRecyclerView.layoutManager =
            LinearLayoutManager(purchaseListItemsRecyclerView.context)
        purchaseListItemsRecyclerView.adapter = adapter
    }

    private fun onItemClicked(itemView: PurchaseListItemView) {}

    override fun showPurchaseList(items: List<PurchaseListItemView>) {
        adapter.purchaseListItems = items.toMutableList()
    }

    override fun showMessage(message: String) {
        context?.toast(message)
    }

    override fun onDestroyView() {
        purchaseListItemsRecyclerView.adapter = null
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

        private const val MENU_ID_FOR_PURCHASE_LIST = "MENU_ID_FOR_PURCHASE_LIST"

        private const val MENU_NAME_FOR_PURCHASE_LIST = "MENU_NAME_FOR_PURCHASE_LIST"

        fun getInstance(menuId: Long, menuName: String): PurchaseListFragment =
            PurchaseListFragment().apply {
                arguments = Bundle().apply {
                    putLong(MENU_ID_FOR_PURCHASE_LIST, menuId)
                    putString(MENU_NAME_FOR_PURCHASE_LIST, menuName)
                }
            }
    }
}

package com.gmail.maystruks08.hikingfood.ui.main.menu.purchase

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.ui.viewmodel.PurchaseListItemView
import com.gmail.maystruks08.hikingfood.utils.extensions.toArrayList
import kotlinx.android.synthetic.main.fragment_purchase_list.*
import javax.inject.Inject

class PurchaseListFragment : Fragment(), PurchaseListContract.View {

    @Inject
    lateinit var presenter: PurchaseListContract.Presenter

    @Inject
    lateinit var controller: ToolBarController

    private lateinit var adapter: PurchaseListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.purchaseListComponent?.inject(this)
        arguments?.getParcelableArrayList<PurchaseListItemView>(PURCHASE_LIST)?.let {
            presenter.saveInitData(it.toMutableList())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_purchase_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.bindView(this)
    }

    override fun configToolbar() {
        controller.configure(
            ToolbarDescriptor.Builder()
                .visibility(true)
                .navigationIcon(R.drawable.ic_arrow_back)
                .title("Закупочный лист")
                .build(),
            activity as ConfigToolbar
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        initSearch(menu, inflater)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initViews() {
        setAdapter()

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

    private fun setAdapter() {
        adapter = PurchaseListItemAdapter(::onItemClicked)
        purchaseListItemsRecyclerView.layoutManager = LinearLayoutManager(purchaseListItemsRecyclerView.context)
        purchaseListItemsRecyclerView.adapter = adapter
    }

    private fun onItemClicked(itemView: PurchaseListItemView) {

    }

    override fun showPurchaseList(items: List<PurchaseListItemView>) {
        adapter.purchaseListItems = items.toMutableList()
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
        private const val PURCHASE_LIST = "PURCHASE_LIST"

        fun getInstance(purchaseList: List<PurchaseListItemView>): PurchaseListFragment =
            PurchaseListFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(PURCHASE_LIST, purchaseList.toArrayList())
                }
            }
    }
}

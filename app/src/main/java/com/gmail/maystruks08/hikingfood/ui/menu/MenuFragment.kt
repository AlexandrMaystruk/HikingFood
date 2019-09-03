package com.gmail.maystruks08.hikingfood.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.maystruks08.domain.entity.Menu
import com.gmail.maystruks08.hikingfood.*
import javax.inject.Inject

class MenuFragment : Fragment(), MenuContract.View {

    @Inject
    lateinit var presenter: MenuContract.Presenter

    @Inject
    lateinit var controller: ToolBarController

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
                .title("Menu")
                .build(),
            activity as ConfigToolbar
        )
    }

    private fun init() {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(t: Throwable) {
    }

    companion object {

        fun getInstance(menu: Menu): MenuFragment =
            MenuFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}


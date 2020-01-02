package com.gmail.maystruks08.hikingfood.core.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.gmail.maystruks08.hikingfood.FragmentToolbar
import com.gmail.maystruks08.hikingfood.ToolbarManager

abstract class BaseFragment : Fragment() {

    var toolbarManager: ToolbarManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarManager =  ToolbarManager(builder(), view).apply { prepareToolbar() }
        initViews()
    }

    protected abstract fun builder(): FragmentToolbar

    protected abstract fun initViews()

    override fun onDestroyView() {
        toolbarManager = null
        super.onDestroyView()
    }
}
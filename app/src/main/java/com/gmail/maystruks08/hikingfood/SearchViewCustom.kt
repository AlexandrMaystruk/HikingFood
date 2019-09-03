package com.gmail.maystruks08.hikingfood

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.search_view.view.*

class SearchViewCustom : FrameLayout {

    interface OnSearchActionListener {
        fun onSearchOpened()
        fun onSearchClosed()
    }

    private var isSearchOpen: Boolean = false
    var isKeyboardVisible: Boolean = false


    lateinit var listener: OnSearchActionListener

    constructor(ctx: Context) : super(ctx) {
        inflate(context, R.layout.search_view, this)
        initView(ctx)
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        inflate(context, R.layout.search_view, this)
        initView(ctx)
    }

    private fun initView(ctx: Context) {
        textSearch.text = ctx.getString(R.string.search)
        parentCustomSearchView.setOnClickListener {
            openSearch()
        }
    }

    private fun openSearch() {
        if (!isSearchOpen) {
            textSearch.text = ""
            isSearchOpen = true
            isKeyboardVisible = true
        } else if (isSearchOpen && !isKeyboardVisible) {
            isKeyboardVisible = true
        }
        listener.onSearchOpened()
        textSearch.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
    }

    fun closeSearch() {
        if (isSearchOpen) {
            textSearch.text = context.getString(R.string.search)
            textSearch.setTextColor(ContextCompat.getColor(context, R.color.colorGrey))
            listener.onSearchClosed()
            isSearchOpen = false
            isKeyboardVisible = false
        }
    }

    fun setOnSearchActionListener(listener: OnSearchActionListener) {
        this.listener = listener
    }

    fun setTexQuery(text: String?) {
        textSearch.text = text
    }

    fun switchToInactiveState() {
        textSearch.setTextColor(ContextCompat.getColor(textSearch.context, R.color.colorGrey))
        if (textSearch.text == "") {
            textSearch.text = textSearch.context.getString(R.string.search)
        }
    }

}
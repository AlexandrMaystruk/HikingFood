package com.gmail.maystruks08.hikingfood.ui.adapter

interface AdapterCallbacks {

    interface OnClickListener<T> {
        fun onClicked(item: T)
    }

    interface OnItemChangeListener<T>{
        fun onItemChanged(changedItem: T)
    }
}
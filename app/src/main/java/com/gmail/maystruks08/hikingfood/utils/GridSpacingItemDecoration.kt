package com.gmail.maystruks08.hikingfood.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(private val space: Int, private val numColumn: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = space
        val position = parent.getChildLayoutPosition(view)

        if (numColumn <= 2) {
            if (position == 0) {
                outRect.left = space
                outRect.right = space / 2
            } else {
                if (position % numColumn != 0) {
                    outRect.left = space / 2
                    outRect.right = space
                } else {
                    outRect.left = space
                    outRect.right = space / 2
                }
            }
        } else {
            if (position == 0) {
                outRect.left = space
                outRect.right = space / 2
            } else {
                when {
                    position % numColumn == 0 -> {
                        outRect.left = space
                        outRect.right = space / 2
                    }
                    position % numColumn == numColumn - 1 -> {
                        outRect.left = space / 2
                        outRect.right = space
                    }
                    else -> {
                        outRect.left = space / 2
                        outRect.right = space / 2
                    }
                }
            }
        }

        if (position < numColumn) {
            outRect.top = space
        } else {
            outRect.top = 0
        }
    }
}
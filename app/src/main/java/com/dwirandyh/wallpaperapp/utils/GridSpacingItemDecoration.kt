package com.dwirandyh.wallpaperapp.utils

import android.R.attr.top
import android.R.attr.right
import android.R.attr.left
import android.R.attr.bottom
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class GridSpacingItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space
        } else {
            outRect.top = 0
        }
    }
}
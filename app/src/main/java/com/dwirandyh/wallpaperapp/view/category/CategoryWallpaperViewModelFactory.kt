package com.dwirandyh.wallpaperapp.view.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class CategoryWallpaperViewModelFactory(
    private val categoryId: Int,
    private val categoryWallpaperDataSource: CategoryWallpaperDataSource
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryWallpaperViewModel(categoryId, categoryWallpaperDataSource) as T
    }
}
package com.dwirandyh.wallpaperapp.view.home.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class PopularFragmentViewModelFactory(
    private val popularWallpaperDataSource: PopularWallpaperDataSource
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PopularFragmentViewModel(popularWallpaperDataSource) as T
    }
}
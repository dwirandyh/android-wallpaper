package com.dwirandyh.wallpaperapp.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dwirandyh.wallpaperapp.data.repository.FavoriteRepository
import com.dwirandyh.wallpaperapp.data.repository.WallpaperRepository
import com.dwirandyh.wallpaperapp.view.category.CategoryWallpaperDataSource

class DetailActivityViewModelFactory(
    private val wallpaperRepository: WallpaperRepository,
    private val favoriteRepository: FavoriteRepository,
    private val categoryWallpaperDataSource: CategoryWallpaperDataSource
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailActivityViewModel(wallpaperRepository, favoriteRepository, categoryWallpaperDataSource) as T
    }
}
package com.dwirandyh.wallpaperapp.view.favorite

import androidx.paging.DataSource
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import com.dwirandyh.wallpaperapp.data.repository.FavoriteRepository
import com.dwirandyh.wallpaperapp.view.home.latest.LatestFragmentViewModel
import com.dwirandyh.wallpaperapp.view.home.latest.LatestWallpaperDataSource


class FavoriteViewModelFactory(
    private val favoriteRepository: FavoriteRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteViewModel(favoriteRepository) as T
    }
}
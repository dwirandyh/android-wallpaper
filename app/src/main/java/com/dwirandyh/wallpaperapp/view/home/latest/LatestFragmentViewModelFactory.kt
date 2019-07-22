package com.dwirandyh.wallpaperapp.view.home.latest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dwirandyh.wallpaperapp.data.repository.WallpaperRepository


class LatestFragmentViewModelFactory(
    private val latestWallpaperDataSource: LatestWallpaperDataSource
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LatestFragmentViewModel(latestWallpaperDataSource) as T
    }
}
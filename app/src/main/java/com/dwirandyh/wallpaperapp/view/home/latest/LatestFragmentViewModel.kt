package com.dwirandyh.wallpaperapp.view.home.latest

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import com.dwirandyh.wallpaperapp.data.repository.WallpaperRepository

class LatestFragmentViewModel(
    private val latestWallpaperDataSource: LatestWallpaperDataSource
) : ViewModel() {

    private var latestWallpaperDataSourceFactory: LatestWallpaperDataSourceFactory = LatestWallpaperDataSourceFactory(latestWallpaperDataSource)
    private var wallpaperLiveData: LiveData<PagedList<Wallpaper>>? = null

    fun initPaging() {
        val config = PagedList.Config.Builder()
            .setPageSize(18)
            .setInitialLoadSizeHint(30)
            .setEnablePlaceholders(false)
            .build()

        latestWallpaperDataSourceFactory = LatestWallpaperDataSourceFactory(latestWallpaperDataSource)
        wallpaperLiveData = LivePagedListBuilder(latestWallpaperDataSourceFactory, config).build()
    }

    fun getLatestWallpaper(): LiveData<PagedList<Wallpaper>>? {
        return wallpaperLiveData
    }

    override fun onCleared() {
        super.onCleared()
        latestWallpaperDataSourceFactory.wallpaperDataSource.clearDisposable()
    }
}
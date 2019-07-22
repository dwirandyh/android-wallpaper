package com.dwirandyh.wallpaperapp.view.home.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper

class PopularFragmentViewModel(
    private val latestWallpaperDataSource: PopularWallpaperDataSource
) : ViewModel() {

    private var popularWallpaperDataSourceFactory: PopularWallpaperDataSourceFactory = PopularWallpaperDataSourceFactory(latestWallpaperDataSource)
    private var wallpaperLiveData: LiveData<PagedList<Wallpaper>>? = null

    fun initPaging() {
        val config = PagedList.Config.Builder()
            .setPageSize(18)
            .setInitialLoadSizeHint(30)
            .setEnablePlaceholders(false)
            .build()

        popularWallpaperDataSourceFactory = PopularWallpaperDataSourceFactory(latestWallpaperDataSource)
        wallpaperLiveData = LivePagedListBuilder(popularWallpaperDataSourceFactory, config).build()
    }

    fun getPopularWallpaper(): LiveData<PagedList<Wallpaper>>? {
        return wallpaperLiveData
    }

    override fun onCleared() {
        super.onCleared()
        popularWallpaperDataSourceFactory.wallpaperDataSource.clearDisposable()
    }
}
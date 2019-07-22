package com.dwirandyh.wallpaperapp.view.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper

class CategoryWallpaperViewModel(
    private val categoryId: Int,
    private val categoryWallpaperDataSource: CategoryWallpaperDataSource
) : ViewModel() {

    private var categoryWallpaperDataSourceFactory: CategoryWallpaperDataSourceFactory? = null
    private var wallpaperLiveData: LiveData<PagedList<Wallpaper>>? = null

    fun initPaging() {
        val config = PagedList.Config.Builder()
            .setPageSize(18)
            .setInitialLoadSizeHint(30)
            .setEnablePlaceholders(false)
            .build()

        categoryWallpaperDataSource.categoryId = categoryId
        categoryWallpaperDataSourceFactory = CategoryWallpaperDataSourceFactory(categoryWallpaperDataSource)
        categoryWallpaperDataSourceFactory?.let {
            wallpaperLiveData = LivePagedListBuilder(it, config).build()
        }
    }

    fun getCategoryWallpaper(): LiveData<PagedList<Wallpaper>>? {
        return wallpaperLiveData
    }

    override fun onCleared() {
        super.onCleared()
        categoryWallpaperDataSourceFactory?.wallpaperDataSource?.clearDisposable()
    }
}
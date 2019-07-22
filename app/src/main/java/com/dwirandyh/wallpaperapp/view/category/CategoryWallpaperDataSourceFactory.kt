package com.dwirandyh.wallpaperapp.view.category

import androidx.paging.DataSource
import androidx.lifecycle.MutableLiveData
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper


class CategoryWallpaperDataSourceFactory(
    val wallpaperDataSource: CategoryWallpaperDataSource
) :
    DataSource.Factory<Long, Wallpaper>() {

    private val mutableLiveData: MutableLiveData<CategoryWallpaperDataSource> = MutableLiveData()

    override fun create(): DataSource<Long, Wallpaper> {
        mutableLiveData.postValue(wallpaperDataSource)
        return wallpaperDataSource
    }

}
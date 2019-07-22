package com.dwirandyh.wallpaperapp.view.home.popular

import androidx.paging.DataSource
import androidx.lifecycle.MutableLiveData
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper


class PopularWallpaperDataSourceFactory(
    val wallpaperDataSource: PopularWallpaperDataSource
) :
    DataSource.Factory<Long, Wallpaper>() {

    private val mutableLiveData: MutableLiveData<PopularWallpaperDataSource> = MutableLiveData()

    override fun create(): DataSource<Long, Wallpaper> {
        mutableLiveData.postValue(wallpaperDataSource)
        return wallpaperDataSource
    }

}
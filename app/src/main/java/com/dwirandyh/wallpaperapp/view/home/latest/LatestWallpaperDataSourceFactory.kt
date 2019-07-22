package com.dwirandyh.wallpaperapp.view.home.latest

import androidx.paging.DataSource
import androidx.lifecycle.MutableLiveData
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper


class LatestWallpaperDataSourceFactory(
    val wallpaperDataSource: LatestWallpaperDataSource
) :
    DataSource.Factory<Long, Wallpaper>() {

    private val mutableLiveData: MutableLiveData<LatestWallpaperDataSource> = MutableLiveData()

    override fun create(): DataSource<Long, Wallpaper> {
        mutableLiveData.postValue(wallpaperDataSource)
        return wallpaperDataSource
    }

}
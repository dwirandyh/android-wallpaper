package com.dwirandyh.wallpaperapp.data.local

import androidx.paging.DataSource
import androidx.paging.PagedList
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import io.reactivex.Observable

interface LocalDataSource {
    fun getLatestWallpaper() : Observable<List<Wallpaper>>

    fun deleteAndCreateLatestWallpaper(wallpapers : List<Wallpaper>)


    fun storeCategories(categories: List<Category>)

    fun getCategories(): DataSource.Factory<Int, Category>


}
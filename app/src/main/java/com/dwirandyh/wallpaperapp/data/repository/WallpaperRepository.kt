package com.dwirandyh.wallpaperapp.data.repository

import androidx.paging.PagedList
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.local.entity.Favorite
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import io.reactivex.Observable
import io.reactivex.Single

interface WallpaperRepository {

    fun getLatestWallpaper(page: Int) : Observable<List<Wallpaper>>

    fun getPopularWallpaper(page: Int) : Observable<List<Wallpaper>>
}
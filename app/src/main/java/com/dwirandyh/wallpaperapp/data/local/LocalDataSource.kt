package com.dwirandyh.wallpaperapp.data.local

import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import io.reactivex.Observable

interface LocalDataSource {
    fun getLatestWallpaper() : Observable<List<Wallpaper>>

    fun deleteAndCreateLatestWallpaper(wallpapers : List<Wallpaper>)
}
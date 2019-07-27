package com.dwirandyh.wallpaperapp.data.local

import com.dwirandyh.wallpaperapp.data.local.Dao.WallpaperDao
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import io.reactivex.Observable

class LocalDataSourceImpl(
    private val wallpaperDao: WallpaperDao
) : LocalDataSource {

    override fun getLatestWallpaper(): Observable<List<Wallpaper>> {
        return wallpaperDao.getLatestWalllpaper()
            .toObservable()
    }

    override fun deleteAndCreateLatestWallpaper(wallpapers: List<Wallpaper>) {
        wallpaperDao.deleteAndCreate(wallpapers)
    }
}
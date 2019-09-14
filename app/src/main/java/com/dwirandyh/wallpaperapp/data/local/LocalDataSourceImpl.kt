package com.dwirandyh.wallpaperapp.data.local

import androidx.paging.DataSource
import com.dwirandyh.wallpaperapp.data.local.dao.CategoryDao
import com.dwirandyh.wallpaperapp.data.local.dao.WallpaperDao
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import io.reactivex.Observable

class LocalDataSourceImpl(
    private val wallpaperDao: WallpaperDao,
    private val categoryDao: CategoryDao
) : LocalDataSource {

    override fun getLatestWallpaper(): Observable<List<Wallpaper>> {
        return wallpaperDao.getLatestWalllpaper()
            .toObservable()
    }

    override fun deleteAndCreateLatestWallpaper(wallpapers: List<Wallpaper>) {
        wallpaperDao.deleteAndCreate(wallpapers)
    }


    override fun storeCategories(categories: List<Category>) {
        categoryDao.insert(categories)
    }

    override fun getCategories(): DataSource.Factory<Int, Category> {
        return categoryDao.getCategories()
    }
    //endregion
}
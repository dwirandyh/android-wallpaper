package com.dwirandyh.wallpaperapp.data.remote

import android.util.Log
import com.dwirandyh.wallpaperapp.data.local.LocalDataSource
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import io.reactivex.Observable
import java.lang.Exception

class RemoteDataSourceImpl(
    private val retrofitInstance: RetrofitInstance,
    private val localDataSource: LocalDataSource
) : RemoteDataSource {
    override fun getLatestWallpaper(page: Int): Observable<List<Wallpaper>> {
        return retrofitInstance.getWallpaperService().getLatestWallpaper(page)
            .flatMap { latestWallpaperResponse ->
                val wallpaperItems = latestWallpaperResponse.wallpaper
                Observable.just(wallpaperItems)
            }
            .doOnNext {
                Log.e("WallpaperRepository", "Save Latest Wallpaper into DB")
                try {
                    if (page == 1) {
                        localDataSource.deleteAndCreateLatestWallpaper(it)
                    }
                } catch (e: Exception) {
                    Log.e("WallpaperRepository", e.message)
                }
            }
    }

    override fun getPopularWallpaper(page: Int): Observable<List<Wallpaper>> {
        return retrofitInstance.getWallpaperService().getPopularWallpaper(page)
            .flatMap {
                val wallpaperItems = it.wallpaper
                Observable.just(wallpaperItems)
            }
    }

    override fun getCategoryById(categoryId: Int): Observable<Category> {
        return retrofitInstance.getWallpaperService().getCategory(categoryId)
    }

    override fun getCategories(page: Int): Observable<List<Category>> {
        return retrofitInstance.getWallpaperService().getCategories(page)
            .flatMap {
                val categories = it.category
                Observable.just(categories)
            }
    }

    override fun getCategoryWallpaper(categoryId: Int, page: Int): Observable<List<Wallpaper>> {
        return retrofitInstance.getWallpaperService().getCategoryWallpaper(categoryId, page)
            .flatMap {
                val wallpaperItems = it.wallpaper
                Observable.just(wallpaperItems)
            }
    }
}
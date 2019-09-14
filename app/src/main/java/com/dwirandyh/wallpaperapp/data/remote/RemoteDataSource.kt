package com.dwirandyh.wallpaperapp.data.remote

import androidx.paging.PagedList
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import io.reactivex.Observable

interface RemoteDataSource {

    fun getLatestWallpaper(page: Int): Observable<List<Wallpaper>>

    fun getPopularWallpaper(page: Int): Observable<List<Wallpaper>>

    //region CATEGORY
    fun getCategoryById(categoryId: Int): Observable<Category>

    fun getCategories(page: Int): Observable<List<Category>>

    fun getCategoryWallpaper(categoryId: Int, page: Int): Observable<List<Wallpaper>>
    //endregion
}
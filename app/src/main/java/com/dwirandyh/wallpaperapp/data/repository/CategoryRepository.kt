package com.dwirandyh.wallpaperapp.data.repository

import androidx.paging.PagedList
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import io.reactivex.Observable

interface CategoryRepository {
    fun getCategoryWallpaper(categoryId: Int, page:Int) : Observable<List<Wallpaper>>

    fun getCategories(page: Int) : Observable<PagedList<Category>>

    fun getCategory(categoryId: Int) : Observable<Category>
}
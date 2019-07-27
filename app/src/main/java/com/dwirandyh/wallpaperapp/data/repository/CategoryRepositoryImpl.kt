package com.dwirandyh.wallpaperapp.data.repository

import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import com.dwirandyh.wallpaperapp.data.remote.RemoteDataSource
import io.reactivex.Observable

class CategoryRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : CategoryRepository {

    override fun getCategories(page: Int): Observable<List<Category>> {
        val observableCategoryFromApi = remoteDataSource.getCategories(page)
        if (page == 1) {

        }
        return observableCategoryFromApi
    }


    override fun getCategoryWallpaper(categoryId: Int, page: Int): Observable<List<Wallpaper>> {
        return remoteDataSource.getCategoryWallpaper(categoryId, page)
    }


    override fun getCategory(categoryId: Int): Observable<Category> {
        return remoteDataSource.getCategoryById(categoryId)
    }
}
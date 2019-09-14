package com.dwirandyh.wallpaperapp.data.repository

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.dwirandyh.wallpaperapp.data.local.LocalDataSource
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import com.dwirandyh.wallpaperapp.data.remote.RemoteDataSource
import com.dwirandyh.wallpaperapp.utils.Constant.Companion.PAGE_SIZE
import io.reactivex.Observable

class CategoryRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CategoryRepository {

//    override fun getCategories(page: Int): Observable<List<Category>> {
//        val observableCategoryFromApi = remoteDataSource.getCategories(page)
//        if (page == 1) {
//
//        }
//        return observableCategoryFromApi
//    }

    override fun getCategories(page: Int): Observable<PagedList<Category>> {
        return RxPagedListBuilder(localDataSource.getCategories(), PAGE_SIZE)
            .setBoundaryCallback(CategoryPageListBoundaryCallback(localDataSource, remoteDataSource))
            .buildObservable()
    }


    override fun getCategoryWallpaper(categoryId: Int, page: Int): Observable<List<Wallpaper>> {
        return remoteDataSource.getCategoryWallpaper(categoryId, page)
    }


    override fun getCategory(categoryId: Int): Observable<Category> {
        return remoteDataSource.getCategoryById(categoryId)
    }
}
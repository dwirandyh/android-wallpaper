package com.dwirandyh.wallpaperapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.dwirandyh.wallpaperapp.data.local.FavoriteDao
import com.dwirandyh.wallpaperapp.data.local.WallpaperDao
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.local.entity.Favorite
import com.dwirandyh.wallpaperapp.data.remote.RetrofitInstance
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import com.dwirandyh.wallpaperapp.utils.Constant.Companion.PAGE_SIZE
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import java.lang.Exception
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class WallpaperRepositoryImpl(
    private val retrofitInstance: RetrofitInstance,
    private val wallpaperDao: WallpaperDao
) : WallpaperRepository {

    override fun getLatestWallpaper(page: Int): Observable<List<Wallpaper>> {
        val observableWallpaperFromApi = getLatestWallpaperFromAPI(page)
        val observableWallpaperFromDB = getLatestWallpaperFromDB()

        if (page == 1) {
            return Observable.mergeDelayError(observableWallpaperFromDB, observableWallpaperFromApi)
                .debounce(500, TimeUnit.MILLISECONDS)
        }
        return observableWallpaperFromApi
    }

    private fun getLatestWallpaperFromAPI(page: Int): Observable<List<Wallpaper>> {
        return retrofitInstance.getWallpaperService().getLatestWallpaper(page)
            .flatMap { latestWallpaperResponse ->
                val wallpaperItems = latestWallpaperResponse.wallpaper
                Observable.just(wallpaperItems)
            }
            .doOnNext {
                Log.e("WallpaperRepository", "Save Latest Wallpaper into DB")
                try {
                    if (page == 1) {
                        wallpaperDao.deleteAndCreate(it)
                    }
                } catch (e: Exception) {
                    Log.e("WallpaperRepository", e.message)
                }
            }
    }


    private fun getLatestWallpaperFromDB(): Observable<List<Wallpaper>> {
        return wallpaperDao.getLatestWalllpaper()
            .toObservable()
    }


    override fun getPopularWallpaper(page: Int): Observable<List<Wallpaper>> {
        return getPopularWallpaperFromAPI(page)
    }

    private fun getPopularWallpaperFromAPI(page: Int): Observable<List<Wallpaper>> {
        return retrofitInstance.getWallpaperService().getPopularWallpaper(page)
            .flatMap {
                val wallpaperItems = it.wallpaper
                Observable.just(wallpaperItems)
            }
    }


    override fun getCategories(page: Int): Observable<List<Category>> {
        val observableCategoryFromApi = getCategoriesFromAPI(page)
        if (page == 1) {

        }
        return observableCategoryFromApi
    }

    private fun getCategoriesFromAPI(page: Int): Observable<List<Category>> {
        return retrofitInstance.getWallpaperService().getCategories(page)
            .flatMap {
                val categories = it.category
                Observable.just(categories)
            }
    }

    override fun getCategoryWallpaper(categoryId: Int, page: Int): Observable<List<Wallpaper>> {
        val observableFromAPI = getCategoryWallpaperFromAPI(categoryId, page)
        if (page == 1){

        }
        return observableFromAPI
    }

    private fun getCategoryWallpaperFromAPI(categoryId: Int, page: Int): Observable<List<Wallpaper>> {
        return retrofitInstance.getWallpaperService().getCategoryWallpaper(categoryId, page)
            .flatMap {
                val wallpaperItems = it.wallpaper
                Observable.just(wallpaperItems)
            }
    }

    override fun getCategory(categoryId: Int): Observable<Category> {
        return retrofitInstance.getWallpaperService().getCategory(categoryId)
    }
}
package com.dwirandyh.wallpaperapp.view.category

import android.app.Application
import androidx.lifecycle.Observer
import androidx.paging.PageKeyedDataSource
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import com.dwirandyh.wallpaperapp.data.repository.WallpaperRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import android.R.id.message
import android.util.Log
import com.dwirandyh.wallpaperapp.data.repository.CategoryRepository


class CategoryWallpaperDataSource(
    private val categoryRepository: CategoryRepository
) : PageKeyedDataSource<Long, Wallpaper>() {

    var categoryId: Int = 0

    private val TAG = "WallpaperDataSource"

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Wallpaper>) {
        val wallpaperDisposable = categoryRepository.getCategoryWallpaper(categoryId, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.isNotEmpty()){
                        callback.onResult(it, null, 2)
                    }
                },
                {
                    Log.e(TAG, it.message)
                }
            )
        compositeDisposable.add(wallpaperDisposable)
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Wallpaper>) {
        val wallpaperDisposable = categoryRepository.getCategoryWallpaper(categoryId, (params.key).toInt())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    callback.onResult(it, params.key + 1)
                },
                {
                    Log.e(TAG, it.message)
                }
            )

        compositeDisposable.add(wallpaperDisposable)
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Wallpaper>) {

    }

    fun clearDisposable() {
        compositeDisposable.clear()
    }

}
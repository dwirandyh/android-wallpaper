package com.dwirandyh.wallpaperapp.view.home.category

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.dwirandyh.wallpaperapp.base.BasePageKeyedDataSource
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.repository.WallpaperRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class CategoryListDataSource(
    private val wallpaperRepository: WallpaperRepository
) : BasePageKeyedDataSource<Category>() {

    private val TAG = "CategoryListDataSource"

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Category>) {
        val categoryDisposable = wallpaperRepository.getCategories(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.isNotEmpty()) {
                        callback.onResult(it, null, 2)
                    }
                },
                {
                    Log.e(TAG, it.message)
                }
            )

        compositeDisposable.add(categoryDisposable)
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Category>) {
        val categoryDisposable = wallpaperRepository.getCategories(params.key.toInt())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.isNotEmpty()){
                        callback.onResult(it, params.key + 1)
                    }
                },
                {
                    Log.e(TAG, it.message)
                }
            )

        compositeDisposable.add(categoryDisposable)
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Category>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
package com.dwirandyh.wallpaperapp.base

import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable

abstract class BasePageKeyedDataSource<T> : PageKeyedDataSource<Long, T>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun clearDisposable() {
        compositeDisposable.clear()
    }
}
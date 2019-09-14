package com.dwirandyh.wallpaperapp.data.repository

import android.util.Log
import androidx.paging.PagedList
import com.dwirandyh.wallpaperapp.data.local.LocalDataSource
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.remote.RemoteDataSource
import io.reactivex.schedulers.Schedulers

class CategoryPageListBoundaryCallback(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : PagedList.BoundaryCallback<Category>()
{
    private var isRequestRunning = false
    private var requestedPage = 1

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        fetchAndStoreData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Category) {
        super.onItemAtEndLoaded(itemAtEnd)
        fetchAndStoreData()
    }

    private fun fetchAndStoreData(){
        if (isRequestRunning) return

        isRequestRunning = true
        val disposable = remoteDataSource.getCategories(requestedPage)
            .doOnNext {
                if (it.isNotEmpty()){
                    localDataSource.storeCategories(it)
                    Log.i(TAG, "Inserted: ${it.size}")
                }else{
                    Log.i(TAG, "No Inserted")
                }
                requestedPage++
            }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .ignoreElements()
            .doFinally {
                isRequestRunning = false
            }
            .subscribe (
                {
                    Log.i(TAG, "Category Loaded")
                },
                {
                    it.printStackTrace()
                }
            )
    }

    companion object {
        private const val TAG: String = "PageListCategoryBound"
    }
}
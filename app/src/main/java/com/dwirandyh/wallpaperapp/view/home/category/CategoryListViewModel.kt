package com.dwirandyh.wallpaperapp.view.home.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import java.util.*

class CategoryListViewModel(
    private val categoryListDataSource: CategoryListDataSource
) : ViewModel() {

    private var categoryListDataSourceFactory = CategoryListDataSourceFactory(categoryListDataSource)

    private var categoriesLiveData: LiveData<PagedList<Category>>? = null

    fun initPaging(){
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(12)
            .setInitialLoadSizeHint(12)
            .setEnablePlaceholders(false)
            .build()

        categoryListDataSourceFactory = CategoryListDataSourceFactory(categoryListDataSource)
        categoriesLiveData = LivePagedListBuilder(categoryListDataSourceFactory, config).build()
    }

    fun getCategories() : LiveData<PagedList<Category>>? {
        return categoriesLiveData
    }

    override fun onCleared() {
        super.onCleared()
        categoryListDataSource.clearDisposable()
    }

}
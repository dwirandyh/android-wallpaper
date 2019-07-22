package com.dwirandyh.wallpaperapp.view.home.category

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dwirandyh.wallpaperapp.data.local.entity.Category


class CategoryListDataSourceFactory (
    private val categoryListDataSource: CategoryListDataSource
) : DataSource.Factory<Long, Category>() {
    private val mutableLiveData: MutableLiveData<CategoryListDataSource> = MutableLiveData()

    override fun create(): DataSource<Long, Category> {
        mutableLiveData.postValue(categoryListDataSource)
        return categoryListDataSource
    }
}

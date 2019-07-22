package com.dwirandyh.wallpaperapp.view.home.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.DataSource
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.view.home.latest.LatestFragmentViewModel


class CategoryListViewModelFactory(
    private val categoryListDataSource: CategoryListDataSource
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryListViewModel(categoryListDataSource) as T
    }
}
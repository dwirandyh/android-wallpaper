package com.dwirandyh.wallpaperapp.view.home.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.repository.CategoryRepository
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class CategoryListViewModel(
//    private val categoryListDataSource: CategoryListDataSource
    private val categoryRepository: CategoryRepository
) : ViewModel() {

//    private var categoryListDataSourceFactory =
//        CategoryListDataSourceFactory(categoryListDataSource)

    private val compositeDisposable = CompositeDisposable()

    var categoriesLiveData: MutableLiveData<PagedList<Category>> = MutableLiveData()

    fun initPaging() {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(12)
            .setInitialLoadSizeHint(12)
            .setEnablePlaceholders(false)
            .build()

//        categoryListDataSourceFactory = CategoryListDataSourceFactory(categoryListDataSource)
//        categoriesLiveData = LivePagedListBuilder(categoryListDataSourceFactory, config).build()
    }

//    fun getCategories(): LiveData<PagedList<Category>>? {
//        return categoriesLiveData
//    }


    fun getCategories() {
        compositeDisposable.add(
            categoryRepository.getCategories(1)
                .subscribe(
                    {
                        categoriesLiveData.value = it
                    },
                    {
                        it.printStackTrace()
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
//        categoryListDataSource.clearDisposable()
        compositeDisposable.dispose()
    }

}
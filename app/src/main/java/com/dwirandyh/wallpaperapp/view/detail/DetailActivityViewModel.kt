package com.dwirandyh.wallpaperapp.view.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.local.entity.Favorite
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import com.dwirandyh.wallpaperapp.data.repository.FavoriteRepository
import com.dwirandyh.wallpaperapp.data.repository.WallpaperRepository
import com.dwirandyh.wallpaperapp.view.category.CategoryWallpaperDataSource
import com.dwirandyh.wallpaperapp.view.category.CategoryWallpaperDataSourceFactory
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailActivityViewModel(
    private val wallpaperRepository: WallpaperRepository,
    private val favoriteRepository: FavoriteRepository,
    private val categoryWallpaperDataSource: CategoryWallpaperDataSource
) : ViewModel() {

    private val _categoryLiveData: MutableLiveData<Category> = MutableLiveData()
    private val categoryLiveData: LiveData<Category>
        get() = _categoryLiveData

    private val _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    private var categoryWallpaperDataSourceFactory: CategoryWallpaperDataSourceFactory? = null
    var similaWallpaperLiveData: LiveData<PagedList<Wallpaper>>? = null

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getCategory(categoryId: Int): LiveData<Category> {
        val observableCategory = wallpaperRepository.getCategory(categoryId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _categoryLiveData.postValue(it)
            }

        compositeDisposable.add(observableCategory)

        return categoryLiveData
    }

    fun addFavorite(favorite: Favorite) {
        compositeDisposable.add(Completable.fromAction {
            favoriteRepository.addFavorite(favorite)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _isFavorite.postValue(true)
                    Log.i("DetailActivityViewModel", "Successfully added into favorite")
                },
                {
                    _isFavorite.postValue(false)
                    Log.e("DetailActivityViewModel", it.message)
                }
            )
        )
    }

    fun removeFavorite(favorite: Favorite) {
        compositeDisposable.add(
            Completable.fromAction {
                favoriteRepository.removeFavorite(favorite)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _isFavorite.postValue(false)
                        Log.i("DetailActivityViewModel", "Remove favorite")
                    },
                    {
                        _isFavorite.postValue(true)
                        Log.i("DetailActivityViewModel", it.message)
                    }
                )
        )
    }

    fun checkFavorite(wallpaperId: Int) {
        val observableFavorite = favoriteRepository.getFavoriteByWallpaperId(wallpaperId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _isFavorite.postValue(true)
                },
                {
                    Log.e("DetailActivityViewModel", it.message)
                },
                {
                    _isFavorite.postValue(false)
                }
            )
        compositeDisposable.add(observableFavorite)
    }



    fun getSimilarWallpaper(categoryId: Int) {
        val config = PagedList.Config.Builder()
            .setPageSize(18)
            .setInitialLoadSizeHint(30)
            .setEnablePlaceholders(false)
            .build()

        categoryWallpaperDataSource.categoryId = categoryId
        categoryWallpaperDataSourceFactory = CategoryWallpaperDataSourceFactory(categoryWallpaperDataSource)
        categoryWallpaperDataSourceFactory?.let {
            similaWallpaperLiveData = LivePagedListBuilder(it, config).build()
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
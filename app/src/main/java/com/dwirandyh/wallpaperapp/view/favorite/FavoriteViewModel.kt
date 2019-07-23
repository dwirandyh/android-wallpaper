package com.dwirandyh.wallpaperapp.view.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.dwirandyh.wallpaperapp.data.local.entity.Favorite
import com.dwirandyh.wallpaperapp.data.repository.FavoriteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _favoriteList: MutableLiveData<List<Favorite>> = MutableLiveData()
    private val favoriteList: LiveData<List<Favorite>>
        get() = _favoriteList

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getFavorites(): LiveData<List<Favorite>>{
        val observableFavorites =  favoriteRepository.getFavorites()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { favorite ->
                _favoriteList.postValue(favorite)
            }
        compositeDisposable.add(observableFavorites)
        return favoriteList
    }
}

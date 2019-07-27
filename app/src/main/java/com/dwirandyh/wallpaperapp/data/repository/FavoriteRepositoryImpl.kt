package com.dwirandyh.wallpaperapp.data.repository

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.dwirandyh.wallpaperapp.data.local.Dao.FavoriteDao
import com.dwirandyh.wallpaperapp.data.local.entity.Favorite
import com.dwirandyh.wallpaperapp.utils.Constant.Companion.PAGE_SIZE
import io.reactivex.Maybe
import io.reactivex.Observable

class FavoriteRepositoryImpl(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {

    override fun addFavorite(favorite: Favorite) {
        favoriteDao.insert(favorite)
    }

    override fun removeFavorite(favorite: Favorite) {
        favoriteDao.delete(favorite.wallpaperId)
    }

    override fun getFavoriteByWallpaperId(wallpaperId: Int): Maybe<Favorite> {
        return favoriteDao.getFavoriteByWallpaperId(wallpaperId)
    }

    override fun getFavorites(): Observable<PagedList<Favorite>> {
        return RxPagedListBuilder(favoriteDao.getFavorites(), PAGE_SIZE)
            .buildObservable()
    }
}
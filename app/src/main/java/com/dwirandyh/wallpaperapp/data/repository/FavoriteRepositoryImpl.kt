package com.dwirandyh.wallpaperapp.data.repository

import com.dwirandyh.wallpaperapp.data.local.FavoriteDao
import com.dwirandyh.wallpaperapp.data.local.entity.Favorite
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

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

    override fun getFavorites(): Single<List<Favorite>> {
        return favoriteDao.getFavorites()
    }
}
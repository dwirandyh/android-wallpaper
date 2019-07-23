package com.dwirandyh.wallpaperapp.data.repository

import com.dwirandyh.wallpaperapp.data.local.entity.Favorite
import io.reactivex.Maybe
import io.reactivex.Single

interface FavoriteRepository {
    fun addFavorite(favorite: Favorite)

    fun removeFavorite(favorite: Favorite)

    fun getFavorites() : Single<List<Favorite>>

    fun getFavoriteByWallpaperId(wallpaperId: Int): Maybe<Favorite>
}
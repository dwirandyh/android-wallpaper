package com.dwirandyh.wallpaperapp.data.repository

import androidx.paging.DataSource
import androidx.paging.PagedList
import com.dwirandyh.wallpaperapp.data.local.entity.Favorite
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface FavoriteRepository {
    fun addFavorite(favorite: Favorite)

    fun removeFavorite(favorite: Favorite)

    fun getFavorites() : Observable<PagedList<Favorite>>

    fun getFavoriteByWallpaperId(wallpaperId: Int): Maybe<Favorite>


}
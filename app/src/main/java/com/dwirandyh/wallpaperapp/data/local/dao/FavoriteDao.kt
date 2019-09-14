package com.dwirandyh.wallpaperapp.data.local.dao

import androidx.paging.DataSource
import androidx.room.*
import com.dwirandyh.wallpaperapp.data.local.entity.Favorite
import io.reactivex.Maybe

@Dao
interface FavoriteDao {

    @Insert
    fun insert(favorite: Favorite)

    @Query("delete from favorites where wallpaperId=:wallpaperId")
    fun delete(wallpaperId: Int)

    @Query("select * from favorites")
    fun getFavorites() : DataSource.Factory<Int, Favorite>

    @Query("select * from favorites where wallpaperId=:wallpaperId")
    fun getFavoriteByWallpaperId(wallpaperId : Int) : Maybe<Favorite>
}
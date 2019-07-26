package com.dwirandyh.wallpaperapp.data.local

import androidx.paging.DataSource
import androidx.room.*
import com.dwirandyh.wallpaperapp.data.local.entity.Favorite
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*

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
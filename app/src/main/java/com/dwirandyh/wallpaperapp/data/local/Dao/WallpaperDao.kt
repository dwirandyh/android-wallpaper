package com.dwirandyh.wallpaperapp.data.local.Dao

import androidx.room.*
import com.dwirandyh.wallpaperapp.data.local.entity.Favorite
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import io.reactivex.Single

@Dao
interface WallpaperDao {

    @Transaction
    fun deleteAndCreate(wallpapers: List<Wallpaper>){
        deleteAll()
        insertAll(wallpapers)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(wallpaper: List<Wallpaper>)

    @Query("delete from wallpapers")
    fun deleteAll()

    @Query("select * from wallpapers")
    fun getLatestWalllpaper() : Single<List<Wallpaper>>
}
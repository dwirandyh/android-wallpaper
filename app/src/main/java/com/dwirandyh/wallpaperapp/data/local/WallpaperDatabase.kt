package com.dwirandyh.wallpaperapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import com.dwirandyh.wallpaperapp.utils.Converters

@Database(
    entities = [Wallpaper::class, Category::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class WallpaperDatabase : RoomDatabase() {

    abstract fun wallpaperDao(): WallpaperDao

    companion object {
        @Volatile
        private var instance: WallpaperDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WallpaperDatabase::class.java, "wallpaper.db"
            ).build()

    }
}
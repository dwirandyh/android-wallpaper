package com.dwirandyh.wallpaperapp.data.repository

import com.dwirandyh.wallpaperapp.data.local.LocalDataSource
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import com.dwirandyh.wallpaperapp.data.remote.RemoteDataSource
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class WallpaperRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : WallpaperRepository {

    override fun getLatestWallpaper(page: Int): Observable<List<Wallpaper>> {
        val observableWallpaperFromApi = remoteDataSource.getLatestWallpaper(page)
        val observableWallpaperFromDB = localDataSource.getLatestWallpaper()

        if (page == 1) {
            return Observable.mergeDelayError(observableWallpaperFromDB, observableWallpaperFromApi)
                .debounce(500, TimeUnit.MILLISECONDS)
        }
        return observableWallpaperFromApi
    }


    override fun getPopularWallpaper(page: Int): Observable<List<Wallpaper>> {
        return remoteDataSource.getPopularWallpaper(page)
    }


}
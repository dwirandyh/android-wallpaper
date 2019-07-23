package com.dwirandyh.wallpaperapp.data.remote

import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.remote.response.CategoryResponse
import com.dwirandyh.wallpaperapp.data.remote.response.CategoryWallpaperResponse
import com.dwirandyh.wallpaperapp.data.remote.response.LatestWallpaperResponse
import com.dwirandyh.wallpaperapp.data.remote.response.PopularWallpaperResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WallpaperAPIService {
    @GET("wallpaper")
    fun getLatestWallpaper(@Query("page") page: Int): Observable<LatestWallpaperResponse>

    @GET("popular")
    fun getPopularWallpaper(@Query("page") page: Int): Observable<PopularWallpaperResponse>

    @GET("category/{id}/wallpaper")
    fun getCategoryWallpaper(@Path("id") categoryId: Int, @Query("page") page: Int) : Observable<CategoryWallpaperResponse>

    @GET("category/{id}")
    fun getCategory(@Path("id") categoryId: Int) : Observable<Category>

    @GET("category")
    fun getCategories(@Query("page") page: Int): Observable<CategoryResponse>
}
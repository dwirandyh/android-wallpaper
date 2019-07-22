package com.dwirandyh.wallpaperapp.data.remote.response


import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import com.google.gson.annotations.SerializedName

data class PopularWallpaperResponse(
    @SerializedName("data")
    val wallpaper: List<Wallpaper>,
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("first_page_url")
    val firstPageUrl: String,
    val from: Int,
    @SerializedName("last_page")
    val lastPage: Int,
    @SerializedName("last_page_url")
    val lastPageUrl: String,
    @SerializedName("next_page_url")
    val nextPageUrl: String,
    val path: String,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("prev_page_url")
    val prevPageUrl: Any?,
    val to: Int,
    val total: Int
)
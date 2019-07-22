package com.dwirandyh.wallpaperapp.data.remote.response


import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("data")
    val category: List<Category>,
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
    val nextPageUrl: Any?,
    val path: String,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("prev_page_url")
    val prevPageUrl: Any?,
    val to: Int,
    val total: Int
)
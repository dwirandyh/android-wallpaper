package com.dwirandyh.wallpaperapp.data.remote

import com.dwirandyh.wallpaperapp.utils.Constant
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


class RetrofitInstance {

    private var retrofit: Retrofit

    init
    {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
        retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }



    fun getWallpaperService(): WallpaperAPIService {
        return retrofit.create(WallpaperAPIService::class.java)
    }
}
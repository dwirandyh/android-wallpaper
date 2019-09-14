package com.dwirandyh.wallpaperapp.utils

class Constant {
    companion object {
        private const val BASE_URL = "http://192.168.1.39:8000"
        const val BASE_API_URL = "${BASE_URL}/v1/"
        const val BASE_IMAGE_URL = "${BASE_URL}/images/wallpaper/"
        const val BASE_THUMBNAIL_URL = "${BASE_URL}/images/thumbnail/"


        const val PAGE_SIZE = 20


        const val EXTRA_WALLPAPER = "EXTRA_WALLPAPER"
        const val EXTERNAL_WALLPAPER_DIR = "/Wallpaper/"


        const val INTERSTITIAL_ID = "ca-app-pub-3940256099942544/1033173712"


        const val MY_EMAIL = "dwirandyherdinanto@gmail.com"
    }
}
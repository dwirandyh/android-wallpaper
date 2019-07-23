package com.dwirandyh.wallpaperapp.utils

import android.content.Context
import android.util.Log
import java.lang.Exception

class AppUtils {
    companion object {
        fun getVersionName(context: Context) : String {
            var version = ""
            try{
                val info = context.packageManager.getPackageInfo(context.packageName, 0)
                version = info.versionName
            }catch (e: Exception){
                Log.e("AppUtils", e.message)
            }
            return version
        }
    }
}
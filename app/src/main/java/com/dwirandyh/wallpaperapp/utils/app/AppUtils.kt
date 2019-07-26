package com.dwirandyh.wallpaperapp.utils.app

import android.app.Activity
import android.content.Context
import android.util.Log
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
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

        fun writeExternalPermission(activity: Activity, callback:() -> Unit){
            Dexter.withActivity(activity)
                .withPermissions(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {
                            if (report.areAllPermissionsGranted()) {
                               callback()
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }).check()
        }
    }
}
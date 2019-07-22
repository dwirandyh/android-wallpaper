package com.dwirandyh.wallpaperapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.net.URL
import java.net.URLDecoder
import android.webkit.MimeTypeMap



class ImageUtils {
    companion object {
        fun saveIntoStorage(imageUrl: String): File? {
            var file: File? = null
            try {
                val imageFileUrl = URL(imageUrl)

                val conn = imageFileUrl.openConnection()
                conn.doInput = true
                conn.connect()
                val inputStream = conn.getInputStream()
                val bitmapImage = BitmapFactory.decodeStream(inputStream)


                val path = imageFileUrl.path
                val idStr = URLDecoder.decode(path.substring(path.lastIndexOf('/') + 1), "UTF-8")

                val externalStoragePath = Environment.getExternalStorageDirectory()
                val externalDir =
                    File(externalStoragePath.absolutePath + Constant.EXTERNAL_WALLPAPER_DIR)
                externalDir.mkdirs()
                file = File(externalDir, idStr)

                val fos = FileOutputStream(file)
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
            } catch (e: Exception) {
                Log.e("GalleryActivity", e.message)
            }
            return file
        }

        fun getMimeType(file: File): String {
            var type: String? = null
            val url = file.toString()
            val extension = MimeTypeMap.getFileExtensionFromUrl(url)
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase())
            }
            if (type == null) {
                type = "image/*"
            }
            return type
        }
    }
}
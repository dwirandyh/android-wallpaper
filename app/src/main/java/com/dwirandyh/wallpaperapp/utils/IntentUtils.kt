package com.dwirandyh.wallpaperapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.dwirandyh.wallpaperapp.BuildConfig
import java.io.File
import java.lang.Exception

class IntentUtils {
    companion object {
        fun setAs(context: Context, file: File?) {
            try {
                file?.let {
                    val setAsIntent = Intent(Intent.ACTION_ATTACH_DATA)
                    val imageUri = FileProvider.getUriForFile(
                        context,
                        BuildConfig.APPLICATION_ID + ".provider",
                        file
                    )

                    setAsIntent.setDataAndType(imageUri, "image/*")
                    setAsIntent.putExtra("mimeType", "image/*")
                    setAsIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                    setAsIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    setAsIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    context.startActivity(Intent.createChooser(setAsIntent, "Set as:"))
                }
            } catch (e: Exception) {
                Log.e("GalleryActivity", e.message)
            }
        }

        fun shareImage(context: Context, file: File?, message: String) {
            file?.let {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = ImageUtils.getMimeType(it)

                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://${file.absolutePath}"))
                shareIntent.putExtra(Intent.EXTRA_TEXT, message)
                shareIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                context.startActivity(Intent.createChooser(shareIntent, "Share Wallpaper"))
            }
        }
    }
}
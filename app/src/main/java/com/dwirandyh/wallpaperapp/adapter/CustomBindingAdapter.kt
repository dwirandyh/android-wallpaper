package com.dwirandyh.wallpaperapp.adapter

import android.os.Build
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dwirandyh.wallpaperapp.R
import com.dwirandyh.wallpaperapp.utils.Constant

@BindingAdapter("htmlText")
fun htmlText(textView: TextView, text: String?) {
    if (text != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        } else {
            textView.text = Html.fromHtml(text)
        }
    }
}

@BindingAdapter("wallpaperPath")
fun wallpaperPath(imageView: ImageView, fileName: String) {
    val imageUrl = Constant.BASE_IMAGE_URL + fileName
    Glide.with(imageView.context)
        .load(imageUrl)
        .thumbnail(0.9f)
        .centerCrop()
        .error(R.drawable.ic_broken_image)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imageView)
}

@BindingAdapter("thumbnailPath")
fun thumbnailPath(imageView: ImageView, fileName: String) {
    val imageUrl = Constant.BASE_THUMBNAIl_URL + fileName
    Glide.with(imageView.context)
        .load(imageUrl)
        .thumbnail(0.9f)
        .centerCrop()
        .error(R.drawable.ic_broken_image)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imageView)
}
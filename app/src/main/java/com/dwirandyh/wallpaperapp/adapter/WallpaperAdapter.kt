package com.dwirandyh.wallpaperapp.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import com.dwirandyh.wallpaperapp.R
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import com.dwirandyh.wallpaperapp.databinding.ItemWallpaperBinding
import com.dwirandyh.wallpaperapp.utils.Constant
import com.dwirandyh.wallpaperapp.view.detail.GalleryActivity


class WallpaperAdapter : PagedListAdapter<Wallpaper, WallpaperAdapter.WallpaperViewHolder>(Wallpaper.CALLBACK) {

    var adapterOnClick: ((View, Wallpaper) -> Unit)? = null

    fun setOnClickListener(onClick: (View, Wallpaper) -> Unit) {
        adapterOnClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val itemWallpaperBinding = ItemWallpaperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WallpaperViewHolder(itemWallpaperBinding)
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    inner class WallpaperViewHolder(private val itemWallpaperBinding: ItemWallpaperBinding) :
        RecyclerView.ViewHolder(itemWallpaperBinding.root) {
        fun bind(item: Wallpaper) {
            itemWallpaperBinding.root.setOnClickListener { view ->
                if (adapterOnClick != null) {
                    adapterOnClick?.invoke(view, item)
                } else {
                    val intent = Intent(view.context, GalleryActivity::class.java)
                    intent.putExtra(Constant.EXTRA_WALLPEPR, item)
                    view.context.startActivity(intent)
                }
            }
            itemWallpaperBinding.apply {
                wallpaper = item
            }

        }
    }
}
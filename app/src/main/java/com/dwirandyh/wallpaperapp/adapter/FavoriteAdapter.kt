package com.dwirandyh.wallpaperapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dwirandyh.wallpaperapp.data.local.entity.Favorite
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import com.dwirandyh.wallpaperapp.databinding.ItemFavoriteBinding
import com.dwirandyh.wallpaperapp.utils.Constant
import com.dwirandyh.wallpaperapp.view.detail.DetailActivity

class FavoriteAdapter : PagedListAdapter<Favorite, FavoriteAdapter.FavoriteViewHolder>(Favorite.CALLBACK) {

    var adapterOnClick: ((View, Favorite) -> Unit)? = null

    fun setOnClickListener(onClick: (View, Favorite) -> Unit) {
        adapterOnClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemWallpaperBinding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(itemWallpaperBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    inner class FavoriteViewHolder(private val itemFavoriteBinding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(itemFavoriteBinding.root) {
        fun bind(item: Favorite) {
            itemFavoriteBinding.root.setOnClickListener { view ->
                if (adapterOnClick != null) {
                    adapterOnClick?.invoke(view, item)
                } else {
                    val intent = Intent(view.context, DetailActivity::class.java)
                    val wallpaper = Wallpaper(item.wallpaperId, item.categoryId, item.createdAt, item.downloads, item.fileName, item.fileSize, item.resolution, null, item.views)
                    intent.putExtra(Constant.EXTRA_WALLPAPER, wallpaper)
                    view.context.startActivity(intent)
                }
            }
            itemFavoriteBinding.apply {
                favorite = item
            }

        }
    }
}
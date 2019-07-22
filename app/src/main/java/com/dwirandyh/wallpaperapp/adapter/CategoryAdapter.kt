package com.dwirandyh.wallpaperapp.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import com.dwirandyh.wallpaperapp.databinding.ItemCategoryBinding
import com.dwirandyh.wallpaperapp.databinding.ItemWallpaperBinding


class CategoryAdapter : PagedListAdapter<Category, CategoryAdapter.CategoryViewHolder>(Category.CALLBACK) {

    var adapterOnClick : ((View, Category) -> Unit)? = null

    fun setOnClickListener(onClick: (View, Category) -> Unit){
        adapterOnClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemCategoryBinding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemCategoryBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    inner class CategoryViewHolder(private val itemCategoryBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemCategoryBinding.root) {
        fun bind(item: Category) {
            itemCategoryBinding.root.setOnClickListener{view ->
                adapterOnClick?.let {
                    it(view, item)
                }
            }
            itemCategoryBinding.apply {
                category = item
            }
        }
    }

}
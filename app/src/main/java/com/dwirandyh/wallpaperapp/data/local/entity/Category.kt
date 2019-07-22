package com.dwirandyh.wallpaperapp.data.local.entity


import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(
    tableName = "categories"
)
data class Category(
    @PrimaryKey
    val id: Int,
    @SerializedName("created_at")
    val createdAt: String,
    val name: String,
    val thumbnail: String,
    @SerializedName("updated_at")
    val updatedAt: Date?,
    @SerializedName("wallpaper_count")
    val wallpaperCount: Int
){
    companion object{
        val CALLBACK: DiffUtil.ItemCallback<Category> = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return true
            }
        }
    }
}
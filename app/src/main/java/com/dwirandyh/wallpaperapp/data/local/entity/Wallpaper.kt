package com.dwirandyh.wallpaperapp.data.local.entity


import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*
import android.graphics.Movie
import android.R.id
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Entity(
    tableName = "wallpapers"
)
@Parcelize
data class Wallpaper(
    @PrimaryKey
    val id: Int,

    @SerializedName("category_id")
    val categoryId: Int,

    @SerializedName("created_at")
    val createdAt: Date,

    val downloads: Int,

    @SerializedName("file_name")
    var fileName: String,

    @SerializedName("file_size")
    val fileSize: Int,

    val resolution: String,

    @SerializedName("updated_at")
    val updatedAt: Date?,

    val views: Int
) : Parcelable
{
    companion object{
        val CALLBACK: DiffUtil.ItemCallback<Wallpaper> = object : DiffUtil.ItemCallback<Wallpaper>() {
            override fun areItemsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
                return true
            }
        }
    }
}
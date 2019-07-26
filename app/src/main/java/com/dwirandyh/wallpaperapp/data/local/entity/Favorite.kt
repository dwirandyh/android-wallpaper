package com.dwirandyh.wallpaperapp.data.local.entity


import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Entity(
    tableName = "favorites"
)
@Parcelize
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    var wallpaperId: Int,
    var categoryId: Int,
    var createdAt: Date,
    var downloads: Int,
    var fileName: String,
    var fileSize: Int,
    var resolution: String,
    var views: Int
) : Parcelable
{
    companion object{
        val CALLBACK: DiffUtil.ItemCallback<Favorite> = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return true
            }
        }
    }
}
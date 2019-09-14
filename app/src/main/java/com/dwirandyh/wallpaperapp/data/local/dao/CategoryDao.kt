package com.dwirandyh.wallpaperapp.data.local.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dwirandyh.wallpaperapp.data.local.entity.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categories: List<Category>)

    @Query("delete from categories where id=:id")
    fun delete(id: Int)

    @Query("select * from categories order by name asc")
    fun getCategories() : DataSource.Factory<Int, Category>
}
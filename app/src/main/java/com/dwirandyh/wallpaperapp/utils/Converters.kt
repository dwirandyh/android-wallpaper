package com.dwirandyh.wallpaperapp.utils

import androidx.room.TypeConverter
import java.util.*


object Converters {
    @TypeConverter
    @JvmStatic // to tell java this object is static
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): Long? {
        return (date?.time)
}
}
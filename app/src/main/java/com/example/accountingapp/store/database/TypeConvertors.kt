package com.example.accountingapp.store.database

import androidx.room.TypeConverter
import java.util.*

class Convertors {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

}
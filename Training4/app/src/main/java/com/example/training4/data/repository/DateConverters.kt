package com.example.training4.data.repository

import androidx.room.TypeConverter

class DateConverters {
    @TypeConverter
    fun fromList(value: List<String>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toList(value: String): List<String> {
        return if (value.isBlank()) emptyList() else value.split(",")
    }
}

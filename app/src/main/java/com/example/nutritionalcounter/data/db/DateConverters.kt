package com.example.nutritionalcounter.data.db

import androidx.room.TypeConverter
import java.time.LocalDate

class DateConverters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.toString() // "YYYY-MM-DD"
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it) }
    }
}
package com.example.dsr_practice.data.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.dsr_practice.data.database.entity.DailyEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@ProvidedTypeConverter
class DailyConverter {
    @TypeConverter
    fun fromDaily(value: List<DailyEntity>): String = Gson().toJson(value)

    @TypeConverter
    fun toDaily(value: String): List<DailyEntity> =
        Gson().fromJson(value, object : TypeToken<List<DailyEntity>>() {}.type)
}
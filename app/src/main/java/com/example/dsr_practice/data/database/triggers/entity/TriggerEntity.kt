package com.example.dsr_practice.data.database.triggers.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TriggerEntity.TABLE_NAME)
data class TriggerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_COLUMN)
    val id: Int = 0,
    @ColumnInfo(name = NAME_COLUMN)
    val name: String? = null,
    @ColumnInfo(name = TEMP_COLUMN)
    val temp: Double? = null,
    @ColumnInfo(name = WIND_SPEED_COLUMN)
    val windSpeed: Double? = null,
    @ColumnInfo(name = HUMIDITY_COLUMN)
    val humidity: Double? = null,
    @ColumnInfo(name = PRESSURE_COLUMN)
    val pressure: Double? = null,
    @ColumnInfo(name = LOCATION_ID)
    val locationId: Int = 0,
    @ColumnInfo(name = LOCATION_NAME)
    val locationName: String? = null,
    @ColumnInfo(name = UNITS_COLUMN)
    val units: String? = null,
) {
    companion object {
        const val TABLE_NAME = "triggers_table"
        const val ID_COLUMN = "id"
        const val NAME_COLUMN = "name"
        const val TEMP_COLUMN = "temp"
        const val WIND_SPEED_COLUMN = "wind_speed"
        const val HUMIDITY_COLUMN = "humidity"
        const val PRESSURE_COLUMN = "pressure"
        const val LOCATION_ID = "location_id"
        const val LOCATION_NAME = "location_name"
        const val UNITS_COLUMN = "units"
    }
}
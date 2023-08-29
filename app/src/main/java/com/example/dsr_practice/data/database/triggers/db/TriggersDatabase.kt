package com.example.dsr_practice.data.database.triggers.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dsr_practice.data.database.triggers.dao.TriggersDao
import com.example.dsr_practice.data.database.triggers.entity.TriggerEntity

@Database(
    entities = [TriggerEntity::class],
    version = TriggersDatabase.DATABASE_VERSION,
    exportSchema = false
)
abstract class TriggersDatabase : RoomDatabase() {
    abstract fun triggersDao(): TriggersDao

    companion object {
        const val DATABASE_NAME = "triggers_database"
        const val DATABASE_VERSION = 3
    }
}
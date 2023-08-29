package com.example.dsr_practice.di

import android.content.Context
import androidx.room.Room
import com.example.dsr_practice.data.database.triggers.db.TriggersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TriggersDatabaseModule {
    @Provides
    @Singleton
    fun provideTriggersDatabase(
        @ApplicationContext context: Context,
    ): TriggersDatabase =
        Room.databaseBuilder(
            context,
            TriggersDatabase::class.java,
            TriggersDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideTriggersDao(triggersDatabase: TriggersDatabase) = triggersDatabase.triggersDao()
}
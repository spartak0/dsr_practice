package com.example.dsr_practice.di

import android.content.Context
import androidx.room.Room
import com.example.dsr_practice.data.database.weather.converter.DailyConverter
import com.example.dsr_practice.data.database.weather.db.WeatherDatabase
import com.example.dsr_practice.domain.UserPrefHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherDatabaseModule {

    @Provides
    @Singleton
    fun provideDailyConverter(): DailyConverter = DailyConverter()

    @Provides
    @Singleton
    fun provideWeatherDatabase(
        @ApplicationContext context: Context,
        dailyConverter: DailyConverter
    ): WeatherDatabase =
        Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            WeatherDatabase.DATABASE_NAME
        ).addTypeConverter(dailyConverter).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDatabase: WeatherDatabase) = weatherDatabase.weatherDao()

    @Provides
    @Singleton
    fun provideUserPrefHelper(
        @ApplicationContext context: Context
    ): UserPrefHelper = UserPrefHelper(context)
}
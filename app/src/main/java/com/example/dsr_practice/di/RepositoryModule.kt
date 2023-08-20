package com.example.dsr_practice.di

import com.example.dsr_practice.data.database.dao.WeatherDao
import com.example.dsr_practice.data.network.api.PlacesApi
import com.example.dsr_practice.data.network.api.WeatherApi
import com.example.dsr_practice.data.repository.PlaceRepositoryImpl
import com.example.dsr_practice.data.repository.WeatherRepositoryImpl
import com.example.dsr_practice.domain.repository.PlaceRepository
import com.example.dsr_practice.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module

@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: WeatherApi,
        dao: WeatherDao,
    ): WeatherRepository = WeatherRepositoryImpl(dao, api)

    @Provides
    @Singleton
    fun providePlacesRepository(
        api: PlacesApi,
    ): PlaceRepository = PlaceRepositoryImpl(api)

}
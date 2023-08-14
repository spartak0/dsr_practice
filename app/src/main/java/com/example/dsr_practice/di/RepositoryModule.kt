package com.example.dsr_practice.di

import com.example.dsr_practice.data.database.dao.WeatherDao
import com.example.dsr_practice.data.network.api.RetrofitApi
import com.example.dsr_practice.data.repository.WeatherRepositoryImpl
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
        api: RetrofitApi,
        dao: WeatherDao,
    ): WeatherRepository = WeatherRepositoryImpl(dao, api)

}
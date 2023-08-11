package com.example.dsr_practice.di

import com.example.dsr_practice.data.database.dao.WeatherDao
import com.example.dsr_practice.data.network.api.RetrofitApi
import com.example.dsr_practice.data.repository.DatabaseRepositoryImpl
import com.example.dsr_practice.data.repository.NetworkRepositoryImpl
import com.example.dsr_practice.domain.repository.DatabaseRepository
import com.example.dsr_practice.domain.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositryModule {

    @Provides
    @Singleton
    fun provideNetworkRepository(
        api: RetrofitApi,
    ): NetworkRepository = NetworkRepositoryImpl(api)


    @Provides
    @Singleton
    fun provideDatabaseRepository(
        weatherDao: WeatherDao
    ): DatabaseRepository =
        DatabaseRepositoryImpl(weatherDao = weatherDao)
}
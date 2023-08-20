package com.example.dsr_practice.di

import com.example.dsr_practice.data.network.api.PlacesApi
import com.example.dsr_practice.data.network.api.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit.Builder): WeatherApi =
        retrofit.baseUrl(WeatherApi.BASE_URL).build().create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun providePlacesApi(retrofit: Retrofit.Builder): PlacesApi =
        retrofit.baseUrl(PlacesApi.BASE_URL).build().create(PlacesApi::class.java)
}
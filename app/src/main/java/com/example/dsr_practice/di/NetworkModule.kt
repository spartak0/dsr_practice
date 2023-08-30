package com.example.dsr_practice.di

import android.content.Context
import com.example.dsr_practice.data.network.api.PlacesApi
import com.example.dsr_practice.data.network.api.WeatherApi
import com.example.dsr_practice.utils.Constants
import com.example.dsr_practice.utils.NetworkConnectionManager
import com.google.maps.GeoApiContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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

    @Provides
    @Singleton
    fun provideGeoContext(): GeoApiContext = GeoApiContext.Builder()
        .apiKey(Constants.MAPS_API_KEY)
        .build()

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectionManager(
        @ApplicationContext context: Context,
        coroutineScope: CoroutineScope,
    ): NetworkConnectionManager =
        NetworkConnectionManager(context, coroutineScope)
}
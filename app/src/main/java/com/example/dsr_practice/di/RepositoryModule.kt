package com.example.dsr_practice.di

import com.example.dsr_practice.data.database.triggers.dao.TriggersDao
import com.example.dsr_practice.data.database.weather.dao.WeatherDao
import com.example.dsr_practice.data.network.api.PlacesApi
import com.example.dsr_practice.data.network.api.WeatherApi
import com.example.dsr_practice.data.repository.PlaceRepositoryImpl
import com.example.dsr_practice.data.repository.TriggersRepositoryImpl
import com.example.dsr_practice.data.repository.UserRepositoryImpl
import com.example.dsr_practice.data.repository.WeatherRepositoryImpl
import com.example.dsr_practice.domain.UserPrefHelper
import com.example.dsr_practice.domain.repository.PlaceRepository
import com.example.dsr_practice.domain.repository.TriggersRepository
import com.example.dsr_practice.domain.repository.UserRepository
import com.example.dsr_practice.domain.repository.WeatherRepository
import com.google.maps.GeoApiContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideUserRepository(
        userPrefHelper: UserPrefHelper,
    ): UserRepository = UserRepositoryImpl(userPrefHelper)

    @Provides
    fun provideWeatherRepository(
        api: WeatherApi,
        dao: WeatherDao,
        userPrefHelper: UserPrefHelper,
    ): WeatherRepository = WeatherRepositoryImpl(dao, api, userPrefHelper)

    @Provides
    fun providePlacesRepository(
        api: PlacesApi,
        geoApiContext: GeoApiContext
    ): PlaceRepository = PlaceRepositoryImpl(api, geoApiContext)

    @Provides
    fun provideTriggersRepository(
        dao: TriggersDao,
    ): TriggersRepository = TriggersRepositoryImpl(dao)

}
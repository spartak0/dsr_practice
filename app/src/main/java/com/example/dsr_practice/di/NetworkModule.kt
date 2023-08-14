package com.example.dsr_practice.di

import com.example.dsr_practice.data.network.api.RetrofitApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder().addNetworkInterceptor { chain ->
            val originalRequest = chain.request()
            val newHttpUrl = originalRequest.url.newBuilder()
                .addQueryParameter("appid", RetrofitApi.API_KEY)
                .addQueryParameter("units", "metric")
                .addQueryParameter("exclude", "minutely,hourly")
                .build()
            val newRequest = originalRequest.newBuilder()
                .url(newHttpUrl)
                .build()
            chain.proceed(newRequest)
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(RetrofitApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): RetrofitApi = retrofit.create(RetrofitApi::class.java)
}
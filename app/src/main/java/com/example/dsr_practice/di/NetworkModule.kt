package com.example.dsr_practice.di

import com.example.dsr_practice.data.network.api.RetrofitApi
import com.example.dsr_practice.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder().addNetworkInterceptor { chain ->
            val request: Request.Builder = chain.request().newBuilder()
            val originalHttpUrl = chain.request().url()
            val newUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("appid", Constants.API_KEY)
                .addQueryParameter("units", "metric")
                .addQueryParameter("exclude", "current, alerts").build()
            request.url(newUrl)
            chain.proceed(request.build())
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(RetrofitApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): RetrofitApi = retrofit.create(RetrofitApi::class.java)
}
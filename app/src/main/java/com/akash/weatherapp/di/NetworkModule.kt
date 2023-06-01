package com.akash.weatherapp.di

import android.content.Context
import com.akash.weatherapp.db.WeatherAppDatabase
import com.akash.weatherapp.repository.AppRepository
import com.akash.weatherapp.retrofit.ApiService
import com.akash.weatherapp.retrofit.NetworkConnectionInterceptor
import com.akash.weatherapp.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
Network module class.
It handles the local db and api class.
*/
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideOKHttpClient(networkInterceptor: NetworkConnectionInterceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder()
            .addInterceptor(networkInterceptor).addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun providesNetworkConnectionInterceptor(context: Context) =
        NetworkConnectionInterceptor(context)

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideAppRepository(apiService: ApiService,weatherAppDatabase: WeatherAppDatabase) =
        AppRepository(apiService,weatherAppDatabase)

    @Singleton
    @Provides
    fun providesAppDatabase(context: Context) =
        WeatherAppDatabase(context)
}

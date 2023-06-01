package com.akash.weatherapp.retrofit

import com.akash.weatherapp.model.WeatherResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getWeatherDetails(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("appid") apiId: String
    ): WeatherResponseModel

    @GET("weather")
    suspend fun getWeatherDetailsByLatLong(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String,
        @Query("appid") apiId: String
    ): WeatherResponseModel
}
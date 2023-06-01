package com.akash.weatherapp.repository

import com.akash.weatherapp.db.WeatherAppDatabase
import com.akash.weatherapp.model.WeatherEntity
import com.akash.weatherapp.model.WeatherResponseModel
import com.akash.weatherapp.retrofit.ApiService
import com.akash.weatherapp.utils.Constants
import javax.inject.Inject

/*
App repository
Handles all the api and db calls.
 */
open class AppRepository @Inject constructor(
    private val apiService: ApiService,
    private val weatherAppDatabase: WeatherAppDatabase
) {
    // Fetch weather data from api by city
    suspend fun getWeatherDetailsByCity(city: String): WeatherResponseModel {
        return apiService.getWeatherDetails(city, "Metric", Constants.API_KEY)
    }

    // Fetch weather data from api by lat long
    suspend fun getWeatherDetailsByLatLon(lat: String, lon: String): WeatherResponseModel {
        return apiService.getWeatherDetailsByLatLong(lat, lon, "Metric", Constants.API_KEY)
    }

    // Add weather data into local db
    suspend fun addWeather(weatherEntity: WeatherEntity) {
        weatherAppDatabase.getWeatherDao().addWeather(weatherEntity)
    }

    // Fetch weather data from local db
    suspend fun fetchAllWeatherData(): List<WeatherEntity> =
        weatherAppDatabase.getWeatherDao().fetchAllWeatherData()
}
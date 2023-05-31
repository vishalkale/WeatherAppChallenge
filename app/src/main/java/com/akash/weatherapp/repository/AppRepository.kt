package com.akash.weatherapp.repository

import com.akash.weatherapp.model.WeatherResponseModel
import com.akash.weatherapp.retrofit.ApiService
import com.akash.weatherapp.utils.Constants
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getWeatherDetailsByCity(city: String): WeatherResponseModel {
        return apiService.getWeatherDetails(city,Constants.API_KEY)
    }
}
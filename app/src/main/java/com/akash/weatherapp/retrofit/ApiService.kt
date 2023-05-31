package com.akash.weatherapp.retrofit

import com.akash.weatherapp.model.WeatherResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
     @GET("weather")
     suspend fun getWeatherDetails(@Query("q") city: String,
                                   @Query("appid") apiId:String): WeatherResponseModel

}
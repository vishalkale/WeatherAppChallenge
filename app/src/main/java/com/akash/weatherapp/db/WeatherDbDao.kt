package com.akash.weatherapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akash.weatherapp.model.WeatherEntity
/*
Weather app dao class for db.
Add weather data into db.
Fetch weather data from db
*/
@Dao
interface WeatherDbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeather(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM weather_entity")
    suspend fun fetchAllWeatherData(): List<WeatherEntity>

}

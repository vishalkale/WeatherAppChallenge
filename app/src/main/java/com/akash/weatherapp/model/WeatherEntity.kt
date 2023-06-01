package com.akash.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Weather entity
 */
@Entity(tableName = "weather_entity")
data class WeatherEntity(
    @PrimaryKey
    var id: Int? = 0,
    var temp: Double? = null,
    var icon: String? = null,
    var cityName: String? = null,
    var countryName: String? = null,
    var weather:String?=null,
    var dateTime: String? = null
)
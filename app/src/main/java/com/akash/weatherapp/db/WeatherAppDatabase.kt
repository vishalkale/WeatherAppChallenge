package com.akash.weatherapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.akash.weatherapp.model.WeatherEntity

/*
Weather app database class.
Invoke and build the database.
*/

@Database(
    entities = [WeatherEntity::class],
    version = 1
)
abstract class WeatherAppDatabase : RoomDatabase() {

    abstract fun getWeatherDao(): WeatherDbDao

    companion object {
        private const val DB_NAME = "weather_db"

        @Volatile
        private var weatherAppDatabase: WeatherAppDatabase? = null

        operator fun invoke(context: Context) = weatherAppDatabase ?: synchronized(this) {
            weatherAppDatabase ?: buildDatabase(context).also {
                weatherAppDatabase = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WeatherAppDatabase::class.java,
                DB_NAME
            ).build()
    }
}

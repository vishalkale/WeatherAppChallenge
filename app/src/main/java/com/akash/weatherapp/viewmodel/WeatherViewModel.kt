package com.akash.weatherapp.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.weatherapp.model.WeatherEntity
import com.akash.weatherapp.model.WeatherResponseModel
import com.akash.weatherapp.repository.AppRepository
import com.akash.weatherapp.utils.Constants
import com.akash.weatherapp.utils.DateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val appRepository: AppRepository) :
    ViewModel() {

   private val _progressBarLiveData = MutableLiveData<Int>()
    val progressBarLiveData: MutableLiveData<Int>
        get() = _progressBarLiveData

    private val _weatherDataEntityListLiveData =
        MutableLiveData<List<WeatherEntity>>()
    val weatherDataEntityListLiveData: MutableLiveData<List<WeatherEntity>>
        get() = _weatherDataEntityListLiveData

    // Fetch weather details from api by entering city
    fun getWeatherDetailsByCity(city: String) {
        _progressBarLiveData.postValue(View.VISIBLE)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = appRepository.getWeatherDetailsByCity(city)
                addWeatherDataToDb(response)
                fetchAllWeatherDetailsFromDb()
                withContext(Dispatchers.Main) {
                    _progressBarLiveData.postValue(View.GONE)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _progressBarLiveData.postValue(View.GONE)
                }
            }
        }
    }

    // Fetch weather details from api by user current location
    fun getWeatherDetailsByLatLon(lat: String,lon: String) {
        _progressBarLiveData.postValue(View.VISIBLE)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = appRepository.getWeatherDetailsByLatLon(lat,lon)
                addWeatherDataToDb(response)
                fetchAllWeatherDetailsFromDb()
                withContext(Dispatchers.Main) {
                    _progressBarLiveData.postValue(View.GONE)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _progressBarLiveData.postValue(View.GONE)
                }
            }
        }
    }

    // Add weather data into database
    private suspend fun addWeatherDataToDb(weatherResponseModel: WeatherResponseModel) {
        WeatherEntity().apply {
            this.id = weatherResponseModel.id
            this.icon = weatherResponseModel.weather[0].icon
            this.cityName = weatherResponseModel.name
            this.weather = weatherResponseModel.weather[0].main
            this.countryName = weatherResponseModel.sys.country
            this.temp = weatherResponseModel.main.temp
            this.dateTime = DateUtils.getCurrentDateTime(
                Constants.DATE_TIME_FORMAT
            )
            appRepository.addWeather(this)
        }
    }

    // fetch weather data from local database
    fun fetchAllWeatherDetailsFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherDetailList = appRepository.fetchAllWeatherData()
            withContext(Dispatchers.Main) {
                _weatherDataEntityListLiveData.postValue(weatherDetailList)
            }
        }
    }
}
package com.akash.weatherapp.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.weatherapp.model.WeatherResponseModel
import com.akash.weatherapp.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val appRepository: AppRepository) :
    ViewModel() {

    private val _responseLiveData = MutableLiveData<WeatherResponseModel>()
    private val _progressBarLiveData = MutableLiveData<Int>()
    val responseLiveData: MutableLiveData<WeatherResponseModel>
        get() = _responseLiveData
    val progressBarLiveData: MutableLiveData<Int>
        get() = _progressBarLiveData

    // Fetch weather details from api
    fun getWeatherDetailsByCity(city: String) {
        _progressBarLiveData.postValue(View.VISIBLE)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = appRepository.getWeatherDetailsByCity(city)
                withContext(Dispatchers.Main) {
                    _responseLiveData.postValue(response)
                    _progressBarLiveData.postValue(View.GONE)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _progressBarLiveData.postValue(View.GONE)
                }
            }
        }
    }
}
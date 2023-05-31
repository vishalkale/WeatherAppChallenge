package com.akash.weatherapp.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.akash.weatherapp.databinding.ActivityMainBinding
import com.akash.weatherapp.viewmodel.WeatherViewModel
import dagger.android.support.DaggerAppCompatActivity
import java.util.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val weatherViewModel: WeatherViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addObservers()
        weatherViewModel.getWeatherDetailsByCity("Frisco")
    }

    private fun addObservers() {
        weatherViewModel.responseLiveData.observe(this, {
                    Log.e("##",it.toString())
                   })
        weatherViewModel.progressBarLiveData.observe(this, {
            binding.progressBarLoading.visibility = it
        })
    }
}
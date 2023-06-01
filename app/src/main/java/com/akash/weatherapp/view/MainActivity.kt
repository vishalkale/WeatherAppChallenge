package com.akash.weatherapp.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.akash.weatherapp.databinding.ActivityMainBinding
import com.akash.weatherapp.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import dagger.android.support.DaggerAppCompatActivity
import java.util.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var cityWeatherAdapter: CityWeatherAdapter

    private val weatherViewModel: WeatherViewModel by viewModels {
        viewModelFactory
    }

    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: Int = 1900
    private var locationPermissionGranted: Boolean = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeViews()
        addObservers()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getCurrentLocation()
        weatherViewModel.fetchAllWeatherDetailsFromDb()
    }

    // Initialize recycler view and its adapter
    private fun initializeViews() {
        cityWeatherAdapter = CityWeatherAdapter()
        val mLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.weatherRv.apply {
            layoutManager = mLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = cityWeatherAdapter
        }

        // Fetch weather details from api on action of done button.
        binding.etSearchInput.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                weatherViewModel.getWeatherDetailsByCity((view as EditText).text.toString())
            }
            false
        }
    }

    // Initialize the observers
    private fun addObservers() {
        weatherViewModel.progressBarLiveData.observe(this, {
            binding.progressBarLoading.visibility = it
        })
        weatherViewModel.weatherDataEntityListLiveData.observe(this, {
            cityWeatherAdapter.addWeatherEntities(it)
            cityWeatherAdapter.notifyDataSetChanged()
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    getCurrentLocation()
                }
            }
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
            return
        }

        fusedLocationClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                    CancellationTokenSource().token

                override fun isCancellationRequested() = false
            })
            .addOnSuccessListener { location: Location? ->
                weatherViewModel.getWeatherDetailsByLatLon(
                    location?.latitude.toString(),
                    location?.longitude.toString()
                )
            }
    }
}
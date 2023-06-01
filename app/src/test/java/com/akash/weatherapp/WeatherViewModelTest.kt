package com.akash.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.akash.weatherapp.model.WeatherEntity
import com.akash.weatherapp.repository.AppRepository
import com.akash.weatherapp.viewmodel.WeatherViewModel
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    lateinit var weatherViewModel: WeatherViewModel

    @Mock
    lateinit var repository: AppRepository

    @Mock
    lateinit var hasReachedMaxObserver: Observer<List<WeatherEntity>>

    @Rule
    @JvmField
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private var testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private var testCoroutineScope = TestCoroutineScope()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        weatherViewModel = WeatherViewModel(repository)
        weatherViewModel.weatherDataEntityListLiveData.observeForever(hasReachedMaxObserver)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getWeatherDataByCity() {
        val list = ArrayList<WeatherEntity>()
        list.add(WeatherEntity(1, 23.0, "2n", "Frisco", "US", "Clear", "1 June, 00:36 AM"))
        testCoroutineScope.launch(testDispatcher) {
            whenever(repository.fetchAllWeatherData()).thenReturn(list)
            weatherViewModel.fetchAllWeatherDetailsFromDb()
            assertEquals(true, weatherViewModel.weatherDataEntityListLiveData.value)
        }
    }
}
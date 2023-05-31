package com.akash.weatherapp.di

import android.content.Context
import com.akash.weatherapp.WeatherApplication
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ContextModule {
    @Singleton
    @Binds
    abstract fun providesContext(appInstance: WeatherApplication): Context
}
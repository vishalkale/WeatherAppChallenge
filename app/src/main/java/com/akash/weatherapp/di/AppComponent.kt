package com.akash.weatherapp.di

import com.akash.weatherapp.WeatherApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, ActivityModule::class, ContextModule::class, ViewModelModule::class, NetworkModule::class])
interface AppComponent : AndroidInjector<WeatherApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: WeatherApplication): Builder
        fun build(): AppComponent
    }

    override fun inject(application: WeatherApplication)
}
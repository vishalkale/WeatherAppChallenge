package com.akash.weatherapp.di


import com.akash.weatherapp.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
/*
Activity module
Register all the activities in this class for DI.
*/
@Module
interface ActivityModule {
    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity
}

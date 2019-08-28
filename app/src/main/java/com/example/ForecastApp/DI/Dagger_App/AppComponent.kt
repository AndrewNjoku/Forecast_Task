package com.example.ForecastApp.DI.Dagger_App


import com.example.ForecastApp.DI.Dagger_Composer.WeatherFeatureComponent
import com.example.ForecastApp.DI.Dagger_Composer.WeatherFeatureModule
import com.example.ForecastApp.Database.ForecastDatabase
import com.example.ForecastApp.Network.ForecastService
import com.example.ForecastApp.application.App

import javax.inject.Singleton

import dagger.Component
import dagger.android.AndroidInjector

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent: AndroidInjector<App>{

    //this is ehat the application component provides
    val userRepository: ForecastDatabase
    val apiService: ForecastService

    fun plus(mySubmodule: WeatherFeatureModule): WeatherFeatureComponent



}

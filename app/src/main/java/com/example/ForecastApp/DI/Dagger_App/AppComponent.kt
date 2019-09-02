package com.example.ForecastApp.DI.Dagger_App


import com.example.ForecastApp.DI.Dagger_Main.MainPresenterModule
import com.example.ForecastApp.DI.Dagger_Composer.DetailComponent
import com.example.ForecastApp.DI.Dagger_Composer.MainPageComponent
import com.example.ForecastApp.DI.Dagger_Composer.SearchComponent
import com.example.ForecastApp.DI.Dagger_Main.DetailPresenterModule
import com.example.ForecastApp.DI.Dagger_Main.SearchPresenterModule
import com.example.ForecastApp.Database.ForecastDatabase
import com.example.ForecastApp.Network.ForecastService
import com.example.ForecastApp.application.App

import javax.inject.Singleton

import dagger.Component
import dagger.android.AndroidInjector

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent: AndroidInjector<App>{

    //this is what the application component provides
    val userRepository: ForecastDatabase
    val apiService: ForecastService

    //these are the methods which return an instance of the specific component to be injected in my 3 fragments
    // These components will inherit the uerRepo from my aplication scope AppModule and the API related instance
    //for use in my RESTfull calls from the NetworkModule

    fun plus(mySubmodule: MainPresenterModule): MainPageComponent

    fun plus(mySubmodule: DetailPresenterModule): DetailComponent

    fun plus(mySubmodule: SearchPresenterModule): SearchComponent






}

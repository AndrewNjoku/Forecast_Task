package com.example.ForecastApp.DI.Dagger_Composer

import androidx.fragment.app.Fragment
import com.example.ForecastApp.DI.Dagger_Main.DetailPresenterModule
import com.example.ForecastApp.Fragments.WeatherDetailFragment
import com.example.ForecastApp.model.WeatherDetailUseCase
import com.example.minimoneybox.model.WeatherDetailUseCase

import dagger.Subcomponent
import dagger.android.AndroidInjector

@FragmentScope
@Subcomponent(modules = [DetailPresenterModule::class])

interface DetailComponent: AndroidInjector<Fragment> {


    //provide model interactor
    val myModelInteractor: WeatherDetailUseCase


    fun inject(fragment: WeatherDetailFragment)
}


package com.example.ForecastApp.DI.Dagger_Composer

import androidx.fragment.app.Fragment
import com.example.ForecastApp.DI.Dagger_Main.MainPresenterModule
import com.example.ForecastApp.Fragments.MainScreenFragment
import com.example.minimoneybox.model.WeatherDetailUseCase

import dagger.Subcomponent
import dagger.android.AndroidInjector

@FragmentScope
@Subcomponent(modules = [MainPresenterModule::class])

interface MainPageComponent: AndroidInjector<Fragment> {


    //for the main page we are providing two use cases, the search use case aswell s the recent searches use case

    val myModelInteractor:


    fun inject(fragment: MainScreenFragment)

}


package com.example.ForecastApp.DI.Dagger_Composer

import androidx.fragment.app.Fragment
import com.example.ForecastApp.DI.Dagger_Weather_Feature.DetailFeatureModule
import com.example.ForecastApp.DI.Dagger_Weather_Feature.SearchFeatureModule
import com.example.ForecastApp.Fragments.MainScreenFragment
import com.example.ForecastApp.Fragments.SearchResultsFragment
import com.example.ForecastApp.Fragments.WeatherDetailFragment
import com.example.minimoneybox.model.ApplicationModelContract

import dagger.Subcomponent
import dagger.android.AndroidInjector

@FragmentScope
@Subcomponent(modules = [MainFeatureModule::class,SearchFeatureModule::class, DetailFeatureModule::class])

interface WeatherFeatureComponent: AndroidInjector<Fragment> {



    //provide model interactor
    val myModelInteractor: ApplicationModelContract


    fun inject(fragment: MainScreenFragment)

    fun inject(fragment: SearchResultsFragment)

    fun inject(fragment : WeatherDetailFragment)
}


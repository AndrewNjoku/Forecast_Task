package com.example.ForecastApp.DI.Dagger_Composer

import androidx.fragment.app.Fragment
import com.example.ForecastApp.Fragments.MainScreenFragment
import com.example.ForecastApp.Fragments.SearchResultsFragment
import com.example.ForecastApp.Fragments.WeatherDetailFragment
import com.example.minimoneybox.model.ApplicationModelContract

import dagger.Subcomponent
import dagger.android.AndroidInjector

@FragmentScope
@Subcomponent(modules = [WeatherFeatureModule::class])

interface WeatherFeatureComponent: AndroidInjector<Fragment> {

    val myModelInteractor: ApplicationModelContract

    fun inject(fragment: MainScreenFragment)

    fun inject(fragment: SearchResultsFragment)

    fun inject(fragment : WeatherDetailFragment)
}


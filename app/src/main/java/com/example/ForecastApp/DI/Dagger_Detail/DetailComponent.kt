package com.example.ForecastApp.DI.Dagger_Composer

import androidx.fragment.app.Fragment
import com.example.ForecastApp.DI.Dagger_Main.DetailPresenterModule
import com.example.ForecastApp.Fragments.SearchResultsFragment
import com.example.ForecastApp.Fragments.WeatherDetailFragment
import com.example.minimoneybox.model.ApplicationModelContract

import dagger.Subcomponent
import dagger.android.AndroidInjector

@FragmentScope
@Subcomponent(modules = [DetailPresenterModule::class])

interface DetailComponent: AndroidInjector<Fragment> {


    //provide model interactor
    val myModelInteractor: ApplicationModelContract


    fun inject(fragment: WeatherDetailFragment)
}


package com.example.ForecastApp.DI.Dagger_Composer

import androidx.fragment.app.Fragment
import com.example.ForecastApp.DI.Dagger_Main.MainPresenterModule
import com.example.ForecastApp.Fragments.MainScreenFragment
import com.example.minimoneybox.model.ApplicationModelContract

import dagger.Subcomponent
import dagger.android.AndroidInjector

@FragmentScope
@Subcomponent(modules = [MainPresenterModule::class])

interface MainPageComponent: AndroidInjector<Fragment> {


    //provide model interactor
    val myModelInteractor: ApplicationModelContract


    fun inject(fragment: MainScreenFragment)

}


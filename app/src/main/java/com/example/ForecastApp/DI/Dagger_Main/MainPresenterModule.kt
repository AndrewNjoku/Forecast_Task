package com.example.ForecastApp.DI.Dagger_Main


import com.example.ForecastApp.Activities.HomeActivity
import com.example.ForecastApp.DI.Dagger_Composer.FragmentScope
import com.example.ForecastApp.Database.ForecastDatabase
import com.example.ForecastApp.Network.ForecastService
import com.example.ForecastApp.mvp.MainScreenFragment.*
import com.example.ForecastApp.model.ApplicationModel
import com.example.minimoneybox.model.WeatherDetailUseCase

import dagger.Module
import dagger.Provides

@Module
class MainPresenterModule(private val myView: MainScreenFragmentContract.View,private val myActivity: HomeActivity){

    @Provides
    fun provideMainView(): MainScreenFragmentContract.View{

        return myView
    }

    @Provides
    fun provideActivity(): HomeActivity {
        return myActivity
    }

    @Provides
    fun provideModelInteractor(service:ForecastService,data:ForecastDatabase, view: MainScreenFragmentContract.View): WeatherDetailUseCase{

        return ApplicationModel(service,data,view)
    }


    //here we provide the 3 presenters which provide the weather feature

    @Provides
    @FragmentScope
    internal fun provideMainFragmentPresenter(mymodelinteractor: WeatherDetailUseCase, myActivity:HomeActivity): MainScreenFragmentContract.Presenter {

        return MainScreenFragmentPresenter(mymodelinteractor,myActivity)
    }


}



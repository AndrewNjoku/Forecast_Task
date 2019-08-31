package com.example.ForecastApp.DI.Dagger_Composer


import com.example.ForecastApp.Database.ForecastDatabase
import com.example.ForecastApp.Fragments.MainScreenFragment
import com.example.ForecastApp.Fragments.WeatherDetailFragment
import com.example.ForecastApp.Network.ForecastService
import com.example.ForecastApp.mvp.BaseContract
import com.example.ForecastApp.mvp.MainScreenFragment.*
import com.example.minimoneybox.model.ApplicationModel
import com.example.minimoneybox.model.ApplicationModelContract

import dagger.Module
import dagger.Provides

@Module
class MainFeatureModule(private val myView: MainScreenFragmentContract.View){

    @Provides
    fun provideMainView(): MainScreenFragmentContract.View{

        return myView
    }



    @Provides
    fun provideModelInteractor(service:ForecastService,data:ForecastDatabase, view: MainScreenFragmentContract.View): ApplicationModelContract{

        return ApplicationModel(service,data,view)
    }


    //here we provide the 3 presenters which provide the weather feature

    @Provides
    @FragmentScope
    internal fun provideMainFragmentPresenter(mymodelinteractor: ApplicationModelContract): MainScreenFragmentContract.Presenter {

        return MainScreenFragmentPresenter(mymodelinteractor)
    }

    @Provides
    @FragmentScope
    internal fun provideSearchResultsPresenter(myModelInteractor: ApplicationModelContract): SearchResultsFragmentContract.Presenter {

        return SearchResultsFragmentPresenter(myModelInteractor)
    }

    @Provides
    @FragmentScope
    internal fun provideWeatherDetailsPresenter(myModelInteractor: ApplicationModelContract): DetailFragmentContract.Presenter {

        return DetailFragmentPresenter(myModelInteractor)
    }


}



package com.example.ForecastApp.DI.Dagger_Main


import com.example.ForecastApp.Activities.HomeActivity
import com.example.ForecastApp.DI.Dagger_Composer.FragmentScope
import com.example.ForecastApp.Database.ForecastDatabase
import com.example.ForecastApp.Network.ForecastService
import com.example.ForecastApp.mvp.MainScreenFragment.*
import com.example.ForecastApp.model.ApplicationModel
import com.example.minimoneybox.model.ApplicationModelContract

import dagger.Module
import dagger.Provides

@Module
class SearchPresenterModule(private val myView: SearchResultsFragmentContract.View, private val myActivity: HomeActivity){

    @Provides
    fun provideSearchView(): SearchResultsFragmentContract.View{
        return myView
    }
    @Provides
    fun provideActivity(): HomeActivity {
        return myActivity
    }


    @Provides
    fun provideModelInteractor(service:ForecastService,data:ForecastDatabase, view: SearchResultsFragmentContract.View): ApplicationModelContract{

        return ApplicationModel(service,data,view)
    }


    @Provides
    @FragmentScope
    internal fun provideSearchResultsPresenter(myModelInteractor: ApplicationModelContract, myActivity: HomeActivity): SearchResultsFragmentContract.Presenter {

        return SearchResultsFragmentPresenter(myModelInteractor,myActivity)
    }

}



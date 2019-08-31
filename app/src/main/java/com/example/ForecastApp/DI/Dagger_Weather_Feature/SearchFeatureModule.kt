package com.example.ForecastApp.DI.Dagger_Weather_Feature


import android.content.Context
import com.example.ForecastApp.DI.Dagger_Composer.FragmentScope
import com.example.ForecastApp.Database.ForecastDatabase
import com.example.ForecastApp.Network.ForecastService
import com.example.ForecastApp.mvp.MainScreenFragment.*
import com.example.minimoneybox.model.ApplicationModel
import com.example.minimoneybox.model.ApplicationModelContract

import dagger.Module
import dagger.Provides

@Module
class SearchFeatureModule(private val myView: SearchResultsFragmentContract.View,private val context: Context){

    @Provides
    fun provideSearchView(): SearchResultsFragmentContract.View{
        return myView
    }

    @Provides
    fun provideCOontext(): SearchResultsFragmentContract.View{
        return myView
    }




    @Provides
    fun provideModelInteractor(service:ForecastService,data:ForecastDatabase, view: SearchResultsFragmentContract.View): ApplicationModelContract{

        return ApplicationModel(service,data,view)
    }


    @Provides
    @FragmentScope
    internal fun provideSearchResultsPresenter(myModelInteractor: ApplicationModelContract, context: Context): SearchResultsFragmentContract.Presenter {

        return SearchResultsFragmentPresenter(myModelInteractor,context)
    }

}



package com.example.ForecastApp.DI.Dagger_Composer


import android.content.Context
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
class MainFeatureModule(private val myView: MainScreenFragmentContract.View, private val context: Context){

    @Provides
    fun provideMainView(): MainScreenFragmentContract.View{

        return myView
    }

    @Provides
    fun provideContext(): Context{

        return context
    }


    @Provides
    fun provideModelInteractor(service:ForecastService,data:ForecastDatabase, view: MainScreenFragmentContract.View): ApplicationModelContract{

        return ApplicationModel(service,data,view)
    }


    //here we provide the 3 presenters which provide the weather feature

    @Provides
    @FragmentScope
    internal fun provideMainFragmentPresenter(mymodelinteractor: ApplicationModelContract,context:Context): MainScreenFragmentContract.Presenter {

        return MainScreenFragmentPresenter(mymodelinteractor,context)
    }


}



package com.example.ForecastApp.DI.Dagger_Main


import com.example.ForecastApp.DI.Dagger_Composer.FragmentScope
import com.example.ForecastApp.Database.ForecastDatabase
import com.example.ForecastApp.Network.ForecastService
import com.example.ForecastApp.mvp.DetailFragment.DetailFragmentPresenter
import com.example.ForecastApp.mvp.MainScreenFragment.*
import com.example.minimoneybox.model.ApplicationModel
import com.example.minimoneybox.model.ApplicationModelContract
import dagger.Module
import dagger.Provides

@Module
class DetailPresenterModule(private val myView: DetailFragmentContract.View){

    //no activity context passed in this module since its not needed

    @Provides
    fun provideDetailView(): DetailFragmentContract.View{
        return myView
    }


    @Provides
    fun provideModelInteractor(service:ForecastService,data:ForecastDatabase, view: DetailFragmentContract.View): ApplicationModelContract{

        return ApplicationModel(service,data,view)
    }


    @Provides
    @FragmentScope
    internal fun provideDetailFragmentPresenter(myModelInteractor: ApplicationModelContract): DetailFragmentContract.Presenter {

        return DetailFragmentPresenter(myModelInteractor)
    }

}



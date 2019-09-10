package com.example.ForecastApp.DI.Dagger_Main


import com.example.ForecastApp.DI.Dagger_Composer.FragmentScope
import com.example.ForecastApp.Database.ForecastDatabase
import com.example.ForecastApp.mvp.DetailFragment.DetailFragmentContract
import com.example.ForecastApp.mvp.DetailFragment.DetailFragmentPresenter
import com.example.ForecastApp.model.WeatherDetailUseCase
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
    fun provideDetailUseCase(data:ForecastDatabase, view: DetailFragmentContract.View): WeatherDetailUseCase {

        return WeatherDetailUseCase(data,view)
    }


    @Provides
    @FragmentScope
    internal fun provideDetailFragmentPresenter(myDetailUseCase: WeatherDetailUseCase): DetailFragmentContract.Presenter {

        return DetailFragmentPresenter(myDetailUseCase)
    }

}



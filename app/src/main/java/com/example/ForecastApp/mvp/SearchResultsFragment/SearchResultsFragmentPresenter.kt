package com.example.ForecastApp.mvp.MainScreenFragment

import android.content.Context
import android.util.Log
import com.example.ForecastApp.DataBank.Utils
import com.example.minimoneybox.model.WeatherDetailUseCase

class SearchResultsFragmentPresenter(private val myModelInteractor: WeatherDetailUseCase, private val context: Context) : SearchResultsFragmentContract.Presenter {

    override fun showSearchResults(location: String ){

        Log.e("SearchPresenter","Showing search results")

        myModelInteractor.getForecastSearch(Utils.isOnline(context),location)
    }

    override fun stop() {
        myModelInteractor.stop()
    }




}

package com.example.ForecastApp.mvp.MainScreenFragment

import android.content.Context
import android.util.Log
import com.example.ForecastApp.DataBank.Utils
import com.example.minimoneybox.model.ApplicationModelContract

class SearchResultsFragmentPresenter(private val myModelInteractor: ApplicationModelContract, private val context: Context) : SearchResultsFragmentContract.Presenter {

    override fun showSearchResults(location: String ){

        Log.e("SearchPresenter","Showing search results")

        myModelInteractor.getForecastSearch(Utils.isOnline(context),location)
    }

    override fun detatchView() {
        myModelInteractor.stop()
    }




}

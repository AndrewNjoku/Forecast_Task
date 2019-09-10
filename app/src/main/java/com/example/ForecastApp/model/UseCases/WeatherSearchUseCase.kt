package com.example.ForecastApp.model.UseCases


//This use case is involved with the search functionality of the main pages searchAutoComplete

import com.example.ForecastApp.DataBank.Constants
import com.example.ForecastApp.DataBank.Utils
import com.example.ForecastApp.Database.ForecastDatabase
import com.example.ForecastApp.Network.ForecastService
import com.example.ForecastApp.model.ApplicationModelContract
import com.example.ForecastApp.model.Objects.Main_Elements.Forecast
import com.example.ForecastApp.mvp.BaseContract
import com.example.ForecastApp.mvp.MainScreenFragment.SearchResultsFragmentContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherSearchUseCase (private val myService: ForecastService
                             , private val myDatabase: ForecastDatabase
                             , private val myView: BaseContract.View
                        ): ApplicationModelContract {


    private val compositeDisposable: CompositeDisposable = CompositeDisposable()



    //needed for this feature since we are initiating the search from this use case. We are calling the view method via the handleResults which is firstly Reactive
    //and also abides by the clean code architecture as we are referencing application

    //with each forecast add to the database onNext()
  private fun forecastFromAPI(location:String): Observable<Forecast>{

        return myService.getFiveDayForecast(location,Constants.API_KEY)
                .doOnNext { this.addToDb(it) }

    }

    //based on our weather search in the main activity, once the user selects a city, this method will be called
    //and the result will be stored in the database

    fun getForecastSearch(location:String) {

        val view = myView as SearchResultsFragmentContract.View
        val observable = forecastFromAPI(location)


        compositeDisposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { forecast -> forecast.days }
                //transform days to days_w
                .map{ days -> Utils.parseDays(days) }
                //show progress once subscribed
                .doOnSubscribe { view.showProgress(true) }
                .doOnTerminate { view.showProgress(false) }
                .doOnError{ error -> view.showError(error)}
                .subscribe{ result -> this.handleResult(result!!) })

    }



    override fun stop() {
        compositeDisposable.clear()
    }



    //overriding method for handling search results
    override fun handleResult(days_w: List<*>) {

        if (days_w.isEmpty()) {
            myView.showNoResults()
        } else {
            myView.showResults(days_w)
        }

    }


    //Add to the database the result of the search

    private fun addToDb(forecast: Forecast) {
        myDatabase.forecastDao()
                .insertForecasts(forecast)
    }







}
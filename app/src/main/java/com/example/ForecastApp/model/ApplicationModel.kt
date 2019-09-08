package com.example.ForecastApp.model

import com.example.ForecastApp.DataBank.Constants
import com.example.ForecastApp.DataBank.Utils
import com.example.ForecastApp.Database.ForecastDatabase
import com.example.ForecastApp.Network.ForecastService
import com.example.ForecastApp.model.Objects.Main_Elements.Day
import com.example.ForecastApp.model.Objects.Main_Elements.Day_w
import com.example.ForecastApp.model.Objects.Main_Elements.Forecast
import com.example.ForecastApp.mvp.BaseContract
import com.example.ForecastApp.mvp.DetailFragment.DetailFragmentContract
import com.example.ForecastApp.mvp.MainScreenFragment.MainScreenFragmentContract
import com.example.ForecastApp.mvp.MainScreenFragment.SearchResultsFragmentContract
import com.example.minimoneybox.model.ApplicationModelContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ApplicationModel (private val myService: ForecastService
                        , private val myDatabase: ForecastDatabase
                        , private val myView: BaseContract.View
                        ): ApplicationModelContract {


    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val allForecasts: Observable<List<Forecast>>
            get() {
                return myDatabase
                        .forecastDao()
                        .forecastAll
                        .observeOn(AndroidSchedulers.mainThread())
                        .toObservable()
    }


    private fun forecastFromDb(location:String): Observable<Forecast>{

        return myDatabase.forecastDao()
                .forecast(location)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete{ this.handleEmptyDb()}
                .toObservable()

    }

    //with each forecast add to the database onNext()
  private fun forecastFromAPI(location:String): Observable<Forecast>{

        return myService.getFiveDayForecast(location,Constants.API_KEY)
                .doOnNext { this.addToDb(it) }

    }

    //based on our weather search in the main activity, once the user selects a city, this method will be called
    //and the result will be stored in the database
    override fun getForecastSearch(isOnline: Boolean, location:String) {

        val view = myView as SearchResultsFragmentContract.View
        val observable = if (isOnline) forecastFromAPI(location) else forecastFromDb(location)

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
                .subscribe{ result -> this.handleResultSearch(result!!) })

    }

    override fun getRecentForecasts() {
        val view = myView as MainScreenFragmentContract.View
        val observable = allForecasts
        compositeDisposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //show progress once subscribed
                .doOnSubscribe { view.showProgress(true) }
                .doOnTerminate { view.showProgress(false) }
                .doOnError{ error -> view.showError(error)}
                .subscribe{ result -> this.handleResultRecent(result!!) })

    }
    override fun getForecastDayDetails(location:String) {

        val view = myView as DetailFragmentContract.View
        val observable = forecastFromDb(location)
        compositeDisposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { forecast -> forecast.days }
                .doOnSubscribe { view.showProgress(true) }
                .doOnTerminate { view.showProgress(false) }
                .doOnError{ error -> view.showError(error)}
                .subscribe{ result -> this.handleResultDetail(result!!) })
    }




    override fun stop() {
        compositeDisposable.clear()
    }
    override fun handleEmptyDb() {
        handleResultSearch(emptyList())
    }

    //here also the weather view is concerned, since this method only gets called after getweather is called we
    //we can assign the view that is passed through that method

    override fun handleResultRecent(forecasts: List<Forecast>) {
        if (forecasts.isEmpty()) {
            //do nothing that is fine
            myView.showNoResults()
        } else {
            myView.showResults(forecasts)
        }
    }
    override fun handleResultSearch(days_w: List<Day_w>) {
        if (days_w.isEmpty()) {
            myView.showNoResults()
        } else {
            myView.showResults(days_w)
        }

    }


    override fun handleResultDetail(days: List<Day>) {
        if (days.isEmpty()) {
            myView.showNoResults()
        } else {
            myView.showResults(days)
        }

    }

    override fun addToDb(forecast: Forecast) {
        myDatabase.forecastDao()
                .insertForecasts(forecast)
    }

}
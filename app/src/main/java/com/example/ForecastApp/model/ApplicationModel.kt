package com.example.minimoneybox.model

import com.example.ForecastApp.DataBank.Constants
import com.example.ForecastApp.DataBank.Constants.FORECAST_BY_NAME_URL
import com.example.ForecastApp.DataBank.Utils.buildUrl
import com.example.ForecastApp.Database.ForecastDatabase
import com.example.ForecastApp.Fragments.WeatherDetailFragment
import com.example.ForecastApp.Network.ForecastService
import com.example.ForecastApp.model.Objects.Main_Elements.Day
import com.example.ForecastApp.model.Objects.Main_Elements.Forecast
import com.example.ForecastApp.mvp.MainScreenFragment.DetailFragmentContract
import com.example.ForecastApp.mvp.MainScreenFragment.MainScreenFragmentContract
import com.example.ForecastApp.mvp.MainScreenFragment.SearchResultsFragmentContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ApplicationModel (private val myService: ForecastService
                        , private val myDatabase: ForecastDatabase
                        ): ApplicationModelContract {



    //weather detail view
    private lateinit var myDetailView: DetailFragmentContract.View

    //main page weather search + recent weather vue
    private lateinit var myResultsView: SearchResultsFragmentContract.View

    private lateinit var myRecentView: MainScreenFragmentContract.View


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
    override fun getForecastSearch(isOnline: Boolean, location:String, view : SearchResultsFragmentContract.View) {
        this.myResultsView=view

        //get weather for url by name

        val url = buildUrl(FORECAST_BY_NAME_URL, location)

        val observable = if (isOnline) forecastFromAPI(url) else forecastFromDb(location)
        compositeDisposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { forecast -> forecast.days }
                //show progress once subscribed
                .doOnSubscribe { myResultsView.showProgress(true) }
                .doOnTerminate { myResultsView.showProgress(false) }
                .doOnError{ error -> view.showError(error)}
                .subscribe{ result -> this.handleResultSearch(result!!) })

    }

    override fun getRecentForecasts( view : MainScreenFragmentContract.View) {
        this.myRecentView=view
        val observable = allForecasts
        compositeDisposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //show progress once subscribed
                .doOnSubscribe { myRecentView.showProgress(true) }
                .doOnTerminate { myRecentView.showProgress(false) }
                .doOnError{ error -> view.showError(error)}
                .subscribe{ result -> this.handleResultRecent(result!!) })

    }

    override fun getForecastDayDetails(location:String, day: Int, view: DetailFragmentContract.View) {

        this.myDetailView=view

        //we can get the whole forecast object and then extract the required day
        //since we have already stored a location in our db to get to this stage we just need to
        //retrieve the forecast from our db

        val observable = forecastFromDb(location)
        compositeDisposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { forecast -> forecast.days }
                .map { days -> days[day] }
                //show progress once subscribed
                .doOnSubscribe { myRecentView.showProgress(true) }
                .doOnTerminate { myRecentView.showProgress(false) }
                .doOnError{ error -> view.showError(error)}
                .subscribe{ result -> this.handleResultDetail(result!!) })

    }

    override fun noStoredData(): Boolean {

       // val checkForEmpty = myDatabase.forecastDao().forecast
        //val check= checkForEmpty.onErrorComplete()
      // return  (myDatabase.forecastDao().forecast==null)
        return true
    }



    override fun start() {}

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
            myRecentView.showNoRecentSearches()
        } else {
            myRecentView.showRecentSavedSearches(forecasts)
        }
    }
    override fun handleResultSearch(days: List<Day>) {
        if (days.isEmpty()) {
            myResultsView.showTryAgain(true)
        } else {
            myResultsView.showTryAgain(false)
             myResultsView.showSearchResults(days)
        }
    }


    override fun handleResultDetail(day:Day) {

           myDetailView.showForecast(day)

    }

    override fun addToDb(forecast: Forecast) {
        myDatabase.forecastDao()
                .insertForecasts(forecast)
    }

}
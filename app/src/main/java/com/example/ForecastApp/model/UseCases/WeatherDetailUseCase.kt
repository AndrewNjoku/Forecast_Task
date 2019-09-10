package com.example.ForecastApp.model


import com.example.ForecastApp.Database.ForecastDatabase
import com.example.ForecastApp.model.Objects.Main_Elements.Forecast
import com.example.ForecastApp.mvp.BaseContract
import com.example.ForecastApp.mvp.DetailFragment.DetailFragmentContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherDetailUseCase (private val myDatabase: ForecastDatabase
                        , private val myView: BaseContract.View
                        ): ApplicationModelContract {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()


    //the actioned search will display the weather information in the search results fragment, when a day of the week is selected, i am passing
    //an extra to the detail fragment for that day which
    private fun forecastFromDb(location:String): Observable<Forecast>{

        return myDatabase.forecastDao()
                .forecast(location)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete{ this.handleEmptyDb()}
                .toObservable()

    }

    //based on our weather search in the main activity, once the user selects a city, this method will be called
    //and the result will be stored in the database

    fun getForecastDayDetails(location:String) {

        val view = myView as DetailFragmentContract.View
        val observable = forecastFromDb(location)
        compositeDisposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { forecast -> forecast.days }
                .doOnSubscribe { view.showProgress(true) }
                .doOnTerminate { view.showProgress(false) }
                .doOnError{ error -> view.showError(error)}
                .subscribe{ result -> this.handleResult(result!!) })
    }



    override fun stop() {
        compositeDisposable.clear()
    }
    private fun handleEmptyDb() {

        myView.showNoResults()
    }

    //here also the weather view is concerned, since this method only gets called after getweather is called we
    //we can assign the view that is passed through that method

    override fun handleResult(forecasts: List<*>) {
        if (forecasts.isEmpty()) {
            //do nothing that is fine
            myView.showNoResults()
        } else {
            myView.showResults(forecasts)
        }
    }



}
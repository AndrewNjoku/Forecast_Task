package com.example.ForecastApp.model.UseCases


import com.example.ForecastApp.Database.ForecastDatabase
import com.example.ForecastApp.model.ApplicationModelContract
import com.example.ForecastApp.model.Objects.Main_Elements.Forecast
import com.example.ForecastApp.mvp.BaseContract
import com.example.ForecastApp.mvp.MainScreenFragment.MainScreenFragmentContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers



//search results use case will define data for the search results screen as well as the detail weather screen as such there will be only one
//use case method for retrieving the data from the dependence's. There will also be a check to establish whether we want the 5 day weather
//or we want the detail view

class RecentWeatherUseCase (private val myDatabase: ForecastDatabase
                            , private val myView: BaseContract.View
                        ): ApplicationModelContract {


    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    // i only need to return all my forecasts stored in the database for this use case

    private val allForecasts: Observable<List<Forecast>>
            get() {
                return myDatabase
                        .forecastDao()
                        .forecastAll
                        .observeOn(AndroidSchedulers.mainThread())
                        .toObservable()
    }


    fun getRecentForecasts() {
        val view = myView as MainScreenFragmentContract.View
        val observable = allForecasts
        compositeDisposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //show progress once subscribed
                .doOnSubscribe { view.showProgress(true) }
                .doOnTerminate { view.showProgress(false) }
                .doOnError{ error -> view.showError(error)}
                .subscribe{ result -> this.handleResult(result!!) })

    }




    override fun stop() {
        compositeDisposable.clear()
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
package com.example.minimoneybox.model

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
            myView.showNoResults()
        } else {
            myView.showResults(forecasts)
        }
    }
    override fun handleResultSearch(days: List<Day>) {
        if (days.isEmpty()) {
            myView.showNoResults()
        } else
            var newList = ArrayList<Day_w>()
            //TODO rather longwinded way of achieving data transformation. Should use RX pipeline to transform data into required form
            //add days of the week in required format
            newList.add(Day_w("Monday"))
            newList.add(Day_w("Tuesday"))
            newList.add(Day_w("Wednesday"))
            newList.add(Day_w("Thursday"))
            newList.add(Day_w("Friday"))
            newList.add(Day_w("Saturday"))
            newList.add(Day_w("Sunday"))

            //here it is neccessery to convery the list of multi days to list of one day per item
            for (day: Day in days) {
                //if the day is the same
                when (Utils.getDayFromDate(day.dateAndTime)) {
//for each day (there will be multiple Day elements for each day) we will add the temperature to a list stored in the Day_W object
// finally after carrying out the for loop we will initiate a calulation of the minimum and max temp per day by sorting the arraylist
// and finally we will set the min and max temps and pass this data to the view to updae the adapter.
                    //Todo i can just simply pass the day object into a method inside of Day_W to instantiate the required data
                    // without bulking up this method which should really be kept clean
                    "Monday" -> newList[0].parse(day)
                    "Tuesday" -> newList[1].parse(day)
                    "Wednesday" -> newList[2].parse(day)
                    "Thursday" -> newList[3].parse(day)
                    "Friday" -> newList[4].parse(day)
                    "Saurday" -> newList[5].parse(day)
                    "Sunday" -> newList[6].parse(day)

                }
            }

            //For each day in the week

                for (day_w: Day_w in newList) {

                    //sort the list of temperatures
                    day_w.dailyTemps.sort()
                    //index 0 is min temp, index 3 is max temp : 4 x 3 hour readings for each day
                    day_w.minTemp = day_w.dailyTemps[0]
                    day_w.maxTemp = day_w.dailyTemps[3]
                }

            myView.showResults(newList)


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
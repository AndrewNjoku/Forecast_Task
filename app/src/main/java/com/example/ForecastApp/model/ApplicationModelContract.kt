package com.example.minimoneybox.model


import com.example.ForecastApp.model.Objects.Main_Elements.Day
import com.example.ForecastApp.model.Objects.Main_Elements.Forecast
import com.example.ForecastApp.mvp.MainScreenFragment.DetailFragmentContract
import com.example.ForecastApp.mvp.MainScreenFragment.MainScreenFragmentContract
import com.example.ForecastApp.mvp.MainScreenFragment.SearchResultsFragmentContract


interface ApplicationModelContract {


    fun start()

    fun stop()

    fun addToDb(forecast: Forecast)

    fun handleEmptyDb()

    fun noStoredData(): Any


    fun handleResultRecent(days: List<Forecast>)
    fun handleResultSearch(days: List<Day>)
    fun getForecastSearch(isOnline: Boolean, city: String, view: SearchResultsFragmentContract.View)
    fun getRecentForecasts(view: MainScreenFragmentContract.View)
    fun getForecastDayDetails(location: String, day:Int, view: DetailFragmentContract.View)
    fun handleResultDetail(day: Day)
}

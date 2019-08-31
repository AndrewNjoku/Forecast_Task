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
    fun handleResultDetail(days: List<Day>)
    fun getForecastSearch(isOnline: Boolean, location: String)
    fun getRecentForecasts()
    fun getForecastDayDetails(location: String)
}

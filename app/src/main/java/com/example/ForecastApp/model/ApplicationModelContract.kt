package com.example.minimoneybox.model


import com.example.ForecastApp.model.Objects.Main_Elements.Day
import com.example.ForecastApp.model.Objects.Main_Elements.Forecast


interface ApplicationModelContract {

    fun stop()
    fun addToDb(forecast: Forecast)
    fun handleEmptyDb()
    fun handleResultRecent(days: List<Forecast>)
    fun handleResultSearch(days: List<Day>)
    fun handleResultDetail(days: List<Day>)
    fun getForecastSearch(isOnline: Boolean, location: String)
    fun getRecentForecasts()
    fun getForecastDayDetails(location: String)
}

package com.example.ForecastApp.mvp.MainScreenFragment

import android.content.Context
import com.example.ForecastApp.model.Objects.Main_Elements.Forecast
import com.example.ForecastApp.mvp.BaseContract


interface MainScreenFragmentContract{
    interface View :BaseContract.View{

        fun savedSearchesInit()
        fun autoCompleteSearchInit()
        fun showRecentSavedSearches(cities: List<Forecast>)
        fun showProgress(b: Boolean)
        fun showError(error: Throwable?)
        fun showNoRecentSearches()


    }

    interface Presenter: BaseContract.Presenter<View> {
        fun attach(context:Context,fragView: View)
        fun getWeatherDetails()
        fun setSelectedLocation(toString: String)
        fun getRecentForecasts()
    }
}





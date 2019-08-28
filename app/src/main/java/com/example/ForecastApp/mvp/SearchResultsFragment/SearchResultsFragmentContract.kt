package com.example.ForecastApp.mvp.MainScreenFragment

import android.content.Context
import com.example.ForecastApp.model.Objects.Main_Elements.Day
import com.example.ForecastApp.model.Objects.Main_Elements.Forecast
import com.example.ForecastApp.mvp.BaseContract


interface SearchResultsFragmentContract{
    interface View :BaseContract.View{
        fun showProgress(b: Boolean)
        fun showError(error: Throwable?)
        fun setActivityTitle(name: String?)
        fun showTryAgain(shouldShow: Boolean)
        fun showSearchResults(days: List<Day>)
    }

    interface Presenter: BaseContract.Presenter<View> {

        fun attach(context: Context, fragView: View)
        fun showSearchResults(location: String)
    }
}





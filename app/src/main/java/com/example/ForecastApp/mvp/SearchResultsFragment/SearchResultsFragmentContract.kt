package com.example.ForecastApp.mvp.MainScreenFragment

import android.content.Context
import com.example.ForecastApp.model.Objects.Main_Elements.Day
import com.example.ForecastApp.model.Objects.Main_Elements.Forecast
import com.example.ForecastApp.mvp.BaseContract


interface SearchResultsFragmentContract{
    interface View :BaseContract.View{
        fun setActivityTitle(name: String?)
        fun showProgress(b: Boolean)



    }

    interface Presenter: BaseContract.Presenter<View> {


        fun showSearchResults(location: String)
    }
}





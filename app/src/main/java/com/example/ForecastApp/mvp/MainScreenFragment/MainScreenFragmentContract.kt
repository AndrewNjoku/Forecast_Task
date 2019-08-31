package com.example.ForecastApp.mvp.MainScreenFragment

import android.content.Context
import com.example.ForecastApp.model.Objects.Main_Elements.Forecast
import com.example.ForecastApp.mvp.BaseContract


interface MainScreenFragmentContract{
    interface View :BaseContract.View{

        fun savedSearchesInit()
        fun autoCompleteSearchInit()




    }

    interface Presenter: BaseContract.Presenter<View> {

        fun setSelectedLocation(toString: String)
        fun getRecentForecasts()
    }
}





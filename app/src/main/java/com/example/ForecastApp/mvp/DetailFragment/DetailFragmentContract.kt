package com.example.ForecastApp.mvp.MainScreenFragment

import android.content.Context
import com.example.ForecastApp.model.Objects.Main_Elements.Day
import com.example.ForecastApp.mvp.BaseContract


interface DetailFragmentContract {

    interface View : BaseContract.View {

        fun showForecast(day: Day)
        fun showError(throwable: Throwable)
        fun showProgress(shouldShow: Boolean)
        fun showTryAgain(shouldShow: Boolean)
        fun setActivityTitle(name: String?) {

        }

    }

    interface Presenter : BaseContract.Presenter<View> {


        fun attach(context: Context, fragView: View)
        fun getDayDetails(location: String, day: String)
    }
}
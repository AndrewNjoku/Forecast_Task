package com.example.ForecastApp.mvp.DetailFragment


import com.example.ForecastApp.mvp.BaseContract


interface DetailFragmentContract {

    interface View : BaseContract.View {

        fun showProgress(b: Boolean)

        fun setActivityTitle(name: String?) {

        }


    }

    interface Presenter : BaseContract.Presenter<View> {

        fun getDayDetails(location: String)
    }
}
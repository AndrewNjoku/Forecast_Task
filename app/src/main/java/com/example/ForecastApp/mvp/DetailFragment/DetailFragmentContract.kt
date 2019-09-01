package com.example.ForecastApp.mvp.MainScreenFragment


import com.example.ForecastApp.mvp.BaseContract


interface DetailFragmentContract {

    interface View : BaseContract.View {


        fun setActivityTitle(name: String?) {

        }


    }

    interface Presenter : BaseContract.Presenter<View> {

        fun getDayDetails(location: String)
    }
}
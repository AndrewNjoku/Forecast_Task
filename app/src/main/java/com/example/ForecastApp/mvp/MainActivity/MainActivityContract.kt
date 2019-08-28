package com.example.ForecastApp.mvp.MainScreenFragment

import android.content.Context
import com.example.ForecastApp.mvp.BaseContract


interface MainActivityContract {

    interface View :BaseContract.View{

        fun showError(throwable: Throwable)
        fun showMainPageFragment()
        fun showSearchResultsFragment(location: String)
        fun showDetailsFragment(location: String, day: Int)


    }

    interface Presenter : BaseContract.Presenter<View>{

        fun start()
        fun stop()
        fun initiateNetworkFragment()
        fun attachSearchResultsFrag(location: String)
        fun attachSearchResultsFrag(location: Long)

        fun attach(context: Context)
    }
}

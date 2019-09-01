package com.example.ForecastApp.mvp.MainScreenFragment

import android.content.Context
import com.example.ForecastApp.mvp.BaseContract


interface MainActivityContract {

    interface View :BaseContract.View{


        fun showMainPageFragment()
        fun showSearchResultsFragment(location: String)
        fun showDetailsFragment(location: String)


    }

    interface Presenter : BaseContract.Presenter<View>{

        fun initiateNetworkFragment()

    }
}

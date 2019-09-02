package com.example.ForecastApp.mvp.MainActivity

import android.content.Context
import com.example.ForecastApp.mvp.BaseContract


interface MainActivityContract {

    interface View {

        fun injectDependencies()
        fun showMainPageFragment()
        fun showSearchResultsFragment(location: String)
        fun showDetailsFragment(location: String)
        fun showError(error: Throwable?)
        fun showTryAgain(b: Boolean)


    }

    interface Presenter {

        fun initiateNetworkFragment()

        fun stop()
    }
}

package com.example.ForecastApp.mvp

import android.content.Context
import com.example.ForecastApp.model.Objects.Main_Elements.Day


class BaseContract {


    interface Presenter<in T> {


        fun stop()
    }

    interface View {

       fun injectDependencies()

        fun showResults(days: List<*>)

        fun showNoResults()

        fun showError(error: Throwable?)

        fun showTryAgain(b: Boolean)

        fun showNoInternet()

    }

}
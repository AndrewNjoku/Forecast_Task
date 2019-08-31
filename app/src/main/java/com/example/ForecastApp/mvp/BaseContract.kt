package com.example.ForecastApp.mvp

import android.content.Context
import com.example.ForecastApp.model.Objects.Main_Elements.Day


class BaseContract {


    interface Presenter<in T> {

        fun detatchView()
    }

    interface View {

       fun injectDependencies()

        fun showResults(days: List<*>)

        fun showNoResults()

    }

}
package com.example.ForecastApp.mvp

import android.content.Context


class BaseContract {


    interface Presenter<in T> {

        fun detatchView()
    }

    interface View {

       fun injectDependencies()

    }

}
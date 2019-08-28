package com.example.ForecastApp.application

import android.app.Application

import com.example.ForecastApp.DI.Dagger_App.AppComponent
import com.example.ForecastApp.DI.Dagger_App.AppModule
import com.example.ForecastApp.DI.Dagger_App.DaggerAppComponent
import com.example.ForecastApp.DI.Dagger_App.NetworkModule

import net.danlew.android.joda.JodaTimeAndroid

class App : Application() {

    lateinit var component: AppComponent

        private set

    override fun onCreate() {
        super.onCreate()
        instance=this
        setup()

    }

    private fun setup() {

        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule())
                .build()

                component.inject(this)


        JodaTimeAndroid.init(this)

    }

    companion object {
        lateinit var instance: App private set
    }


}



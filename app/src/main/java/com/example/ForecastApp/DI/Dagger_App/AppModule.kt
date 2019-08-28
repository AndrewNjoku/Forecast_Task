package com.example.ForecastApp.DI.Dagger_App

import android.app.Application
import android.content.Context

import com.example.ForecastApp.Database.ForecastDatabase

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class AppModule(private val app: Application) {


    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideForecastDatabase(context: Context): ForecastDatabase {
        //lazy instantiation  of database ensures we only create and access the one instance
        return ForecastDatabase.getAppDataBase(context)!!

    }

}

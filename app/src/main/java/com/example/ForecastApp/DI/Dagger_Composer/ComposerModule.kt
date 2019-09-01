package com.example.ForecastApp.DI.Dagger_Composer

import android.content.Context
import com.example.ForecastApp.mvp.MainScreenFragment.MainActivityContract
import com.example.ForecastApp.mvp.MainActivity.MainActivityPresenter


import dagger.Module
import dagger.Provides

@Module
class ComposerModule(private val activityContext: Context) {


    @Provides
    @ActivityScope
    internal fun provideContext():Context{
        return activityContext
    }

    @Provides
    @ActivityScope
    internal fun provideActivityPresenter(myActivityContext: Context): MainActivityContract.Presenter {

        return MainActivityPresenter(myActivityContext)
    }
}

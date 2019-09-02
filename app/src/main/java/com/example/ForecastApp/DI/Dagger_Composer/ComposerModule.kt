package com.example.ForecastApp.DI.Dagger_Composer

import android.content.Context
import com.example.ForecastApp.Activities.HomeActivity
import com.example.ForecastApp.mvp.MainActivity.MainActivityContract
import com.example.ForecastApp.mvp.MainActivity.MainActivityPresenter


import dagger.Module
import dagger.Provides

@Module
class ComposerModule(private val activityContext: HomeActivity) {


    @Provides
    @ActivityScope
    internal fun provideContext():HomeActivity{
        return activityContext
    }

    @Provides
    @ActivityScope
    internal fun provideActivityPresenter(myActivityContext: HomeActivity): MainActivityContract.Presenter {

        return MainActivityPresenter(myActivityContext)
    }
}

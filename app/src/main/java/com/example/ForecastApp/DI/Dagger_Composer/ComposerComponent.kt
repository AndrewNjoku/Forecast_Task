package com.example.ForecastApp.DI.Dagger_Composer

import com.example.ForecastApp.Activities.HomeActivity

import dagger.Component

@ActivityScope
@Component(modules = [ComposerModule::class])
interface ComposerComponent {

    fun inject(homeActivity: HomeActivity)
}

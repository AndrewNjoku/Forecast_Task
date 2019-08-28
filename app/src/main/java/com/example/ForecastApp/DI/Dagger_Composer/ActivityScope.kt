package com.example.ForecastApp.DI.Dagger_Composer

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Scope

@Scope
@Retention(RetentionPolicy.CLASS)
annotation class ActivityScope

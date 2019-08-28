package com.example.ForecastApp.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.ForecastApp.model.Objects.Main_Elements.Forecast

import io.reactivex.Maybe

@Dao
interface ForecastOpp {

    @get:Query("SELECT * FROM forecast")
    val forecastAll: Maybe<List<Forecast>>

    @Query("SELECT * FROM forecast WHERE name LIKE :cityname ")
    fun forecast(cityname: String): Maybe<Forecast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecasts(vararg forecasts: Forecast)
}

package com.example.ForecastApp.Network

import com.example.ForecastApp.DataBank.Constants.GET_FIVE_DAY_FORECAST
import com.example.ForecastApp.model.Objects.Main_Elements.Forecast


import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastService {

    @GET(GET_FIVE_DAY_FORECAST)
    fun getFiveDayForecast(@Query("id") id: String, @Query("APPID") appId: String): Observable<Forecast>

}

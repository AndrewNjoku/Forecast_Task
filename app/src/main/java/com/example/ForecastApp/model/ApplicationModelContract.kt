package com.example.ForecastApp.model


import com.example.ForecastApp.model.Objects.Main_Elements.Day
import com.example.ForecastApp.model.Objects.Main_Elements.Day_w
import com.example.ForecastApp.model.Objects.Main_Elements.Forecast


interface ApplicationModelContract {

    fun stop()
    fun handleResult(results : List<*>)

}
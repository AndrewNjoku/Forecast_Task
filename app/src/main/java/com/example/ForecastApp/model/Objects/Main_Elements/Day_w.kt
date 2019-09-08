package com.example.ForecastApp.model.Objects.Main_Elements

import com.example.ForecastApp.DataBank.Utils
import com.example.ForecastApp.model.Objects.Supporting_Elements.Clouds
import com.example.ForecastApp.model.Objects.Supporting_Elements.Wind

class Day_w(private val day:String) {

    var dateDay: String = ""
    var minTemp: Double = 0.0
    var maxTemp: Double = 0.0
    var dailyTemps= ArrayList<Double>()
    var clouds: Clouds? = null
    var wind: Wind? = null
    var condition: String = ""
    var icon: String = ""

    fun parse(day: Day){

        dateDay=Utils.getDateAndDay(day.dateAndTime)
        dailyTemps.add(day.main?.temp!!)
        condition= day.weather?.get(0)?.description.toString()
        clouds= day.clouds
        wind= day.wind
        icon= day.weather?.get(0)?.icon.toString()

    }

}

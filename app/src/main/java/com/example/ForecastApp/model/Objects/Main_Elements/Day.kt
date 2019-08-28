package com.example.ForecastApp.model.Objects.Main_Elements

import androidx.room.Embedded
import androidx.room.TypeConverters

import com.example.ForecastApp.Database.convertWeatherType
import com.example.ForecastApp.model.Objects.Supporting_Elements.Clouds
import com.example.ForecastApp.model.Objects.Supporting_Elements.Main
import com.example.ForecastApp.model.Objects.Supporting_Elements.Wind
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Day {
    @SerializedName("dt")
    @Expose
    var dateAndTime: Long = 0

    //main contins the main temperature details
    @SerializedName("main")
    @Expose
    var main: Main? = null

    //weather contains main , description and icon for the "Weather"
    @TypeConverters(convertWeatherType::class)
    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null

    //clouds is cloud details
    @Embedded
    @SerializedName("clouds")
    @Expose
    var clouds: Clouds? = null

    //wind speed etc
    @Embedded
    @SerializedName("wind")
    @Expose
    var wind: Wind? = null
}

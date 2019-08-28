package com.example.ForecastApp.DataBank

object Constants {


    val OPEN_WEATHER_API = "http://api.openweathermap.org/data/2.5"
    val OPEN_WEATHER_API_MODE = "&mode=json"
    val OPEN_WEATHER_COMMON_PARAMS = OPEN_WEATHER_API_MODE
    val FORECAST_BY_NAME_URL = "$OPEN_WEATHER_API/forecast/daily?q=%s&cnt=5$OPEN_WEATHER_COMMON_PARAMS"
    val CONNECTION_TIMEOUT: Long = 60
    val READ_TIMEOUT: Long = 60
    val BASE_URL = "https://api.openweathermap.org/"
    val ICON_BASE_URL = "http://openweathermap.org/img/w/"
    val AUTOCOMPLETE_API_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=%s&types=(regions)&sensor=false&key="
    //val OPEN_WEATHER_API_KEY = "e87978f0ef99fd866adffe6a767099d3"
    val OPEN_WEATHER_API_KEY = "&appid="
    val OPEN_WEATHER_API_UNITS = "&units="

    const val GET_FIVE_DAY_FORECAST = "data/2.5/forecast"

    val ICON_EXTENSION = ".png"

    val CELSIUS_EXTENSION = "°C"
}

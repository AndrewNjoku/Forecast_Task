package com.example.ForecastApp.DataBank

import android.content.Context
import android.net.ConnectivityManager
import com.example.ForecastApp.R
import com.example.ForecastApp.application.App

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.lang.NumberFormatException

import java.math.RoundingMode
import java.net.URLEncoder
import java.text.DecimalFormat

object Utils {
    private val KELVIN_CONSTANT = 273.15

    fun getCelsiusFromKelvin(value: Double?): String {
        val tempInCelsius = value?.minus(KELVIN_CONSTANT)
        return roundDoubleToTwoDecimalPoints(tempInCelsius) + Constants.CELSIUS_EXTENSION
    }

    fun getDateForLocaleFromUtc(value: Long): String {
        val dateTime = DateTime(value * 1000L, DateTimeZone.getDefault()) //Converting to milliseconds
        val dateTimeFormatter = DateTimeFormat.forPattern("d MMM E h:m a")
        return dateTimeFormatter.print(dateTime)
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    fun roundDoubleToTwoDecimalPoints(value: Double?): String {
        val decimalFormat = DecimalFormat("#.#")
        decimalFormat.roundingMode = RoundingMode.CEILING
        return decimalFormat.format(value)
    }


    fun buildUrl(urlTemplate: String, location: String): String {
        return try {
            val builder = StringBuilder(String.format(urlTemplate, URLEncoder.encode(location, "UTF-8")))
            addUnitParam(builder)
            builder.toString()
        } catch (e: Exception) {
            Constants.FORECAST_BY_NAME_URL
        }

    }
    private fun addUnitParam(builder: StringBuilder) {
        builder.append(Constants.OPEN_WEATHER_API_KEY).append(App.instance.getString(R.string.openweather_api_key))
            builder.append(Constants.OPEN_WEATHER_API_UNITS).append("metric")

    }

}

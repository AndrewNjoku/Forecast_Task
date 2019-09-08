package com.example.ForecastApp.DataBank

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
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

    fun getDateAndDay(value: Long): String {
        val dateTime = DateTime(value * 1000L, DateTimeZone.getDefault()) //Converting to milliseconds
        val dateTimeFormatter = DateTimeFormat.forPattern("EEE, MMM d,")
        Log.e("DATETIME","date + day convertion:"+dateTimeFormatter.print(dateTime))
        return dateTimeFormatter.print(dateTime)
    }

    fun getDayFromDate(value: Long): String {
        val dateTime = DateTime(value * 1000L, DateTimeZone.getDefault()) //Converting to milliseconds
        val dateTimeFormatter = DateTimeFormat.forPattern("EEEE")
        Log.e("DATETIME","day convertion:"+dateTimeFormatter.print(dateTime))
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




    }



package com.example.ForecastApp.DataBank

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.ForecastApp.R
import com.example.ForecastApp.application.App
import com.example.ForecastApp.model.Objects.Main_Elements.Day
import com.example.ForecastApp.model.Objects.Main_Elements.Day_w

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
        Log.e("DATETIME", "date + day convertion:" + dateTimeFormatter.print(dateTime))
        return dateTimeFormatter.print(dateTime)
    }

    fun getDayFromDate(value: Long): String {
        val dateTime = DateTime(value * 1000L, DateTimeZone.getDefault()) //Converting to milliseconds
        val dateTimeFormatter = DateTimeFormat.forPattern("EEEE")
        Log.e("DATETIME", "day convertion:" + dateTimeFormatter.print(dateTime))
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

    fun parseDays(days: List<Day>): List<Day_w> {
        var newList = ArrayList<Day_w>()

        for (day: Day in days) {
            when (getDayFromDate(day.dateAndTime)) {
                "Monday" -> newList[0].parse(day)
                "Tuesday" -> newList[1].parse(day)
                "Wednesday" -> newList[2].parse(day)
                "Thursday" -> newList[3].parse(day)
                "Friday" -> newList[4].parse(day)
                "Saurday" -> newList[5].parse(day)
                "Sunday" -> newList[6].parse(day)
            }
        }
        //For each day in the week
        for (day_w: Day_w in newList) {
            //sort the list of temperatures
            day_w.dailyTemps.sort()
            //index 0 is min temp, index 3 is max temp : 4 x 3 hour readings for each day
            day_w.minTemp = day_w.dailyTemps[0]
            day_w.maxTemp = day_w.dailyTemps[3]
        }
        return newList


    }
}



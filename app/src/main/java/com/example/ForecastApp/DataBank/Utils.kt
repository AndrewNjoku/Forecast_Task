package com.example.ForecastApp.DataBank

import android.content.Context
import android.net.ConnectivityManager

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.lang.NumberFormatException

import java.math.RoundingMode
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

    //check if the toDOuble check passes to see if t is co-ordinates
   fun isGpsCoordinates(location: String): Boolean {
        try {
            val split = location.replace("Search Lat: ", "").replace("Lon: ", "").split(",")
            if (split.size== 2) {
               val dbl = split[0].trim().toDouble()
                return true;
            }
            return false
        } catch (nfe: NumberFormatException) {
            return false
        }
    }
    //replace string with empty spaces to convert to the right format for long and lat
    fun convertToGpsCoordinates(location: String): Array<String>? {
        try {
            //replace the string
            val split = location.replace("Search Lat: ", "").replace("Lon: ", "").split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (split.size == 2) {
                split[0] = split[0].trim { it <= ' ' }
                split[1] = split[1].trim { it <= ' ' }
                return split
            }
            return null
        } catch (nfe: NumberFormatException) {
            return null
        }

    }

}

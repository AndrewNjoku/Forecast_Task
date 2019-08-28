package com.example.ForecastApp.model.Objects.Predicitions

import android.text.TextUtils
import com.example.ForecastApp.R
import com.example.ForecastApp.application.App
import com.google.gson.annotations.SerializedName

import java.util.ArrayList



class Predictions {

    //list of predictions to be inflated in my search adapter

    //this list should be nullable
    var predictions: List<Prediction>? = null
        get() = field ?: ArrayList()

    var status:String = ""
        get() = if (TextUtils.isEmpty(field)) {
            Status.ERROR.toString()
        } else {
            if (field == Status.OK.name) {
                Status.OK.toString()
            } else if (field == Status.REQUEST_DENIED.name) {
                Status.REQUEST_DENIED.toString()
            } else if (field == Status.ZERO_RESULTS.name) {
                Status.ZERO_RESULTS.toString()
            } else {
                Status.ERROR.toString()
            }
        }
    @SerializedName("error_message")
    var errorMessage: String? = null
        private set

    val count: Int
        get() = if (predictions != null) {
            predictions!!.size
        } else 0



    //return status of predicitions


    fun hasError(): Boolean {
        //java version
        //return errorMessage = errorMessage!!.isNotEmpty()
        //Kotlin version - dont need t check for null
        return errorMessage!!.isNotEmpty()
    }

    enum class Status {
        ZERO_RESULTS, OK, REQUEST_DENIED, ERROR
    }

    //error companion object initialised to conform to error spec
    companion object {
        val ERROR = Predictions()

        init {
            ERROR.status = Status.ERROR.name
            ERROR.errorMessage = App.instance.getString(R.string.error_unknown)
        }
    }
}

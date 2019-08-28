package com.example.ForecastApp.model.Objects.Predicitions

import android.text.TextUtils

import com.google.gson.annotations.SerializedName


class Prediction {
    var id: String = ""
    var description: String = ""
    @SerializedName("structured_formatting")
    var structuredFormatting: PredictionStructuredFormatting? = null

    override fun toString(): String {
        //in all instances return the description
        return if (structuredFormatting == null || TextUtils.isEmpty(structuredFormatting!!.mainText) || TextUtils.isEmpty(structuredFormatting!!.secondaryText)) {
            description
        } else {
            structuredFormatting!!.mainText + ", " + structuredFormatting!!.secondaryText
        }

    }
}

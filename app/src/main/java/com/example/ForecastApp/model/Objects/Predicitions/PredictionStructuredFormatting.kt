package com.example.ForecastApp.model.Objects.Predicitions

import com.google.gson.annotations.SerializedName

class PredictionStructuredFormatting {
    @SerializedName("main_text")
    lateinit var mainText: String
    @SerializedName("secondary_text")
    lateinit var secondaryText: String
}

package com.example.ForecastApp.model.Objects.Supporting_Elements

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Clouds {
    @SerializedName("all")
    @Expose
    var all: Int = 0
}

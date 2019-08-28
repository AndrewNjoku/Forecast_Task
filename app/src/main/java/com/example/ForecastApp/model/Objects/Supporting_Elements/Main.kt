package com.example.ForecastApp.model.Objects.Supporting_Elements

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Main {
    @SerializedName("temp")
    @Expose
    var temp: Double = 0.toDouble()

    @SerializedName("temp_min")
    @Expose
    var tempMin: Double = 0.toDouble()

    @SerializedName("temp_max")
    @Expose
    var tempMax: Double = 0.toDouble()

    @SerializedName("pressure")
    @Expose
    var pressure: Double = 0.toDouble()

    @SerializedName("sea_level")
    @Expose
    var seaLevel: Double = 0.toDouble()

    @SerializedName("grnd_level")
    @Expose
    var grndLevel: Double = 0.toDouble()

    @SerializedName("humidity")
    @Expose
    var humidity: Double = 0.toDouble()

    @SerializedName("temp_kf")
    @Expose
    var tempKf: Double = 0.toDouble()
}

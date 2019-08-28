package com.example.ForecastApp.model.Objects.Supporting_Elements

import androidx.room.ColumnInfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class City {
    @ColumnInfo(name = "city_id")
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("country")
    @Expose
    var country: String? = null
}

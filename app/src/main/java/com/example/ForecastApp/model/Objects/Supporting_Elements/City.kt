package com.example.ForecastApp.model.Objects.Supporting_Elements

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class City {
    @PrimaryKey
    @ColumnInfo(name = "city_id")
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("name")
    @Expose
    lateinit var name: String
    @SerializedName("country")
    @Expose
    lateinit var country: String
}

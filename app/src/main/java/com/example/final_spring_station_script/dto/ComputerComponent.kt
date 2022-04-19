package com.example.final_spring_station_script.dto

import com.google.gson.annotations.SerializedName

data class ComputerComponent(@SerializedName("Type") var Type : String ="",
    @SerializedName("Name") var Name : String = "",
    @SerializedName("Brand") var Brand : String = "",
    @SerializedName("Price") var Price: Double = 0.0,
    @SerializedName("Rating") var Rating: Int = 0
    ){
    private var component = Type + " " + Name+ " " + Brand + " " + Price + " " + Rating
    override fun toString(): String {
        return component
    }
}


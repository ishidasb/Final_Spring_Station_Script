package com.example.final_spring_station_script.dto

data class SpecifiedComputerPart(

    var thisPartType : String = "",
    var thisPartName :String = "",
    var thisPartBrand : String = "",
    var thisPartPrice : Double = 0.0,
    var thisPartRating : Int = 0,
    var thisPartId : Int
    ) {
    override fun toString(): String {
        return "$thisPartType, $thisPartName, $thisPartBrand, $thisPartPrice, $thisPartRating"
    }
}

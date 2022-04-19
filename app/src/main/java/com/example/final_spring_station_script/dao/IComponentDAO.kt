package com.example.final_spring_station_script.dao

import com.example.final_spring_station_script.dto.ComputerComponent
import retrofit2.Call

import retrofit2.http.GET

interface IComponentDAO {
    @GET ("b/625e67abbc312b30ebe93989")
    fun getAllComponents() : Call<ArrayList<ComputerComponent>>
}
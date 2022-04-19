package com.example.final_spring_station_script.service

import com.example.final_spring_station_script.RetrofitClientInstance
import com.example.final_spring_station_script.dao.IComponentDAO
import com.example.final_spring_station_script.dto.ComputerComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

interface IComponentService {
    suspend fun fetchComputerComponents() : List<ComputerComponent>?
}

class ComponentService : IComponentService {
    override suspend fun fetchComputerComponents(): List<ComputerComponent>? {
        return withContext(Dispatchers.IO){
            val service = RetrofitClientInstance.retrofitInstance?.create(IComponentDAO::class.java)
            val components = async {service?.getAllComponents()}
            var result = components.await()?.awaitResponse()?.body()
            return@withContext result
        }
    }
}
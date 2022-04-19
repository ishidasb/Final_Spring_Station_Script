
package com.example.springprojectstationbuild.service

import com.example.final_spring_station_script.MainViewModel
import com.example.final_spring_station_script.service.ComponentService
import com.example.final_spring_station_script.service.IComponentService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    single<IComponentService> { ComponentService() }
}


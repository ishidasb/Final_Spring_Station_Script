package com.example.final_spring_station_script

import com.example.final_spring_station_script.service.IComponentService
import com.example.final_spring_station_script.service.ComponentService
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    viewModel {MainViewModel(get())}
    single<IComponentService>{ComponentService()}
}
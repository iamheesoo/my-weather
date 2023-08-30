package com.example.myweather

import com.example.myweather.domain.ApiService
import com.example.myweather.domain.WeatherRepository
import com.example.myweather.domain.WeatherRepositoryImpl
import com.example.myweather.info.WeatherInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { WeatherInfoViewModel(get()) }
}

val apiModule = module {
    single { get<ApiService>().create() }
    single { ApiService }
}
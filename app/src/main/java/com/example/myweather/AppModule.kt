//package com.example.myweather
//
//import com.example.myweather.domain.ApiService
//import com.example.myweather.domain.GeocodingRepository
//import com.example.myweather.domain.GeocodingRepositoryImpl
//import com.example.myweather.domain.WeatherRepository
//import com.example.myweather.domain.WeatherRepositoryImpl
//import com.example.myweather.info.WeatherInfoViewModel
//import com.example.myweather.search.SearchViewModel
//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.dsl.module
//
//val appModule = module {
//    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
//    single<GeocodingRepository> { GeocodingRepositoryImpl(get()) }
//}
//
//val viewModelModule = module {
//    viewModel { MainViewModel(get()) }
//    viewModel { WeatherInfoViewModel(get()) }
//    viewModel { SearchViewModel(get()) }
//}
//
//val apiModule = module {
//    single { get<ApiService>().weatherRetrofitCreate() }
//    single { get<ApiService>().geocodingRetrofitCreate() }
//    single { ApiService }
//}
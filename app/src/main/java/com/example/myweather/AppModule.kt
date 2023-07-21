package com.example.myweather

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

}

val viewModelModule = module {
    viewModel { MainViewModel() }
}

val apiModule = module {

}
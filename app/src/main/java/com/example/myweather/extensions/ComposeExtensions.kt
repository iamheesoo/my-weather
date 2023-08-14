package com.example.myweather.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import org.koin.androidx.viewmodel.ViewModelOwner

@Composable
fun getComposeActivityViewModelOwner(): ViewModelStoreOwner? {
    return LocalContext.current.getActivity()?.let { _activity ->
        ViewModelOwner.from(
            storeOwner = _activity,
            stateRegistry = _activity
        ).storeOwner
    }
}
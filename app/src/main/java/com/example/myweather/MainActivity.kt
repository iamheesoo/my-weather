package com.example.myweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myweather.search.SearchScreen
import com.example.myweather.ui.theme.MyWeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyWeatherTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Navigation.MAIN.name) {
                    composable(Navigation.MAIN.name) {
                        MainScreen(
                            viewModel = hiltViewModel(),
                            onListClick = { _locationList ->
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    NavigationKey.LOCATION_LIST,
                                    _locationList
                                )
                                navController.navigate(Navigation.SEARCH.name)
                            },
                            navController = navController
                        )
                    }
                    composable(Navigation.SEARCH.name) {
                        SearchScreen(
                            onPopBackStack = { isAdded ->
                                navController.previousBackStackEntry?.savedStateHandle?.set(
                                    NavigationKey.IS_ADDED,
                                    isAdded
                                )
                                navController.popBackStack()
                            },
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

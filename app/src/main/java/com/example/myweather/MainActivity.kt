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

//    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyWeatherTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Navigation.MAIN.name) {
                    composable(Navigation.MAIN.name) {
                        MainScreen(
                            viewModel = hiltViewModel(),
                            onListClick = {
                                navController.navigate(Navigation.SEARCH.name)
                            }
                        )
                    }
                    composable(Navigation.SEARCH.name) {
                        SearchScreen()
                    }
                }
            }
        }
    }
}

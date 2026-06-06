package com.example.mobileapps2025

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHost
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileapps2025.data.UserPreferencesRepository
import com.example.mobileapps2025.ui.screens.WelcomeScreen
import com.example.mobileapps2025.ui.theme.MobileApps2025Theme
import com.example.mobileapps2025.ui.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {

    private val userPreferencesRepository by lazy {
        UserPreferencesRepository(applicationContext)
    }
    private val appViewModel: AppViewModel by viewModels {
        AppViewModel.provideFactory(userPreferencesRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileApps2025Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(appViewModel = appViewModel)
                }



            }
        }
    }
}
@Composable
fun AppNavigation(appViewModel: AppViewModel){
    val navController = rememberNavController()

    val currentLanguage by appViewModel.currentLanguage.collectAsStateWithLifecycle()

    val startDestination = if (appViewModel.isUserLoggedIn) "main" else "welcome"

    NavHost(navController = navController, startDestination = startDestination){

        composable("welcome"){
            WelcomeScreen(
                currentLanguage = currentLanguage,
                onLanguageChange = {newLang -> appViewModel.changeLanguage(newLang)},
                onNavigateToLogin = {navController.navigate("login")}
            )
        }

        ///TODO
//        composable("login"){
//
//        }
//        composable("main"){
//
//        }



    }


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MobileApps2025Theme {
        Greeting("Android")
    }
}
package com.example.mobileapps2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileapps2025.data.*
import com.example.mobileapps2025.ui.screens.*
import com.example.mobileapps2025.ui.theme.MobileApps2025Theme
import com.example.mobileapps2025.ui.viewmodel.*

class MainActivity : ComponentActivity() {

    // Initialize repos
    private val userPreferencesRepository by lazy { UserPreferencesRepository(applicationContext) }
    private val networkMonitor by lazy { NetworkMonitor(applicationContext) }
    private val appViewModel: AppViewModel by viewModels {
        AppViewModel.provideFactory(userPreferencesRepository, networkMonitor)
    }

    private val appDatabase by lazy {
        com.example.mobileapps2025.data.local.AppDatabase.getDatabase(applicationContext)
    }
    private val firestoreDataSource by lazy {
        com.example.mobileapps2025.data.network.FirestoreDataSource()
    }

    private val artistRepository by lazy {
        com.example.mobileapps2025.data.ArtistRepository(
            appDatabase.artistDao(),
            firestoreDataSource,
            networkMonitor
        )
    }

    private val artistViewModel: com.example.mobileapps2025.ui.viewmodel.ArtistViewModel by viewModels {
        com.example.mobileapps2025.ui.viewmodel.ArtistViewModel.provideFactory(artistRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize system splash before onCreate
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Hold Splash Screen
        splashScreen.setKeepOnScreenCondition {
            !appViewModel.isAppReady.value
        }

        setContent {
            MobileApps2025Theme() {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNavigation(appViewModel = appViewModel, artistViewModel = artistViewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(
    appViewModel: AppViewModel,
    artistViewModel: com.example.mobileapps2025.ui.viewmodel.ArtistViewModel
) {
    val navController = rememberNavController()
    val currentLanguage by appViewModel.currentLanguage.collectAsStateWithLifecycle()
    val isOnline by appViewModel.isOnline.collectAsStateWithLifecycle()
    val cachedAuthState by appViewModel.cachedAuthState.collectAsStateWithLifecycle()

    val authViewModel: AuthViewModel = viewModel()

    // Dynamically determine the start screen
    val startDestination = if (!isOnline) {
        "no_internet"
    } else if (cachedAuthState) {
        "main"
    } else {
        "welcome"
    }
    LaunchedEffect(isOnline) {
        if (!isOnline) {
            navController.navigate("no_internet") { popUpTo(0) } // clear history
        } else {
            val destination = if (cachedAuthState) "main" else "welcome"
            navController.navigate(destination) { popUpTo(0) }
        }
    }

    NavHost(navController = navController, startDestination = startDestination) {

        composable("no_internet") {
            NoInternetScreen(currentLanguage = currentLanguage)
        }

        composable("splash") {
            SplashScreen()
        }

        composable("welcome") {
            WelcomeScreen(
                currentLanguage = currentLanguage,
                onLanguageChange = { appViewModel.changeLanguage(it) },
                onNavigateToLogin = { navController.navigate("login") }
            )
        }

        composable("login") {
            LoginScreen(
                currentLanguage = currentLanguage,
                authViewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate("main") { popUpTo(0) }
                },
                onNavigateToSignUp = { navController.navigate("signup") },
                onResetPassword = { navController.navigate("reset") }
            )
        }

        composable("signup") {
            SignupScreen(
                currentLanguage = currentLanguage,
                authViewModel = authViewModel,
                onNavigateToLogin = { navController.navigate("login") },
                onSignupSuccess = { navController.navigate("main") { popUpTo(0) } }
            )
        }

        composable("reset") {
            ResetPassword(
                currentLanguage = currentLanguage,
                authViewModel = authViewModel,
                onNavigateToLogin = { navController.navigate("login") { popUpTo("login") { inclusive = true } } }
            )
        }

        composable("main") {
            MainScreen(
                currentLanguage = currentLanguage,
                viewModel = artistViewModel,
                onArtistClick = { artistId ->
                    //TODO
                    println("Clicked on artist: $artistId")
                }
            )
        }
    }
}
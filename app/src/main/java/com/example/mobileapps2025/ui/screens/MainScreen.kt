package com.example.mobileapps2025.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen(currentLanguage: String) {
    // Get current user from firebase
    val username = FirebaseAuth.getInstance().currentUser?.displayName ?: "Guest"

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Success login, hello $username !", fontSize = 24.sp)
    }
}

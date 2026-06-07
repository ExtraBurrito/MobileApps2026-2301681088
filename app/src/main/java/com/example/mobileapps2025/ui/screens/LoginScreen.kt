package com.example.mobileapps2025.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapps2025.ui.theme.MobileApps2025Theme
import com.example.mobileapps2025.ui.viewmodel.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    currentLanguage: String,
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onResetPassword: () -> Unit
) {
    LoginScreenContent(
        currentLanguage = currentLanguage,
        isLoading = authViewModel.isLoading,
        errorMessage = authViewModel.errorMessage,
        onLoginClick = { email, password ->
            authViewModel.signIn(email, password, onLoginSuccess)
        },
        onNavigateToSignUp = onNavigateToSignUp,
        onResetPassword = onResetPassword
    )
}

@Composable
fun LoginScreenContent(
    currentLanguage: String,
    isLoading: Boolean,
    errorMessage: String?,
    onLoginClick: (String, String) -> Unit,
    onNavigateToSignUp: () -> Unit,
    onResetPassword: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val emailLabel = when(currentLanguage) {
        "English" -> "Email"
        "Български" -> "Имейл"
        else -> "Электронная почта"
    }
    val passwordLabel = when(currentLanguage) {
        "English" -> "Password"
        "Български" -> "Парола"
        else -> "Пароль"
    }
    val loginButtonText = when(currentLanguage) {
        "English" -> "Log In"
        "Български" -> "Вход"
        else -> "Войти"
    }
    val forgotPasswordText = when(currentLanguage) {
        "English" -> "Forgot Password?"
        "Български" -> "Забравена парола?"
        else -> "Забыли пароль?"
    }
    val signUpText = when(currentLanguage) {
        "English" -> "Sign Up"
        "Български" -> "Регистрация"
        else -> "Зарегистрироваться"
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(30.dp)
        ) {
            Text(
                text = "Musify",
                fontSize = 56.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 32.dp),
                fontWeight = FontWeight.Bold
            )
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.width(400.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = emailLabel,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = email, onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp),
                            singleLine = true, isError = errorMessage != null
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = passwordLabel,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            singleLine = true, isError = errorMessage != null
                        )
                    }

                    if (errorMessage != null) {
                        Text(
                            text = errorMessage, color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { onLoginClick(email.trim(), password.trim()) },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                        } else {
                            Text(text = loginButtonText, fontSize = 18.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    TextButton(onClick = onResetPassword) {
                        Text(text = forgotPasswordText, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    TextButton(onClick = onNavigateToSignUp) {
                        Text(text = signUpText, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true, device = "id:pixel_2")
@Composable
fun LoginScreenPreview() {
    MobileApps2025Theme {
        LoginScreenContent(
            currentLanguage = "English",
            isLoading = false,
            errorMessage = null,
            onLoginClick = { _, _ -> },
            onNavigateToSignUp = {},
            onResetPassword = {}
        )
    }
}

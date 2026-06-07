package com.example.mobileapps2025.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.mobileapps2025.ui.viewmodel.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ResetPassword(
    currentLanguage: String,
    authViewModel: AuthViewModel = viewModel(),
    onNavigateToLogin: () -> Unit,
) {
    val context = LocalContext.current

    ResetPasswordContent(
        currentLanguage = currentLanguage,
        isLoading = authViewModel.isLoading,
        errorMessage = authViewModel.errorMessage,
        onResetClick = { email, onSuccess ->
            authViewModel.resetPassword(email, onSuccess)
        },
        onNavigateToLogin = onNavigateToLogin,
        showToast = { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    )
}

@Composable
fun ResetPasswordContent(
    currentLanguage: String,
    isLoading: Boolean,
    errorMessage: String?,
    onResetClick: (String, () -> Unit) -> Unit,
    onNavigateToLogin: () -> Unit,
    showToast: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(true) }
    var timeLeft by remember { mutableIntStateOf(0) }

    val reset = when (currentLanguage) {
        "English" -> "Reset your password"
        "Български" -> "Нулиране на парола"
        else -> "Сброс пароля"
    }
    val emaillabel = when (currentLanguage) {
        "English" -> "Your Email"
        "Български" -> "Вашия имейл"
        else -> "Ваш адрес электронной почты"
    }
    val returntologin = when (currentLanguage) {
        "English" -> "Return to login"
        "Български" -> "Върнете се към логин"
        else -> "Вернуться к авторизации"
    }

    val buttonText = if (isButtonEnabled) {
        when (currentLanguage) {
            "English" -> "Send reset link"
            "Български" -> "Изпрати линк"
            else -> "Отправить письмо"
        }
    } else {
        val minutes = String.format("%02d", timeLeft / 60)
        val seconds = String.format("%02d", timeLeft % 60)
        when (currentLanguage) {
            "English" -> "Resend in $minutes:$seconds"
            "Български" -> "Изпрати отново след $minutes:$seconds"
            else -> "Повторить через $minutes:$seconds"
        }
    }

    LaunchedEffect(timeLeft) {
        if (timeLeft > 0) {
            delay(1000L)
            timeLeft -= 1
        } else if (timeLeft == 0 && !isButtonEnabled) {
            isButtonEnabled = true
        }
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
                text = reset,
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 32.dp)
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
                        Text(text = emaillabel, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            singleLine = true,
                            isError = errorMessage != null
                        )
                    }

                    if (errorMessage != null) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (email.isNotBlank()) {
                                onResetClick(email.trim()) {
                                    isButtonEnabled = false
                                    timeLeft = 120
                                    showToast("Email sent")
                                }
                            }
                        },
                        enabled = isButtonEnabled && !isLoading,
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text(text = buttonText)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = onNavigateToLogin) {
                        Text(
                            text = returntologin,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_6_pro", showSystemUi = true)
@Composable
fun ResetPasswordPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ResetPasswordContent(
                currentLanguage = "English",
                isLoading = false,
                errorMessage = null,
                onResetClick = { _, _ -> },
                onNavigateToLogin = {},
                showToast = {}
            )
        }
    }
}

package com.example.mobileapps2025.ui.screens.tabs

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ProfileTab(
    currentLanguage: String,
    favoriteCount: Int,
    username: String = "Guest",
    email: String = "No email",
    onLogout: () -> Unit
) {
    val context = LocalContext.current

    val title = when (currentLanguage) {
        "English" -> "Profile"
        "Български" -> "Профил"
        else -> "Профиль"
    }
    val savedCountLabel = when (currentLanguage) {
        "English" -> "Saved artists"
        "Български" -> "Запазени артисти"
        else -> "Сохранено артистов"
    }

    val logoutBtn = when (currentLanguage) {
        "English" -> "Log Out"
        "Български" -> "Изход"
        else -> "Выйти из аккаунта"
    }
    val editBtn = when (currentLanguage) {
        "English" -> "Edit Profile"
        "Български" -> "Редактиране"
        else -> "Изменить данные"
    }
    val inDevelopment = when (currentLanguage) {
        "English" -> "in development"
        "Български" -> "в процес на разработка"
        else -> "в разработке"
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = title,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = username,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = email,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "$favoriteCount $savedCountLabel",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                Toast.makeText(context,
                    inDevelopment,
                    Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(editBtn, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
        ) {
            Text(logoutBtn, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Preview composables with dummy data
@Preview(name = "Profile Tab - English", showBackground = true, device = "id:pixel_6_pro")
@Composable
fun ProfileTabEnglishPreview() {
    MaterialTheme {
        ProfileTab(
            currentLanguage = "English",
            favoriteCount = 2,
            username = "John doe",
            email = "john.doe@example.com",
            onLogout = {}
        )
    }
}

@Preview(name = "Profile Tab - Bulgarian", showBackground = true)
@Composable
fun ProfileTabBulgarianPreview() {
    MaterialTheme {
        ProfileTab(
            currentLanguage = "Български",
            favoriteCount = 5,
            username = "Иван Иванов",
            email = "ivan@example.com",
            onLogout = {}
        )
    }
}

@Preview(name = "Profile Tab - Russian", showBackground = true)
@Composable
fun ProfileTabRussianPreview() {
    MaterialTheme {
        ProfileTab(
            currentLanguage = "Русский",
            favoriteCount = 8,
            username = "Анна Смирнова",
            email = "anna@example.com",
            onLogout = {}
        )
    }
}

@Preview(name = "Profile Tab - Empty Favorites", showBackground = true)
@Composable
fun ProfileTabEmptyFavoritesPreview() {
    MaterialTheme {
        ProfileTab(
            currentLanguage = "English",
            favoriteCount = 0,
            username = "Guest User",
            email = "guest@example.com",
            onLogout = {}
        )
    }
}
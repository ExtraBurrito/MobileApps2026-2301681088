package com.example.mobileapps2025.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
class AuthRepository  {

    private val auth = FirebaseAuth.getInstance()

    //Check if the user has logged in account
    val isUserLoggedIn: Boolean
        get() = auth.currentUser != null

    suspend fun signUp(email: String, password: String, username: String): Result<Unit>{
        return try {
            // Create user
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user

            if (user != null) {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build()
                user.updateProfile(profileUpdates).await()
            }
            Result.success(Unit)
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}
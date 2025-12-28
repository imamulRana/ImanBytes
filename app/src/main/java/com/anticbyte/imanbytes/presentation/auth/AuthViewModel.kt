package com.anticbyte.imanbytes.presentation.auth

import android.content.Context
import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth
    private var fbUser: FirebaseUser? = null

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    fun launchCredentials(credentialManager: CredentialManager, context: Context) {
        viewModelScope.launch {
            runCatching {
                // First try with filtered accounts
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(true)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialManager.getCredential(context, request)
                handleSignIn(result.credential)
            }.onFailure { e ->
                Log.e("AuthViewModel", "Filtered sign-in failed: ${e.message}")
                // If filtered fails, try without filter (for new sign-ups)
                trySignInWithoutFilter(credentialManager, context)
            }
        }
    }

    private fun trySignInWithoutFilter(credentialManager: CredentialManager, context: Context) {
        viewModelScope.launch {
            runCatching {
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialManager.getCredential(context, request)
                handleSignIn(result.credential)
            }.onFailure { e ->
                Log.e("AuthViewModel", "Sign-in failed: ${e.message}")
                // Handle error - update UI state
            }
        }
    }

    private fun handleSignIn(credential: Credential) {
        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
        firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user != null
                    fbUser = user
                    _state.update {
                        it.copy(
                            userName = fbUser?.displayName.orEmpty(),
                            userEmail = fbUser?.email.orEmpty(),
                            userId = fbUser?.uid.orEmpty()
                        )
                    }
                    Log.d("AuthViewModel", "Sign-in successful: ${user?.email}")
                } else {
                    Log.e("AuthViewModel", "Firebase auth failed: ${task.exception?.message}")
                }
            }
    }
}
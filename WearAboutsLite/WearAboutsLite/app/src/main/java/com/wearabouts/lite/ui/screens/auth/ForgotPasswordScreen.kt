package com.wearabouts.lite.ui.screens.auth

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wearabouts.lite.ui.components.PrimaryButton
import com.wearabouts.lite.ui.theme.Background
import com.wearabouts.lite.ui.theme.Primary
import com.wearabouts.lite.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(onNavigateBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var resendCooldown by remember { mutableStateOf(0) }
    var redirectCountdown by remember { mutableStateOf(5) }

    fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    LaunchedEffect(resendCooldown) {
        if (resendCooldown > 0) {
            delay(1000)
            resendCooldown--
        }
    }

    LaunchedEffect(isSubmitted) {
        if (isSubmitted) {
            while (redirectCountdown > 0) {
                delay(1000)
                redirectCountdown--
            }
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reset Password", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        },
        containerColor = Background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedContent(
                targetState = isSubmitted,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                }, label = ""
            ) { submitted ->
                if (!submitted) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Forgot your password?",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Enter your email to receive a password reset link.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        OutlinedTextField(
                            value = email,
                            onValueChange = { 
                                email = it
                                if (error != null) error = null
                            },
                            placeholder = { Text("you@email.com", color = TextSecondary) },
                            leadingIcon = { Icon(Icons.Default.Email, null, tint = TextSecondary) },
                            trailingIcon = {
                                if (error != null) Icon(Icons.Default.Error, null, tint = MaterialTheme.colorScheme.error)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            isError = error != null,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f),
                                focusedBorderColor = Primary,
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White
                            ),
                            singleLine = true
                        )
                        
                        if (error != null) {
                            Text(
                                text = error!!,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp).align(Alignment.Start)
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        PrimaryButton(
                            text = if (isLoading) "" else "Send Reset Link",
                            onClick = {
                                if (validateEmail(email)) {
                                    isLoading = true
                                    // Simulate network
                                    kotlinx.coroutines.MainScope().launch {
                                        delay(1500)
                                        isLoading = false
                                        isSubmitted = true
                                        resendCooldown = 30
                                    }
                                } else {
                                    error = "Please enter a valid email address"
                                }
                            },
                            enabled = email.isNotBlank() && !isLoading
                        )
                        
                        if (isLoading) {
                            Box(modifier = Modifier.offset(y = (-36).dp)) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 2.dp)
                            }
                        }
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF22C55E),
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Reset Link Sent!",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "We've sent a password reset link to\n$email",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(48.dp))
                        
                        TextButton(
                            onClick = { 
                                if (resendCooldown == 0) {
                                    resendCooldown = 30
                                    // Simulate resend
                                }
                            },
                            enabled = resendCooldown == 0
                        ) {
                            Text(
                                text = if (resendCooldown > 0) "Resend link in ${resendCooldown}s" else "Resend link",
                                color = if (resendCooldown > 0) TextSecondary else Primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Redirecting to login in ${redirectCountdown}s...",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                        
                        PrimaryButton(
                            text = "Back to Login",
                            onClick = onNavigateBack
                        )
                    }
                }
            }
        }
    }
}

// Extension to allow launch in onClick
private fun kotlinx.coroutines.CoroutineScope.launch(block: suspend kotlinx.coroutines.CoroutineScope.() -> Unit) {
    kotlinx.coroutines.MainScope().launch { block() }
}

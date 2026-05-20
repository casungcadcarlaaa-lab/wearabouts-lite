package com.wearabouts.lite.ui.screens.auth

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.wearabouts.lite.ui.components.PrimaryButton
import com.wearabouts.lite.ui.components.bounceClick
import com.wearabouts.lite.ui.theme.Primary
import com.wearabouts.lite.ui.theme.TextSecondary
import com.wearabouts.lite.viewmodel.ClothingViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: ClothingViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    
    // Mock Social Auth States
    var showSocialDialog by remember { mutableStateOf(false) }
    var selectedSocial by remember { mutableStateOf("") }
    var isSocialLoading by remember { mutableStateOf(false) }
    var selectedName by remember { mutableStateOf("") }

    // Login Error State
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    if (showSocialDialog) {
        Dialog(onDismissRequest = { if (!isSocialLoading) showSocialDialog = false }) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isSocialLoading) {
                        CircularProgressIndicator(color = Color(0xFF4285F4))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Signing you in with Google...", fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("G", color = Color(0xFF4285F4), fontWeight = FontWeight.Bold, fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Choose an account", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        // Mock Google accounts
                        listOf("Carla Casungcad" to "carla.casungcad@unc.edu.ph", "Alex Rivera" to "alex@example.com").forEach { (name, mail) ->
                            Surface(
                                onClick = {
                                    selectedName = name
                                    isSocialLoading = true
                                },
                                shape = RoundedCornerShape(12.dp),
                                color = Color.Transparent,
                                modifier = Modifier.bounceClick()
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(if(name.startsWith("C")) Color(0xFF4D7C6E) else Color.LightGray), 
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(name.take(1), color = Color.White, fontWeight = FontWeight.Bold)
                                    }
                                    Spacer(Modifier.width(12.dp))
                                    Column {
                                        Text(name, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                        Text(mail, fontSize = 12.sp, color = TextSecondary)
                                    }
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        TextButton(onClick = { showSocialDialog = false }, modifier = Modifier.fillMaxWidth()) {
                            Text("Cancel", color = TextSecondary)
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(isSocialLoading) {
        if (isSocialLoading) {
            delay(1500)
            viewModel.setUserName(selectedName)
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Checkroom, null, tint = Color.White.copy(alpha = 0.9f), modifier = Modifier.size(48.dp))
            Icon(Icons.Default.Place, null, tint = Color(0xFFC9A96E), modifier = Modifier.size(12.dp).offset(y = 4.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("WearAbouts Lite", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
        Text("Welcome back. Your wardrobe awaits.", style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
        
        Spacer(modifier = Modifier.height(32.dp))

        // Error Alert Sign and Note
        AnimatedVisibility(
            visible = showError,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                color = Color(0xFFFEF2F2),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFFFCA5A5))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = "Error",
                        tint = Color(0xFFEF4444),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Login Failed",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF991B1B),
                            fontSize = 14.sp
                        )
                        Text(
                            text = errorMessage,
                            color = Color(0xFFB91C1C),
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        OutlinedTextField(
            value = email,
            onValueChange = { 
                email = it
                showError = false 
            },
            placeholder = { Text("you@email.com", color = TextSecondary) },
            leadingIcon = { Icon(Icons.Default.Email, null, tint = if(showError) Color(0xFFEF4444) else TextSecondary) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if(showError) Color(0xFFFCA5A5) else Color.LightGray.copy(alpha = 0.5f),
                focusedBorderColor = if(showError) Color(0xFFEF4444) else Primary,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { 
                password = it
                showError = false
            },
            placeholder = { Text("Enter your password", color = TextSecondary) },
            leadingIcon = { Icon(Icons.Default.Lock, null, tint = if(showError) Color(0xFFEF4444) else TextSecondary) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff, null, tint = TextSecondary)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if(showError) Color(0xFFFCA5A5) else Color.LightGray.copy(alpha = 0.5f),
                focusedBorderColor = if(showError) Color(0xFFEF4444) else Primary,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            ),
            singleLine = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onNavigateToForgotPassword) {
                Text("Forgot Password?", color = Color(0xFFC9A96E), fontWeight = FontWeight.Medium)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        PrimaryButton(
            text = "Log In", 
            onClick = {
                // Mock Validation Logic
                if (email.isEmpty() || password.isEmpty()) {
                    errorMessage = "Please enter both email and password."
                    showError = true
                } else if (password.length < 6) {
                    errorMessage = "Invalid password. Please try again."
                    showError = true
                } else {
                    // Success path
                    val name = email.substringBefore("@").replaceFirstChar { it.uppercase() }
                    viewModel.setUserName(if (name.isNotEmpty()) name else "User")
                    onLoginSuccess()
                }
            }
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Divider(modifier = Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
            Text(" or continue with ", color = TextSecondary, modifier = Modifier.padding(horizontal = 8.dp), fontSize = 12.sp)
            Divider(modifier = Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        SocialButton(
            icon = { Text("G", color = Color(0xFF4285F4), fontWeight = FontWeight.Bold, fontSize = 20.sp) },
            label = "Google",
            modifier = Modifier.fillMaxWidth(),
            onClick = { selectedSocial = "Google"; showSocialDialog = true }
        )

        Spacer(modifier = Modifier.height(48.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Don't have an account? ", color = TextSecondary)
            TextButton(onClick = onNavigateToSignUp, contentPadding = PaddingValues(0.dp)) {
                Text("Sign Up", color = Primary, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SocialButton(icon: @Composable () -> Unit, label: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(60.dp).bounceClick(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon()
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text("Continue with", fontSize = 9.sp, color = TextSecondary)
                Text(label, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}

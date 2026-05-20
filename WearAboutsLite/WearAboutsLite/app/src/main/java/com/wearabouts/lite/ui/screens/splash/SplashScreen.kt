package com.wearabouts.lite.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashScreen(onNavigateToLogin: () -> Unit) {
    // Colors from the design
    val offWhiteBackground = Color(0xFFF5F5F3)
    val brandGreen = Color(0xFF4D7C6E)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(offWhiteBackground)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Icon Box (RESTORED)
            Surface(
                modifier = Modifier
                    .size(120.dp)
                    .shadow(10.dp, RoundedCornerShape(28.dp)),
                shape = RoundedCornerShape(28.dp),
                color = brandGreen
            ) {
                Box(contentAlignment = Alignment.Center) {
                    // Hanger Icon
                    Icon(
                        imageVector = Icons.Default.Checkroom,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(70.dp)
                    )
                    // Small Pin Icon inside the hanger
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        tint = Color(0xFFC9A96E), 
                        modifier = Modifier
                            .size(14.dp)
                            .offset(y = 10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // App Name - Single line Serif font
            Text(
                text = "WearAbouts Lite",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A),
                    fontSize = 35.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Tagline
            Text(
                text = "Know Where Your Clothes Are, Always.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                ),
                textAlign = TextAlign.Center
            )
            
            // Shirt emoji
            Text(
                text = "👕",
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Get Started Button
            Button(
                onClick = onNavigateToLogin,
                modifier = Modifier
                    .width(160.dp)
                    .height(52.dp)
                    .shadow(6.dp, RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = brandGreen,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Get Started",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}
